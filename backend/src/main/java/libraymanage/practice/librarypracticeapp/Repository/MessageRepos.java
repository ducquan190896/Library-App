package libraymanage.practice.librarypracticeapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Message;

@Repository
public interface MessageRepos extends JpaRepository<Message, Long> {
    
}
