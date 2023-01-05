package libraymanage.practice.librarypracticeapp.Service.Implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Entity.Review;
import libraymanage.practice.librarypracticeapp.Entity.Role;
import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Entity.Request.ReviewRequest;
import libraymanage.practice.librarypracticeapp.Entity.Response.ReviewResponse;
import libraymanage.practice.librarypracticeapp.Exception.AppIllegalStateException;
import libraymanage.practice.librarypracticeapp.Exception.EntityExistException;
import libraymanage.practice.librarypracticeapp.Exception.EntityNotFoundException;
import libraymanage.practice.librarypracticeapp.Repository.BookRepos;
import libraymanage.practice.librarypracticeapp.Repository.ReviewRepos;
import libraymanage.practice.librarypracticeapp.Repository.UserRepos;
import libraymanage.practice.librarypracticeapp.Service.ReviewService;

@Service
public class ReviewServiceIml implements ReviewService {
    @Autowired
    ReviewRepos reviewRepos;
    @Autowired
    BookRepos bookRepos;
    @Autowired
    UserRepos userRepos;

    @Override
    public void createReview(ReviewRequest reviewRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> userEntity = userRepos.findByUsername(username);
        if(!userEntity.isPresent()) {
            throw new EntityNotFoundException("the user with username " + username + " not found");
        }
        Users user = userEntity.get();
        Optional<Book> bookEntity = bookRepos.findById(reviewRequest.getBookId());
        if(!bookEntity.isPresent()) {
            throw new EntityNotFoundException("the book with id " + reviewRequest.getBookId() + " not found");
        }
        Book book = bookEntity.get();

        Optional<Review> reviewEntity = reviewRepos.findByBookAndUser(book, user);
        if(reviewEntity.isPresent()) {
            throw new EntityExistException("the review with book id " + book.getId() + " and user with id " + user.getId() + " already exist");
        }
        Review review = new Review(reviewRequest.getRating(), book, user, LocalDateTime.now());
        if(reviewRequest.getReviewDescription() != null) {
            review.setReviewDescription(reviewRequest.getReviewDescription());
        }
        reviewRepos.save(review);

        book.getReviews().add(review);
        bookRepos.save(book);

        user.getReviews().add(review);
        userRepos.save(user);
        
    }

    @Override
    public void deleteReview(Long id) {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(au -> au.getAuthority()).collect(Collectors.toList());
        if(!authorities.contains(Role.ADMIN.getName())) {
            throw new AppIllegalStateException("the role of auth user is not authorized to delete the review");
        }

        Optional<Review> entity = reviewRepos.findById(id);
        if(entity.isPresent()) {
         Review review = entity.get();
         Book book = review.getBook();
         book.getReviews().remove(review);
         bookRepos.save(book);
         
         Users user = review.getUser();
         user.getReviews().remove(review);
         userRepos.save(user);

        reviewRepos.deleteById(id);
        }
        throw new EntityNotFoundException("the review is not found with id " + id);
    }
    @Override
    public ReviewResponse getReviewById(Long Id) {
       Optional<Review> entity = reviewRepos.findById(Id);
       if(entity.isPresent()) {
        Review review = entity.get();
       if(review.getReviewDescription() != null) {
        ReviewResponse reviewResponse = new ReviewResponse(Id, review.getRating(), review.getReviewDescription(), review.getDate(), review.getBook().getId(), review.getUser().getUsername());
        return reviewResponse;
       }else {
        ReviewResponse reviewResponse = new ReviewResponse(Id, review.getRating(), review.getDate(), review.getBook().getId(), review.getUser().getUsername());
        return reviewResponse;
       }
       
       }
       throw new EntityNotFoundException("the review is not found with id " + Id);
    }
    @Override
    public List<ReviewResponse> getReviews() {
        List<Review> list = reviewRepos.findAll();
        List<ReviewResponse> listResponse = list.stream().map(review -> {
            if(review.getReviewDescription() != null) {
                return new ReviewResponse(review.getId(), review.getRating(), review.getReviewDescription(), review.getDate(), review.getBook().getId(), review.getUser().getUsername());
            }
            return new ReviewResponse(review.getId(), review.getRating(), review.getDate(), review.getBook().getId(), review.getUser().getUsername());
        }).collect(Collectors.toList());
        return listResponse;
    }
    @Override
    public List<ReviewResponse> getReviewsByBook(Long bookId) {
        Optional<Book> entity = bookRepos.findById(bookId);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the book with id " + bookId + " not found");
        }
        Book book = entity.get();

        List<Review> list = reviewRepos.findReviewByBook(book);
        List<ReviewResponse> listResponse = list.stream().map(review -> {
            if(review.getReviewDescription() != null) {
                return new ReviewResponse(review.getId(), review.getRating(), review.getReviewDescription(), review.getDate(), review.getBook().getId(), review.getUser().getUsername());
            }
            return new ReviewResponse(review.getId(), review.getRating(), review.getDate(), review.getBook().getId(), review.getUser().getUsername());
        }).collect(Collectors.toList());
        return listResponse;
    }

    
}
