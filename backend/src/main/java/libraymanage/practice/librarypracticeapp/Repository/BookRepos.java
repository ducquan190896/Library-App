package libraymanage.practice.librarypracticeapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Book;

@Repository
public interface BookRepos extends JpaRepository<Book, Long> {
    
}
