package com.devsuperior.movieflix.controller;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReviewDTO create(@Valid @RequestBody ReviewDTO review) {
        return reviewService.createReview(review);
    }

}
