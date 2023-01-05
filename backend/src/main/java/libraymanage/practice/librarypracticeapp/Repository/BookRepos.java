package libraymanage.practice.librarypracticeapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Book;

@Repository
public interface BookRepos extends JpaRepository<Book, Long> {
    
    @Query(
        value = "select * from book where title = ? and author = ?",
        nativeQuery = true
    )
    Optional<Book> findByTitleAndAuthor(String title, String author);
}
