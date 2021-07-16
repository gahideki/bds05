package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repository.MovieRepository;
import com.devsuperior.movieflix.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public MovieDTO getMovieBy(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        return new MovieDTO(movie);
    }

    public Page<MovieDTO> getMovies(Pageable pageable, Long genreId) {
        Page<Movie> movies;
        if (ObjectUtils.isEmpty(genreId)) {
            movies = movieRepository.findAll(pageable);
        } else {
            movies = movieRepository.findMoviesByGenreId(genreId, pageable);
        }
        return movies.map(MovieDTO::new);
    }

    @Transactional
    public Page<ReviewDTO> getReviews(Long id, PageRequest pageRequest) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        List<Review> reviews = movie.getReviews();
        Page<Review> pageReviews = new PageImpl<>(reviews, pageRequest, reviews.size());
        return pageReviews.map(ReviewDTO::new);
    }

}
