package com.malviys.movie_search.models;

import java.util.Map;
import java.util.Set;

public record MovieEmbedding(Long id, String title, Map<Integer, Double> embedding) {
}
