package io.nerdbyteslns.firstjobapp.review;


import io.nerdbyteslns.firstjobapp.company.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies/{companyId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> findAllByCompanyId(@PathVariable Long companyId) {
        return ResponseEntity.ok(reviewService.findAllByCompanyId(companyId));
    }

    @PostMapping
    public ResponseEntity<String> create(@PathVariable Long companyId, @RequestBody Review review) {
        if (!reviewService.create(companyId, review)) {
            return ResponseEntity.badRequest().body("Company not found");
        }
        return ResponseEntity.ok("Review created successfully");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> findByIdAndCompanyId(@PathVariable Long companyId, @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.findByIdAndCompanyId(reviewId, companyId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteByIdAndCompanyId(@PathVariable Long companyId, @PathVariable Long reviewId) {
        if (reviewService.deleteByIdAndCompanyId(reviewId, companyId)) {
            return ResponseEntity.ok("Review deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long companyId, @PathVariable Long id, @RequestBody Review review) {
        review.setCompany(new Company(companyId));
        if (reviewService.update(id, review)) {
            return ResponseEntity.ok("Review updated successfully");
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }
}
