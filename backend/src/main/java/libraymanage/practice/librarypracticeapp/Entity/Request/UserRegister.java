package libraymanage.practice.librarypracticeapp.Entity.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister {
    private String email;
    private String username;
    private String password;    
    private String confirmedPassword;
}
