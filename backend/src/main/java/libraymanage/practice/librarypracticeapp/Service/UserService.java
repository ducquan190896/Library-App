package libraymanage.practice.librarypracticeapp.Service;

import java.util.List;

import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Entity.Request.ChangePassword;
import libraymanage.practice.librarypracticeapp.Entity.Request.UserRegister;
import libraymanage.practice.librarypracticeapp.Entity.Response.UserResponse;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse getUserById(Long id);
    UserResponse getUserByName(String username);
    UserResponse getuserByEmail(String email);
    void saveUser(UserRegister userRegister);
    UserResponse updateToAdmin(Long userId);
    UserResponse updatePassword(ChangePassword changePassword);
    void deleteUser(Long id);
}
