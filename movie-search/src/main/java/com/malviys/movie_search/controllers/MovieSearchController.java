package com.malviys.movie_search.controllers;

import com.malviys.movie_search.models.MovieResult;
import com.malviys.movie_search.services.MovieSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieSearchController {
    private static final Logger logger = LoggerFactory.getLogger(MovieSearchController.class);

    private final MovieSearchService movieSearchService;

    public MovieSearchController(MovieSearchService movieSearchService) {
        this.movieSearchService = movieSearchService;
    }

    @GetMapping("/search")
    public List<MovieResult> search(@RequestParam String query, @RequestParam(defaultValue = "10") int topK) {
        var safeTopK = Math.clamp(topK, 1, 100);

        return movieSearchService.search(query, safeTopK);
    }
}