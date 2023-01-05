package libraymanage.practice.librarypracticeapp.Entity.Response;
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
public class ReviewResponse {
    private Long id;
    private int rating;
    private String reviewDescription;
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    private Long bookId;
    private String username;

    public ReviewResponse(Long id, int rating, LocalDateTime date, Long bookId, String username) {
        this.id = id;
        this.rating = rating;
        this.date = date;
        this.bookId = bookId;
        this.username = username;
    }

    
}
