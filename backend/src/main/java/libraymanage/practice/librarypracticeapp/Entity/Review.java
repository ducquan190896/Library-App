package libraymanage.practice.librarypracticeapp.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import libraymanage.practice.librarypracticeapp.Validation.RatingSize;
import lombok.*;


@Table(name = "review", uniqueConstraints = {@UniqueConstraint(columnNames = {"book_id", "user_id"})})
@Entity(name = "Review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @SequenceGenerator(
        name = "sequence_review",
        sequenceName = "sequence_review",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sequence_review"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    // @RatingSize
    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "review_description")
    private String reviewDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "book_id",
        referencedColumnName = "id"
    )
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    private Users user;






    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

        
    

  
    public Review(int rating, String reviewDescription, Book book, Users user, LocalDateTime date) {
        this.rating = rating;
        this.reviewDescription = reviewDescription;
        this.book = book;
        this.user = user;
        this.date = date;
    }
    public Review(int rating,  Book book, Users user, LocalDateTime date) {
        this.rating = rating;
        this.book = book;
        this.user = user;
        this.date = date;
    }
}
