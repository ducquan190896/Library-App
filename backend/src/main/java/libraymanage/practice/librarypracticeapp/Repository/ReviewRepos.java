package libraymanage.practice.librarypracticeapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Entity.Review;
import libraymanage.practice.librarypracticeapp.Entity.Users;

@Repository
public interface ReviewRepos extends JpaRepository<Review, Long> {
    List<Review> findReviewByBook(Book book);
    Optional<Review> findByBookAndUser(Book book, Users user);
}
