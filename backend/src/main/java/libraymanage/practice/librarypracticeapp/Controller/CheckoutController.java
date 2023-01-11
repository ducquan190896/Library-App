package libraymanage.practice.librarypracticeapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import libraymanage.practice.librarypracticeapp.Entity.Checkout;
import libraymanage.practice.librarypracticeapp.Service.CheckoutService;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    @Autowired
    CheckoutService checkoutService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Checkout>> getcheckouts() {
        return new ResponseEntity<List<Checkout>>(checkoutService.checkouts(), HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Checkout>> getcheckoutByUser(@PathVariable Long id) {
        return new ResponseEntity<List<Checkout>>(checkoutService.checkoutByUser(id), HttpStatus.OK);
    }

    @GetMapping("/authUser")
    public ResponseEntity<List<Checkout>> getcheckoutByAuthUser() {
        return new ResponseEntity<List<Checkout>>(checkoutService.checkoutByAuthUser(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/book/{id}")
    public ResponseEntity<List<Checkout>> getcheckoutByBoook(@PathVariable Long id) {
        return new ResponseEntity<List<Checkout>>(checkoutService.checkoutByBook(id), HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Checkout> getcheckoutById(@PathVariable Long id) {
        return new ResponseEntity<Checkout>(checkoutService.getChechout(id), HttpStatus.OK);
    }

    @PostMapping("/book/{bookId}")
    public ResponseEntity<HttpStatus> saveCheckout(@PathVariable Long bookId) {
        checkoutService.createCheckout(bookId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/extendReturn/{id}")
    public ResponseEntity<Checkout> extendReturnTime(@PathVariable Long id) {
        
        return new ResponseEntity<>(checkoutService.updateCheckout(id), HttpStatus.OK);
    }

    @PutMapping("/returnBook/{id}")
    public ResponseEntity<HttpStatus> returnBook(@PathVariable Long id) {
        checkoutService.turnCheckoutIntoHistorty(id);;
        return new ResponseEntity<>(HttpStatus.OK);
    }
}