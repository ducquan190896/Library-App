package libraymanage.practice.librarypracticeapp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import libraymanage.practice.librarypracticeapp.Security.Filter.FilterAuthentication;
import libraymanage.practice.librarypracticeapp.Security.Filter.FilterException;
import libraymanage.practice.librarypracticeapp.Security.Filter.FilterJwtAuthorization;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    
    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    FilterException filterException;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        FilterAuthentication filterAuthentication = new FilterAuthentication(customAuthenticationManager);
        filterAuthentication.setFilterProcessesUrl("/login");

        http.csrf().disable()
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(filterException, filterAuthentication.getClass())
        .addFilter(filterAuthentication)
        .addFilterAfter(new FilterJwtAuthorization(), filterAuthentication.getClass())
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
