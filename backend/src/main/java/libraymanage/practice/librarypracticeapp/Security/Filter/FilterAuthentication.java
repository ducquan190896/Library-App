package libraymanage.practice.librarypracticeapp.Security.Filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Entity.Response.UserResponse;
import libraymanage.practice.librarypracticeapp.Repository.UserRepos;
import libraymanage.practice.librarypracticeapp.Security.CustomAuthenticationManager;
import libraymanage.practice.librarypracticeapp.Security.SecurityConstant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FilterAuthentication extends UsernamePasswordAuthenticationFilter{
    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    UserRepos userRepos;
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Users user = new ObjectMapper().readValue(request.getInputStream(), Users.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        return customAuthenticationManager.authenticate(authentication);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        Users user = userRepos.findByUsername(username).get();
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail(), username, user.getRole());     
        Gson gson = new Gson();

        List<String> claims = authResult.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
        String token = JWT.create()
        .withSubject(authResult.getName())
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.expire_time))
        .withClaim("authorities", claims)
        .sign(Algorithm.HMAC512(SecurityConstant.private_key));
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
            
        response.setHeader("Authorization", SecurityConstant.authorization + token);
        
        response.setStatus(HttpServletResponse.SC_OK);
        // response.getWriter().write("login successfully");
        response.getWriter().print(gson.toJson(userResponse));
        response.getWriter().flush();
        
    }
}
