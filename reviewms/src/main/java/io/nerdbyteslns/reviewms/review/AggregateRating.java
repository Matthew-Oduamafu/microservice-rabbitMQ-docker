package io.nerdbyteslns.reviewms.review;


import jakarta.persistence.*;


@Entity
@Table(name = "aggregate_rating_tbl")
public class AggregateRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private double averageRating;
    private int totalReviews;
    private int oneStarCount;
    private int twoStarCount;
    private int threeStarCount;
    private int fourStarCount;
    private int fiveStarCount;

    public AggregateRating() {
        this.totalReviews = 0;
    }

    public AggregateRating(Long id, Long companyId, double averageRating, int totalReviews) {
        this.id = id;
        this.companyId = companyId;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
    }

    public AggregateRating(Long companyId, double averageRating, int totalReviews, int oneStarCount, int twoStarCount, int threeStarCount, int fourStarCount, int fiveStarCount) {
        this.companyId = companyId;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
        this.oneStarCount = oneStarCount;
        this.twoStarCount = twoStarCount;
        this.threeStarCount = threeStarCount;
        this.fourStarCount = fourStarCount;
        this.fiveStarCount = fiveStarCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public int getOneStarCount() {
        return oneStarCount;
    }

    public void setOneStarCount(int oneStarCount) {
        this.oneStarCount = oneStarCount;
    }

    public int getTwoStarCount() {
        return twoStarCount;
    }

    public void setTwoStarCount(int twoStarCount) {
        this.twoStarCount = twoStarCount;
    }

    public int getThreeStarCount() {
        return threeStarCount;
    }

    public void setThreeStarCount(int threeStarCount) {
        this.threeStarCount = threeStarCount;
    }

    public int getFourStarCount() {
        return fourStarCount;
    }

    public void setFourStarCount(int fourStarCount) {
        this.fourStarCount = fourStarCount;
    }

    public int getFiveStarCount() {
        return fiveStarCount;
    }

    public void setFiveStarCount(int fiveStarCount) {
        this.fiveStarCount = fiveStarCount;
    }
}
