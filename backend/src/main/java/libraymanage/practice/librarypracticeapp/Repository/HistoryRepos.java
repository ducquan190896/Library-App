package libraymanage.practice.librarypracticeapp.Repository;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Entity.History;
import libraymanage.practice.librarypracticeapp.Entity.Users;

@Repository
public interface HistoryRepos extends JpaRepository<History, Long> {
    List<History> findHistoryByUser(Users user);
    List<History> findHistoryByBook(Book book);
    List<History> findByUserAndBook(Users user, Book book);
}
