# Movie Search

A Spring Boot demo that searches a bundled movie catalog using keyword features and cosine similarity. The search runs in memory using local JSON data and requires no model API key or database.

Read the [Generative AI guide](https://www.malviys.com/blog/gen-ai) for related background.

## Prerequisites

- JDK 27, as configured in [`pom.xml`](pom.xml).
- Network access to download Maven and project dependencies on the first run.

## Run

From this directory:

```sh
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd spring-boot:run`. The application uses port `8080` by default.

## Search for movies

```sh
curl --get 'http://localhost:8080/api/movies/search' \
  --data-urlencode 'query=space astronaut survival' \
  --data-urlencode 'topK=5'
```

| Parameter | Description |
| --- | --- |
| `query` | Required search text. Matching is case-insensitive and uses words in the feature dictionary. |
| `topK` | Maximum number of results; defaults to `10` and is clamped to the range `1`–`100`. |

The endpoint returns a JSON array of objects containing `id`, `title`, and `score`, sorted by descending similarity. Only movies with a positive score are included, so fewer than `topK` results may be returned. Blank queries or queries with no recognized features return `[]`.

## How it works

1. At startup, [`MovieSearchService`](src/main/java/com/malviys/movie_search/services/MovieSearchService.java) loads the [feature dictionary](src/main/resources/embeddings/feature_embedding.json) and [movie embeddings](src/main/resources/embeddings/movies_embedding.json).
2. The query is lowercased and split on characters other than ASCII letters and digits. Recognized words become a set of feature IDs; repeated words count once.
3. Each movie is scored using cosine similarity between the query's feature set and the movie's feature set: `shared feature count / sqrt(query feature count * movie feature count)`.
4. Positive matches are sorted and limited to the requested result count.

The current scoring uses the feature IDs present in each movie's embedding, ignoring their numeric weights. Matching depends on exact dictionary words; there is no stemming, synonym expansion, or model-generated query embedding.

## Update the catalog

Add or edit movies in `src/main/resources/embeddings/movies_embedding.json`, using feature IDs from `feature_embedding.json`. Keep the dictionary and movie feature IDs consistent, then restart the application to reload the data.

[Back to project index](../readme.md)
