package libraymanage.practice.librarypracticeapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Entity.Checkout;
import libraymanage.practice.librarypracticeapp.Entity.Users;

@Repository
public interface CheckoutRepos extends JpaRepository<Checkout, Long> {
    
    List<Checkout> findCheckoutByUser(Users user);
    List<Checkout> findCheckoutByBook(Book book);
    Optional<Checkout> findByUserAndBook(Users user, Book book);
}
