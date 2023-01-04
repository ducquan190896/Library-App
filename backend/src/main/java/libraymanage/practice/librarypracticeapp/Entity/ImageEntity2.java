package libraymanage.practice.librarypracticeapp.Entity;
import javax.persistence.*;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;


@Table(name = "imageEntity2")
@Entity(name = "ImageEntity2")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity2 {
    @Id
    @SequenceGenerator(
        name = "sequence_imageEntity2",
        sequenceName = "sequence_imageEntity2",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sequence_imageEntity2"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    
    @NotBlank(message = "name of image cannot be blank")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "databye", nullable = false)
    private byte[] databyte;

    public ImageEntity2( String name, String type, byte[] databyte) {
        this.name = name;
        this.type = type;
        this.databyte = databyte;
       
    }

}
