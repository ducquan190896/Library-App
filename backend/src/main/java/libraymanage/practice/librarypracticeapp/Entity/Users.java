package libraymanage.practice.librarypracticeapp.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

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

    @NotBlank(message = "email cannot be blank")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "username cannot be blank")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "password cannot be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    private List<Checkout> checkouts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    private List<History> histories = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messagesUser = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "admin",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messagesAdmin = new ArrayList<>();



    public Users(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
       
    }

    
    @Override
    public int hashCode() {
        
        return Objects.hash(this.id, this.email, this.password);
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Users user = (Users) obj;
       return Objects.equals(user.getId(), this.getId()) && Objects.equals(user.getEmail(), this.getEmail()) && Objects.equals(this.getPassword(), user.getPassword());
    }



    @Override
    public String toString() {
        return "Users [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + "]";
    }

    
}
