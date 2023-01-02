package libraymanage.practice.librarypracticeapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Review;

@Repository
public interface ReviewRepos extends JpaRepository<Review, Long> {
    
}
