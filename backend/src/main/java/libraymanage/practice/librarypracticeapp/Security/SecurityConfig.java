package libraymanage.practice.librarypracticeapp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import libraymanage.practice.librarypracticeapp.Repository.UserRepos;
import libraymanage.practice.librarypracticeapp.Security.Filter.FilterAuthentication;
import libraymanage.practice.librarypracticeapp.Security.Filter.FilterException;
import libraymanage.practice.librarypracticeapp.Security.Filter.FilterJwtAuthorization;
import libraymanage.practice.librarypracticeapp.Service.UserService;
import libraymanage.practice.librarypracticeapp.Service.Implementation.UserServiceIml;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    FilterException filterException;


    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    CustomLogoutHandler customLogoutHandler;
    @Autowired
    UserRepos userRepos;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        FilterAuthentication filterAuthentication = new FilterAuthentication(customAuthenticationManager, userRepos);
        filterAuthentication.setFilterProcessesUrl("/login");

        http.csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/users/register").permitAll()
        .antMatchers(HttpMethod.GET, "/api/books/**/*").permitAll()
        .antMatchers(HttpMethod.GET, "/api/reviews/**/*").permitAll()
        .antMatchers(HttpMethod.GET, "/api/users/test").permitAll()
        .anyRequest().authenticated()
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(filterException, filterAuthentication.getClass())
        .addFilter(filterAuthentication)
        .addFilterAfter(new FilterJwtAuthorization(), filterAuthentication.getClass())
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.logout()        
        // .logoutUrl("/userLogout")
        .permitAll()
        .addLogoutHandler((request, response, auth) -> {
            SecurityContextHolder.clearContext();
            response.setHeader("Authorization", "");
        })
        .logoutSuccessHandler(customLogoutHandler)
        .invalidateHttpSession(true)
        .deleteCookies("token")
        ;
        http.cors();
        return http.build();
    }

    // @Bean
    // public WebMvcConfigurer corsConfigurer(){
    //     return new WebMvcConfigurer() {
    //         @Override
    //         public void addCorsMappings(CorsRegistry registry) {
    //             registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    //         }
    //     };
    // }
    

}
