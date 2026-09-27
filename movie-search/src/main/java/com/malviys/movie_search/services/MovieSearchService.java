package com.malviys.movie_search.services;

import com.malviys.movie_search.models.MovieEmbedding;
import com.malviys.movie_search.models.MovieResult;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class MovieSearchService {
    private static final Logger logger = LoggerFactory.getLogger(MovieSearchService.class);

    private static final Pattern TOKEN_SPLITTER = Pattern.compile("[^a-zA-Z0-9]+");

    private final ObjectMapper objectMapper;

    private Map<String, Integer> featureEmbeddings;

    private List<MovieEmbedding> movieEmbeddings;

    public MovieSearchService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadEmbeddings() throws IOException {
        loadFeatureEmbeddings();
        loadMovieEmbeddings();

        logger.info("Loaded {} features and {} movies%n", featureEmbeddings.size(), movieEmbeddings.size());
    }

    private void loadFeatureEmbeddings() throws IOException {
        var resource = new ClassPathResource("embeddings/feature_embedding.json");

        record FeatureEmbeddingStore(Map<String, Integer> features) {
        }

        try (InputStream inputStream = resource.getInputStream()) {
            var featureEmbeddingStore = objectMapper.readValue(inputStream, FeatureEmbeddingStore.class);

            // Normalize keys once during startup
            var normalized = new HashMap<String, Integer>();

            featureEmbeddingStore.features.forEach((word, index) -> normalized.put(word.toLowerCase(Locale.ROOT), index));

            featureEmbeddings = Map.copyOf(normalized);
        }
    }

    private void loadMovieEmbeddings() throws IOException {
        var resource = new ClassPathResource("embeddings/movies_embedding.json");

        record MovieEmbeddingStore(List<MovieEmbedding> movies) {
        }

        try (InputStream inputStream = resource.getInputStream()) {
            var movieEmbeddingStore = objectMapper.readValue(inputStream, MovieEmbeddingStore.class);

            movieEmbeddings = List.copyOf(movieEmbeddingStore.movies);
        }
    }

    public List<MovieResult> search(String query, int topK) {
        var queryVector = createQueryVector(query);

        if (queryVector.isEmpty()) {
            return List.of();
        }

        return movieEmbeddings.stream()
                .map(movie -> new MovieResult(
                        movie.id(),
                        movie.title(),
                        cosineSimilarity(queryVector, movie.embedding().keySet())
//                        similarity(queryVector, movie.embedding())
                ))
                .filter(result -> result.score() > 0)
                .sorted(Comparator.comparingDouble(MovieResult::score).reversed())
                .limit(topK)
                .toList();
    }

    private Set<Integer> createQueryVector(String query) {
        if (query == null || query.isBlank()) {
            return Set.of();
        }

        var normalized = query.toLowerCase(Locale.ROOT);

        var tokens = TOKEN_SPLITTER.split(normalized);

        var queryFeatures = new HashSet<Integer>();

        for (var token : tokens) {
            var featureIndex = featureEmbeddings.get(token);

            if (featureIndex != null) {
                queryFeatures.add(featureIndex);
            }
        }

        return queryFeatures;
    }

    private double cosineSimilarity(Set<Integer> query, Set<Integer> movie) {
        if (query.isEmpty() || movie.isEmpty()) {
            return 0.0;
        }

        var smaller = query.size() <= movie.size() ? query : movie;

        var larger = query.size() <= movie.size() ? movie : query;

        long intersection = smaller.stream().filter(larger::contains).count();

        if (intersection == 0) {
            return 0.0;
        }

        return intersection / Math.sqrt((double) query.size() * movie.size());
    }

    private double similarity(Set<Integer> query, Map<Integer, Double> movie) {
        if(query.isEmpty() || movie.isEmpty()) {
            return 0.0;
        }

        var intersection =  query.stream().map(t -> movie.getOrDefault(t, 0.0));

        return Math.sqrt(intersection.reduce(0.0, Double::sum));
    }
}