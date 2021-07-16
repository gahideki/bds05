package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repository.MovieRepository;
import com.devsuperior.movieflix.repository.ReviewRepository;
import com.devsuperior.movieflix.service.exception.ResourceExistsException;
import com.devsuperior.movieflix.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasAnyRole('MEMBER')")
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        User userAuthenticated = authService.authenticated();
        validateExistsReview(reviewDTO, userAuthenticated.getId());

        Review review = new Review();
        review.setUser(userAuthenticated);
        copyDtoToEntity(reviewDTO, review);
        review = reviewRepository.save(review);
        return new ReviewDTO(review);
    }

    private void validateExistsReview(ReviewDTO reviewDTO, Long userId) {
        if (reviewRepository.findReviewByTextAndMovieIdAndUserId(reviewDTO.getText(), reviewDTO.getMovieId(), userId).isPresent())
            throw new ResourceExistsException("Have you done this review");
    }

    private void copyDtoToEntity(ReviewDTO reviewDTO, Review review) {
        Movie movie = movieRepository.findById(reviewDTO.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        review.setText(reviewDTO.getText());
        review.setMovie(movie);
    }

}
