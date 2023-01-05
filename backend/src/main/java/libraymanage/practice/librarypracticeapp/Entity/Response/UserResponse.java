package libraymanage.practice.librarypracticeapp.Entity.Response;

import libraymanage.practice.librarypracticeapp.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private Role role;
}
