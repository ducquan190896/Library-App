package libraymanage.practice.librarypracticeapp.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import libraymanage.practice.librarypracticeapp.Entity.Review;
import libraymanage.practice.librarypracticeapp.Entity.Request.ReviewRequest;
import libraymanage.practice.librarypracticeapp.Entity.Response.ReviewResponse;
import libraymanage.practice.librarypracticeapp.Service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<List<ReviewResponse>> getAll() {
        return new ResponseEntity<List<ReviewResponse>>(reviewService.getReviews(), HttpStatus.OK);

    }
    @PreAuthorize("permitAll()")
    @GetMapping("/book/{id}")
    public ResponseEntity<List<ReviewResponse>> getByBook(@PathVariable Long id) {
        return new ResponseEntity<List<ReviewResponse>>(reviewService.getReviewsByBook(id), HttpStatus.OK);
        
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ReviewResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<ReviewResponse>(reviewService.getReviewById(id), HttpStatus.OK);
        
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> addReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        reviewService.createReview(reviewRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<HttpStatus>( HttpStatus.OK);
        
    }


}
