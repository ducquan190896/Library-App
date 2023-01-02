package libraymanage.practice.librarypracticeapp.Exception;

public class EntityNotFoundException extends RuntimeException {
   

    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {
        super();
    }
}
