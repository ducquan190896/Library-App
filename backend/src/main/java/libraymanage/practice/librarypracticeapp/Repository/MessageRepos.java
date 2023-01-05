package libraymanage.practice.librarypracticeapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Message;
import libraymanage.practice.librarypracticeapp.Entity.Users;

@Repository
public interface MessageRepos extends JpaRepository<Message, Long> {
    List<Message> findMessageByClosed(boolean closed);
    List<Message> findMessageByUserAndClosed(Users user, boolean closed);
}
