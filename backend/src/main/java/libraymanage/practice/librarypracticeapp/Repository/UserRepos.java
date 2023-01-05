package libraymanage.practice.librarypracticeapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Users;

@Repository
public interface UserRepos extends JpaRepository<Users, Long> {
    @Query(
        value = "select * from users where email = ?",
        nativeQuery = true
    )
    Optional<Users> findByEmail(String email);
    @Query(
        value = "select * from users where username = ?",
        nativeQuery = true
    )
    Optional<Users> findByUsername(String username);
}
