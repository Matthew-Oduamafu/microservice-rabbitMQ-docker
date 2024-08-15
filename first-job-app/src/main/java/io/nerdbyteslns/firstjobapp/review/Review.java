package io.nerdbyteslns.firstjobapp.review;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.nerdbyteslns.firstjobapp.company.Company;
import jakarta.persistence.*;

@Entity
@Table(name = "reviews_tbl")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int rating;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

    public Review() {
    }

    public Review(Long id, String title, String description, int rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
