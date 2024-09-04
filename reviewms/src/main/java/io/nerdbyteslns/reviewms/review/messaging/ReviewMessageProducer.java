package io.nerdbyteslns.reviewms.review.messaging;

import io.nerdbyteslns.reviewms.review.Review;
import io.nerdbyteslns.reviewms.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review review, double averageRating) {
        ReviewMessage reviewMessage = new ReviewMessage(review.getId(), review.getTitle(), review.getDescription(), review.getRating(), review.getCompanyId());
        reviewMessage.setAverageRating(averageRating);
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
    }
}
