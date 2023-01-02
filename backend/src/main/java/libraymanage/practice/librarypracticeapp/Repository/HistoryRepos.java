package libraymanage.practice.librarypracticeapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.History;

@Repository
public interface HistoryRepos extends JpaRepository<History, Long> {
    
}
