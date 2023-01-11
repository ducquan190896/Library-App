package libraymanage.practice.librarypracticeapp.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;


@Table(name = "checkout", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "book_id"})})
@Entity(name = "Checkout")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {

    @Id
    @SequenceGenerator(
        name = "sequence_checkout",
        sequenceName = "sequence_checkout",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sequence_checkout"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    // "startDate":"2019-04-02 11:45" this is json request
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "checkout_time", nullable = false)
    private LocalDateTime checkoutTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "return_time", nullable = false)
    private LocalDateTime returnTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "book_id",
        referencedColumnName = "id"
    )
    private Book book;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    private Users user;

    public Checkout(LocalDateTime checkoutTime, LocalDateTime returnTime, Book book, Users user) {
        this.checkoutTime = checkoutTime;
        this.returnTime = returnTime;
        this.book = book;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Checkout [id=" + id + ", checkoutTime=" + checkoutTime + ", returnTime=" + returnTime + ", book=" + book.getId()
                + ", user=" + user.getId() + "]";
    }

    
    
}
