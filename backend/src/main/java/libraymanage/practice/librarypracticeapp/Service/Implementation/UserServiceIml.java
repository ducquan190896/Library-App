package libraymanage.practice.librarypracticeapp.Service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Exception.EntityNotFoundException;
import libraymanage.practice.librarypracticeapp.Repository.UserRepos;
import libraymanage.practice.librarypracticeapp.Service.UserService;

@Service
public class UserServiceIml implements UserService, UserDetailsService {
    @Autowired
    UserRepos userRepos;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> entity = userRepos.findByEmail(username);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the user with email " + username + " not found");
        }
        Users user = entity.get();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
