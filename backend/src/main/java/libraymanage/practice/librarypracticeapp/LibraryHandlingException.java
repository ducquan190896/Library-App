package libraymanage.practice.librarypracticeapp;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import libraymanage.practice.librarypracticeapp.Exception.AppIllegalStateException;
import libraymanage.practice.librarypracticeapp.Exception.EntityExistException;
import libraymanage.practice.librarypracticeapp.Exception.EntityNotFoundException;
import libraymanage.practice.librarypracticeapp.Exception.ErrorResponse;

@ControllerAdvice
public class LibraryHandlingException {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handlingNotFoundException(EntityNotFoundException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), ex, LocalDateTime.now());
        return new ResponseEntity<Object>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityExistException.class)
    public ResponseEntity<Object> handlingEntityExistException(EntityExistException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), ex, LocalDateTime.now());
        return new ResponseEntity<Object>(err, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AppIllegalStateException.class)
    public ResponseEntity<Object> handlingAppillegalStateException(AppIllegalStateException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), ex, LocalDateTime.now());
        return new ResponseEntity<Object>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handlingRuntimeException(RuntimeException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), ex, LocalDateTime.now());
        return new ResponseEntity<Object>(err, HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler(IllegalStateException.class)
    // public ResponseEntity<Object> handlingIllegalStateException(IllegalStateException ex) {
    //     ErrorResponse err = new ErrorResponse(ex.getMessage(), ex, LocalDateTime.now());
    //     return new ResponseEntity<Object>(err, HttpStatus.BAD_REQUEST);
    // }

    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<Object> handlingValidationException(MethodArgumentNotValidException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), ex, LocalDateTime.now());
        return new ResponseEntity<Object>(err, HttpStatus.BAD_REQUEST);
    }
}
