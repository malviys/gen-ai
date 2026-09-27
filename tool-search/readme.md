# Tool Search

A Spring Boot and Spring AI demo using Google GenAI to call utility tools. It includes a route that explicitly attaches a Lucene-backed `ToolSearchToolCallingAdvisor` and logs prompt, completion, and total token usage through `TokenConsumedAdvisor`.

The registered tools cover date and time, decisions, encoding, lifestyle, math, productivity, public information, text, units, and validation.

## Prerequisites

- JDK 27, as configured in [`pom.xml`](pom.xml).
- A Google GenAI API key and an available chat model ID.
- Network access to download Maven dependencies and call the model provider.

## Run

From this directory, set the values referenced by [`application.yaml`](src/main/resources/application.yaml):

```sh
export GOOGLE_GENAI_API_KEY='your-api-key'
export GOOGLE_GENAI_MODEL='your-chat-model-id'
./mvnw spring-boot:run
```

## Try the endpoints

Send a prompt with the registered tools:

```sh
curl --get 'http://localhost:8080/' \
  --data-urlencode 'query=What is the current date and time?'
```

Send a prompt through the route that explicitly adds the tool search advisor:

```sh
curl --get 'http://localhost:8080/search-advisor' \
  --data-urlencode 'query=What is the current date and time?'
```

Both endpoints require the `query` parameter and return the model's response as text. Check the application logs for token usage. The configuration also enables the tool search advisor with a Lucene index; account for that setting when comparing the routes.

[Back to project index](../readme.md)
