package libraymanage.practice.librarypracticeapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.ImageEntity2;

@Repository
public interface ImageEntityRepos2 extends JpaRepository<ImageEntity2, Long> {
    
    @Query(
        value = "select * from image_entity2 where name = ?",
        nativeQuery = true
    )
    Optional<ImageEntity2> findByName(String name);
}
