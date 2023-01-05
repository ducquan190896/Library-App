package libraymanage.practice.librarypracticeapp.Service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import libraymanage.practice.librarypracticeapp.Entity.Role;
import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Entity.Request.ChangePassword;
import libraymanage.practice.librarypracticeapp.Entity.Request.UserRegister;
import libraymanage.practice.librarypracticeapp.Entity.Response.UserResponse;
import libraymanage.practice.librarypracticeapp.Exception.AppIllegalStateException;
import libraymanage.practice.librarypracticeapp.Exception.EntityExistException;
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


    @Override
    public void deleteUser(Long id) {
        List<String> roles =  SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
       roles.stream().forEach(ro -> System.out.println(ro));
       if(!roles.contains(Role.ADMIN.getName())) {
        throw new AppIllegalStateException("the role of current authUser is not admin");
       }
       Optional<Users> entity = userRepos.findById(id);
       Users user = isCheck(entity, id);
       userRepos.delete(user);
        
    }


    @Override
    public UserResponse getUserById(Long id) {
        Optional<Users> entity = userRepos.findById(id);
        Users user = isCheck(entity, id);
        UserResponse response = new UserResponse( user.getId() ,user.getEmail(), user.getUsername(), user.getRole());
        return response;
    }


    @Override
    public UserResponse getUserByName(String username) {
        Optional<Users> entity = userRepos.findByUsername(username);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the user with username " + username + " not found");
        }
        Users user = entity.get();
        UserResponse response = new UserResponse( user.getId() ,user.getEmail(), user.getUsername(), user.getRole());
        return response;
        
    }


    @Override
    public UserResponse getuserByEmail(String email) {
        Optional<Users> entity = userRepos.findByEmail(email);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the user with email " + email + " not found");
        }
        Users user = entity.get();
        UserResponse response = new UserResponse( user.getId() ,user.getEmail(), user.getUsername(), user.getRole());
        return response;
    }


    @Override
    public List<UserResponse> getUsers() {
       List<Users> userList = userRepos.findAll();
       return userList.stream().map(user -> new UserResponse(user.getId(), user.getEmail(), user.getUsername(), user.getRole())).collect(Collectors.toList());
    }


    @Override
    public void saveUser(UserRegister userRegister) {
       Optional<Users> entity = userRepos.findByEmail(userRegister.getEmail());
       if(entity.isPresent()) {
            throw new EntityExistException("the user register with email" + userRegister.getEmail() + " already exist");
       }
       if(!userRegister.getPassword().equals(userRegister.getConfirmedPassword())) {
        throw new IllegalStateException("the passwords provided do not match");
       }
       Users user = new Users(userRegister.getEmail(), new BCryptPasswordEncoder().encode(userRegister.getPassword()), userRegister.getUsername());
       user.setRole(Role.USER);
       userRepos.save(user);
        
    }

    @Override
    public UserResponse updatePassword(ChangePassword changePassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> entity = userRepos.findByUsername(username);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the user with username " + username +  " not found");
        }
        Users user = entity.get();
        boolean isCorrect = new BCryptPasswordEncoder().matches(changePassword.getCurrentPassword(), user.getPassword());
        if(!isCorrect) {
            throw new AppIllegalStateException("the current password provided is wrong");
        }
        if(!changePassword.getNewPassword().equals(changePassword.getConfirmedPassword())) {
            throw new AppIllegalStateException("the new and confirmed password provided do not match");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(changePassword.getNewPassword()));
        Users finalUser = userRepos.save(user);
        UserResponse response = new UserResponse( finalUser.getId() ,finalUser.getEmail(), finalUser.getUsername(), finalUser.getRole());
        return response;
    }


    @Override
    public UserResponse updateToAdmin(Long userId) {
        
       List<String> roles =  SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
       roles.stream().forEach(ro -> System.out.println(ro));
       if(!roles.contains(Role.ADMIN.getName())) {
        throw new AppIllegalStateException("the role of current authUser is not admin");
       }
       Optional<Users> entity = userRepos.findById(userId);
       Users user = isCheck(entity, userId);
       if(!user.getRole().equals(Role.USER)) {
        throw new AppIllegalStateException("the role of user is not a user, cannot update it to admind");
       }
       user.setRole(Role.ADMIN);
      
       Users finalUser = userRepos.save(user);
        UserResponse response = new UserResponse( finalUser.getId() ,finalUser.getEmail(), finalUser.getUsername(), finalUser.getRole());
        return response;

    }


    private Users isCheck(Optional<Users> entity, Long id) {
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the user with id " + id + " not found");
        }
        return entity.get();
    }
}
