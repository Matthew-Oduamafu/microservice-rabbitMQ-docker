package io.nerdbyteslns.reviewms.review;


import io.nerdbyteslns.reviewms.review.dto.CreateReviewDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
@Tag(name = "Reviews", description = "Reviews API")
public class ReviewController {

    private final ReviewService reviewService;
    private final AggregateRatingService aggregateRatingService;

    public ReviewController(ReviewService reviewService, AggregateRatingService aggregateRatingService) {
        this.reviewService = reviewService;
        this.aggregateRatingService = aggregateRatingService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> findAllByCompanyId(@RequestParam Long companyId) {
        return ResponseEntity.ok(reviewService.findAllByCompanyId(companyId));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestParam Long companyId, @RequestBody CreateReviewDto review) {
        var response = reviewService.create(companyId, review);
        if (response == null) {
            return ResponseEntity.badRequest().body("Company not found");
        }
        return ResponseEntity.ok("Review created successfully");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> findById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.findById(reviewId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteByIdAndCompanyId(@PathVariable Long reviewId) {
        if (reviewService.deleteById(reviewId)) {
            return ResponseEntity.ok("Review deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Review review) {
        if (reviewService.update(id, review)) {
            return ResponseEntity.ok("Review updated successfully");
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating/{companyId}")
    public ResponseEntity<Double> getAverageReview(@PathVariable Long companyId) {
        AggregateRating aggregateRating = aggregateRatingService.getAggregateRatingByCompanyId(companyId);
        if (aggregateRating == null) {
            return ResponseEntity.ok(0.0);
        }
        return ResponseEntity.ok(aggregateRating.getAverageRating());
    }
}
