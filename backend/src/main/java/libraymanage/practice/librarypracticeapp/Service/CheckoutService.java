package libraymanage.practice.librarypracticeapp.Service;

import java.util.List;

import libraymanage.practice.librarypracticeapp.Entity.Checkout;

public interface CheckoutService {
    List<Checkout> checkouts();
    List<Checkout> checkoutByUser(Long userId);
    List<Checkout> checkoutByBook(Long bookId);
    List<Checkout> checkoutByAuthUser();
    Checkout getChechout(Long id);
    void createCheckout(Long bookId);
    Checkout updateCheckout(Long id);
    void turnCheckoutIntoHistorty(Long id);
    
}
