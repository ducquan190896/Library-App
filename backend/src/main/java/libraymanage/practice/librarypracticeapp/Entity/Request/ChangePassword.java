package libraymanage.practice.librarypracticeapp.Entity.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {
   
    private String currentPassword;
    private String newPassword;
    private String confirmedPassword;    
}
