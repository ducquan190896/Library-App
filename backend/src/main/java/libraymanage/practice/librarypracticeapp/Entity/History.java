package libraymanage.practice.librarypracticeapp.Entity;

import java.time.LocalDateTime;

import java.time.LocalDate;


import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;


@Table(name = "history")
@Entity(name = "History")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @SequenceGenerator(
        name = "sequence_history",
        sequenceName = "sequence_history",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sequence_history"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape  = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "checkout_time", nullable = false)
    private LocalDateTime checkoutTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape  = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "return_time", nullable = false)
    private LocalDateTime returnTime;

    @ManyToOne( fetch = 
    FetchType.EAGER)
    @JoinColumn(
        name = "book_id",
        referencedColumnName = "id"
    )
    private Book book;

    @ManyToOne( fetch = 
    FetchType.EAGER)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    private Users user;

    public History(LocalDateTime checkoutTime, LocalDateTime returnTime, Book book, Users user) {
        this.checkoutTime = checkoutTime;
        this.returnTime = returnTime;
        this.book = book;
        this.user = user;
    }

    @Override
    public String toString() {
        return "History [id=" + id + ", checkoutTime=" + checkoutTime + ", returnTime=" + returnTime + ", book=" + book.getId()
                + ", user=" + user.getId() + "]";
    }

    
}
