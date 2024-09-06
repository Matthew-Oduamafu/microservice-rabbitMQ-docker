package io.nerdbyteslns.companyms.company.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "REVIEW-MICROSERVICE", url = "${review-service.url}")
public interface ReviewClients {
    @GetMapping("/reviews/averageRating/{companyId}")
    public Double getAverageReview(@PathVariable Long companyId);
}
