# RAG Demo

A retrieval-augmented generation (RAG) demo built with Spring Boot, Spring AI, Google GenAI, and PostgreSQL with pgvector.

At startup, `IngestionService` reads a PDF, splits its text into chunks with `TokenTextSplitter`, and adds them to the vector store. The chat client uses `QuestionAnswerAdvisor` to retrieve context from that store when answering a query.

## Prerequisites

- JDK 27, as configured in [`pom.xml`](pom.xml).
- Docker with Docker Compose available and the Docker daemon running.
- A Google GenAI API key and an available chat model ID.
- Network access to download dependencies and the database image, and to call the model provider.
- The PDF expected by the ingestion service (see below).

## Setup and run

1. Place a PDF at `src/main/resources/docs/article_thbeatoct2024.pdf`. This file is currently missing from the project. To use a different path, update the resource reference in [`IngestionService.java`](src/main/java/com/malviys/rag_demo/services/IngestionService.java).
2. From this directory, set the environment variables and start the application:

   ```sh
   export GOOGLE_GENAI_API_KEY='your-api-key'
   export GOOGLE_GENAI_MODEL='your-chat-model-id'
   ./mvnw spring-boot:run
   ```

The project includes Spring Boot Docker Compose integration and [`compose.yaml`](compose.yaml) for a PostgreSQL 16 pgvector service on port `5432`. The Compose configuration uses the database `markets` and local demo credentials `root` / `root`. Vector-store schema initialization is enabled in [`application.yaml`](src/main/resources/application.yaml).

## Ask a question

Once startup and document ingestion finish:

```sh
curl --get 'http://localhost:8080/' \
  --data-urlencode 'query=Summarize the main points of the document.'
```

`GET /` requires a `query` parameter and returns the model's response as text.

Ingestion runs on every application startup without a deduplication check, so restarting against an existing database can add duplicate chunks. There is currently no PDF upload endpoint.

[Back to project index](../readme.md)
