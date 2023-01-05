package libraymanage.practice.librarypracticeapp.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutHandler implements LogoutHandler  {
    
   @Override
   public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
       SecurityContextHolder.getContext().setAuthentication(null);
       response.setHeader("Authorization", "");
      
   }
}
