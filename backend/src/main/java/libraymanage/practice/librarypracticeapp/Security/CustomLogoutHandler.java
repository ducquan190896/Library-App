package libraymanage.practice.librarypracticeapp.Security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler  {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        System.out.println("logout successfully with username ");
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.getWriter().write("logout successfully");
        response.getWriter().flush();
        super.onLogoutSuccess(request, response, authentication);
    }
    
//    @Override
//    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        SecurityContextHolder.getContext().setAuthentication(null);
//        response.setHeader("Authorization", "");
      
//    }

}
