package libraymanage.practice.librarypracticeapp.Entity.Request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private int rating;
    private String reviewDescription;

   

    private Long bookId;

    public ReviewRequest(int rating,  Long bookId) {
        this.rating = rating;    
        this.bookId = bookId;
    }

    
}
