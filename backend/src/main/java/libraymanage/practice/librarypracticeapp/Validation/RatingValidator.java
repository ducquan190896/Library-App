package libraymanage.practice.librarypracticeapp.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<RatingSize, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value >= 0 && value <= 5) {
            return true;
        }
        return false;
    }
    
}
