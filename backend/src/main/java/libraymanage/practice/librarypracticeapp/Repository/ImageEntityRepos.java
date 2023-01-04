package libraymanage.practice.librarypracticeapp.Repository;

import java.util.Optional;

import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.ImageEntity;

@Repository
public interface ImageEntityRepos extends JpaRepository<ImageEntity, Long> {
    
    @Query(
        value = "select * from image_entity where name = ?",
        nativeQuery = true
    )
    Optional<ImageEntity> findByName(String name);
}
