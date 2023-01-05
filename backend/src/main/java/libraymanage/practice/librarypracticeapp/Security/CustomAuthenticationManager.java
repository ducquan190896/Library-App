package libraymanage.practice.librarypracticeapp.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Repository.UserRepos;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    UserRepos userRepos;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       Optional<Users> entity = userRepos.findByUsername(authentication.getName()); 
       if(!entity.isPresent()) {
        throw new EntityNotFoundException("the user with email " + authentication.getName() + " not found");
       }
       Users user = entity.get();
       boolean isCorrect = new BCryptPasswordEncoder().matches(authentication.getCredentials().toString(), user.getPassword());
       if(!isCorrect) {
        throw new BadCredentialsException("you provided wrong password");
       }
       List<SimpleGrantedAuthority> authorities = new ArrayList<>();
       authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
       return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
    }
}
