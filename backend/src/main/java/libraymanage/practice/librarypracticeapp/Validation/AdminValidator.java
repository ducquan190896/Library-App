package libraymanage.practice.librarypracticeapp.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import libraymanage.practice.librarypracticeapp.Entity.Role;
import libraymanage.practice.librarypracticeapp.Entity.Users;

public class AdminValidator implements ConstraintValidator<IsAdmin, Users> {

    @Override
    public boolean isValid(Users value, ConstraintValidatorContext context) {
        if(value.getRole().getName().equals(Role.ADMIN.getName())) {
            return true;
        }
        return false;
    }
    
    
}
