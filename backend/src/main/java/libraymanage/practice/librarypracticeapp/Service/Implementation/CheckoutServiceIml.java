package libraymanage.practice.librarypracticeapp.Service.Implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Entity.Checkout;
import libraymanage.practice.librarypracticeapp.Entity.History;
import libraymanage.practice.librarypracticeapp.Entity.Role;
import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Exception.AppIllegalStateException;
import libraymanage.practice.librarypracticeapp.Exception.EntityExistException;
import libraymanage.practice.librarypracticeapp.Exception.EntityNotFoundException;
import libraymanage.practice.librarypracticeapp.Repository.BookRepos;
import libraymanage.practice.librarypracticeapp.Repository.CheckoutRepos;
import libraymanage.practice.librarypracticeapp.Repository.HistoryRepos;
import libraymanage.practice.librarypracticeapp.Repository.UserRepos;
import libraymanage.practice.librarypracticeapp.Service.CheckoutService;

@Service
public class CheckoutServiceIml implements CheckoutService {
    
    @Autowired 
    CheckoutRepos checkoutRepos;

    @Autowired
    BookRepos bookRepos;

    @Autowired
    UserRepos userRepos;

    @Autowired
    HistoryRepos historyRepos;

    @Override
    public List<Checkout> checkoutByBook(Long bookId) {
       Optional<Book> entity = bookRepos.findById(bookId);
       if(!entity.isPresent()) {
        throw new EntityNotFoundException("the book with id " + bookId + " not found");     
       }
       Book book = entity.get();
       return checkoutRepos.findCheckoutByBook(book);

    }

    @Override
    public List<Checkout> checkoutByUser(Long userId) {
       Optional<Users> entity = userRepos.findById(userId);
       if(!entity.isPresent()) {
        throw new EntityNotFoundException("the user with id " + userId + " not found");
       }
       Users user = entity.get();
       return checkoutRepos.findCheckoutByUser(user);
    }

    @Override
    public Checkout getChechout(Long id) {
      Optional<Checkout> entity = checkoutRepos.findById(id);
      Checkout checkout = isCheck(entity, id);
      return checkout;
    }

    @Override
    public void turnCheckoutIntoHistorty(Long id) {
        Optional<Checkout> entity = checkoutRepos.findById(id);
        Checkout checkout = isCheck(entity, id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username ==  null) {
            throw new AppIllegalStateException("the username is not exist in the authentication");
        }
        Optional<Users> userEntity = userRepos.findByUsername(username);
        if(!userEntity.isPresent()) {
            throw new EntityNotFoundException("the user with username " + username + " not found");
        }
        Users user = userEntity.get();
        LocalDateTime currentTime = LocalDateTime.now();
        Book book = checkout.getBook();
        History history = new History(checkout.getCheckoutTime(), currentTime, book, user);
        historyRepos.save(history);
        user.getCheckouts().remove(checkout);
        book.getCheckouts().remove(checkout);
        checkoutRepos.deleteById(id);
        userRepos.save(user);
        bookRepos.save(book);
       
        
    }

    @Override
    public List<Checkout> checkouts() {
       return checkoutRepos.findAll();
    }

    @Override
    public void createCheckout(Long bookId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username == null) {
            throw new AppIllegalStateException("there is no username in the authorization");
        }
        Optional<Users> userEntity = userRepos.findByUsername(username);
        if(!userEntity.isPresent()) {
            throw new EntityNotFoundException("the user with username " + username + " not found");
        }
        Users user = userEntity.get();

        Optional<Book> bookEntity = bookRepos.findById(bookId);
        if(!bookEntity.isPresent()) {
            throw new EntityNotFoundException("the book with id " + bookId + " not found");
        }
        Book book = bookEntity.get();

        Optional<Checkout> checkoutEntity = checkoutRepos.findByUserAndBook(user, book);
        if(checkoutEntity.isPresent()) {
            throw new EntityExistException("the checkout already exist, cannot checkout more");
        }
        if(user.getCheckouts().size() >= 5) {
            throw new AppIllegalStateException("the maximum checkout per user is 5");
        }

        if(book.getCopiesAvailable() <= 0) {
            throw new AppIllegalStateException("the is now available copies for checkout, please wait for the next copy");
        }

        Checkout checkout = new Checkout(LocalDateTime.now(), LocalDateTime.now().plusDays(10), book, user);
        checkoutRepos.save(checkout);
        user.getCheckouts().add(checkout);

        book.getCheckouts().add(checkout);
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        userRepos.save(user);
        bookRepos.save(book);
        
    }

    @Override
    public Checkout updateCheckout(Long id) {
        Optional<Checkout> entity = checkoutRepos.findById(id);
        Checkout checkout = isCheck(entity, id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username ==  null) {
            throw new AppIllegalStateException("the username is not exist in the authentication");
        }
        Optional<Users> userEntity = userRepos.findByUsername(username);
        if(!userEntity.isPresent()) {
            throw new EntityNotFoundException("the user with username " + username + " not found");
        }
        Users user = userEntity.get();
        // if(!user.equals(checkout.getUser()) || !user.getRole().getName().equals(Role.ADMIN.getName())) {
        //     throw new AppIllegalStateException("the user is not authorized to extend the checkout");
        // }
        if(!user.equals(checkout.getUser())) {
            throw new AppIllegalStateException("the user is not authorized to extend the checkout");
        } else if(user.equals(checkout.getUser()) || user.getRole().getName().equals(Role.ADMIN.getName())) {
  
            LocalDateTime currentTime = LocalDateTime.now();
            if(checkout.getReturnTime().isBefore(currentTime)) {
                throw new AppIllegalStateException("the time for returning the book is due, cannot extend it");
            }
            checkout.setReturnTime(checkout.getReturnTime().plusDays(10));
           return checkoutRepos.save(checkout);
        }
      return null;
    }

    private Checkout isCheck(Optional<Checkout> entity, Long id) {
        if(entity.isPresent()) {
            return entity.get();
        }
        throw new EntityNotFoundException("the checkout with id " + id + " not found");
    }

    @Override
    public List<Checkout> checkoutByAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username ==  null) {
            throw new AppIllegalStateException("the username is not exist in the authentication");
        }
        Optional<Users> entity = userRepos.findByUsername(username);
       if(!entity.isPresent()) {
        throw new EntityNotFoundException("the user with username " + username + " not found");
       }
       Users user = entity.get();
       return checkoutRepos.findCheckoutByUser(user);
    }
    
}
