package com.devsuperior.movieflix.controller;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    public MovieService movieService;

    @GetMapping("/{id}")
    public MovieDTO getMovieBy(@PathVariable Long id) {
        return movieService.getMovieBy(id);
    }

    @GetMapping
    public Page<MovieDTO> getMovies(@RequestParam(required = false) Long genreId,
                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                    @RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
                                    @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return movieService.getMovies(pageRequest, genreId);
    }

    @GetMapping("/{id}/reviews")
    public Page<ReviewDTO> getReviews(@PathVariable Long id,
                                      @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                      @RequestParam(value = "orderBy", defaultValue = "text") String orderBy,
                                      @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return movieService.getReviews(id, pageRequest);
    }

}
