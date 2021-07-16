package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<GenreDTO> getAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(GenreDTO::new).collect(Collectors.toList());
    }

}
