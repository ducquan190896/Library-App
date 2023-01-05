package libraymanage.practice.librarypracticeapp.Service;

import java.util.List;

import libraymanage.practice.librarypracticeapp.Entity.Review;
import libraymanage.practice.librarypracticeapp.Entity.Request.ReviewRequest;
import libraymanage.practice.librarypracticeapp.Entity.Response.ReviewResponse;

public interface ReviewService {
    List<ReviewResponse> getReviews();
    List<ReviewResponse> getReviewsByBook(Long bookId);
    ReviewResponse getReviewById(Long Id);
    void createReview(ReviewRequest reviewRequest);
    void deleteReview(Long id);

}
