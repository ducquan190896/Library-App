package libraymanage.practice.librarypracticeapp.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.sym.Name;

import lombok.*;


@Table(name = "book", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "author"})})
@Entity(name = "Book")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @SequenceGenerator(
        name = "sequence_users",
        sequenceName = "sequence_users",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sequence_users"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "title cannot be blank")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "author cannot be blank")
    @Column(name = "author", nullable = false)
    private String author;

    @NotBlank(message = "description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;

    @Min(value = 0, message = "copies cannot be less than 0")
    @Column(name = "copies", nullable = false)
    private int copies;

    @Min(value = 0, message = "copies available cannot be less than 0")
    @Column(name = "coppies_available", nullable = false)
    private int copiesAvailable;


    @NotBlank(message = "category cannot be blank")
    @Column(name = "category", nullable = false)
    private String category;

    @Column(name= "img_url")
    private String imgUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Checkout> checkouts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> histories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public Book( String title, String author, String description, int copies, int copiesAvailable, String category, String imgUrl) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.copies = copies;
        this.copiesAvailable = copiesAvailable;
        this.category = category;
        this.imgUrl = imgUrl;
    }

    public Book( String title, String author, String description, int copies, int copiesAvailable, String category) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.copies = copies;
        this.copiesAvailable = copiesAvailable;
        this.category = category;
    }

    

    @Override
    public int hashCode() {
       return Objects.hash(this.id, this.title, this.author, this.category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book book = (Book) obj;
       return Objects.equals(this.getId(), book.getId()) && Objects.equals(this.getAuthor(), book.getAuthor()) && Objects.equals(this.title, book.getTitle()) && Objects.equals(this.getCategory(), book.getCategory());
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description
                + ", copies=" + copies + ", copiesAvailable=" + copiesAvailable + ", category=" + category + ", imgUrl="
                + imgUrl + "]";
    }

    

    
}


