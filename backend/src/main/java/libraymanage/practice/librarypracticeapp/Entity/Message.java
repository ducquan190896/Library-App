package libraymanage.practice.librarypracticeapp.Entity;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import libraymanage.practice.librarypracticeapp.Validation.IsAdmin;
import lombok.*;


@Table(name = "message")
@Entity(name = "Message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @SequenceGenerator(
        name = "sequence_message",
        sequenceName = "sequence_message",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sequence_message"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "title cannot be blank")
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    private Users user;

    @Column(name = "question")
    private String question;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime questionTime;

    @IsAdmin
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "admin_id",
        referencedColumnName = "id"
    )
    private Users admin;

    @Column(name = "answer")
    private String answer;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime answerTime;

    
    @Column(name = "closed", nullable = false)
    private boolean closed;



    public Message(String title, Users user, String question,
            LocalDateTime questionTime, Users admin, String answer, LocalDateTime answerTime, boolean closed) {
        this.title = title;
        this.user = user;
        this.question = question;
        this.questionTime = questionTime;
        this.admin = admin;
        this.answer = answer;
        this.answerTime = answerTime;
        this.closed = closed;
    }

    public Message( String title, Users user, String question,
            LocalDateTime questionTime) {
        this.title = title;
        this.user = user;
        this.question = question;
        this.questionTime = questionTime;
        this.closed = false;
        
    }

    public void updateAnswer(String answer, Users admin, LocalDateTime answerTime) {
        // this.admin = admin;
        // this.answer = answer;
        // this.answerTime = answerTime;
        // this.closed = true;
        this.setAdmin(admin);
        this.setAnswer(answer);
        this.setAnswerTime(answerTime);
        this.setClosed(true);
    }

    public boolean getClosed() {
        return this.closed;
    }
    
}
