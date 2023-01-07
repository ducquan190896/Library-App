package libraymanage.practice.librarypracticeapp.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import libraymanage.practice.librarypracticeapp.Entity.Request.ChangePassword;
import libraymanage.practice.librarypracticeapp.Entity.Request.UserRegister;
import libraymanage.practice.librarypracticeapp.Entity.Response.UserResponse;
import libraymanage.practice.librarypracticeapp.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class userController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> Register(@Valid @RequestBody UserRegister userRegister) {
        userService.saveUser(userRegister);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return new ResponseEntity<UserResponse>(userService.getUserById(id), HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<UserResponse>(userService.getUserByName(username), HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<UserResponse>(userService.getuserByEmail(email), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAll() {
        return new ResponseEntity<List<UserResponse>>(userService.getUsers(), HttpStatus.OK);
    }

    @PutMapping("/changepassword")
    public ResponseEntity<UserResponse> changePassword(@Valid @RequestBody ChangePassword changePassword) {
        return new ResponseEntity<UserResponse>(userService.updatePassword(changePassword), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateToAdmin/{id}") 
    public ResponseEntity<UserResponse> updateRoleToAdmin(@PathVariable Long id) {
        return new ResponseEntity<UserResponse>(userService.updateToAdmin(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<HttpStatus> deleteUserByAdmin(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/test")
    public ResponseEntity<String> testApiwithReactNative() {
        return new ResponseEntity<String>("hello library app", HttpStatus.OK);
    }

}
