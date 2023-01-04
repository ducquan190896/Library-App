package libraymanage.practice.librarypracticeapp.Entity;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@Table(name = "imageEntity")
@Entity(name = "ImageEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity {
    @Id
    @SequenceGenerator(
        name = "sequence_imageEntity",
        sequenceName = "sequence_imageEntity",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sequence_imageEntity"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    
    @NotBlank(message = "name of image cannot be blank")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "filepath", nullable = false)
    private String filepath;

    public ImageEntity( String name, String type, String filepath) {
        this.name = name;
        this.type = type;
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return "ImageEntity [name=" + name + ", type=" + type + ", filepath=" + filepath + "]";
    }

    

        



}
