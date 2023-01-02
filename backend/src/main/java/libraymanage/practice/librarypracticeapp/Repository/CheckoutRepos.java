package libraymanage.practice.librarypracticeapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraymanage.practice.librarypracticeapp.Entity.Checkout;

@Repository
public interface CheckoutRepos extends JpaRepository<Checkout, Long> {
    
}
