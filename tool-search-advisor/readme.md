# Tool Search Advisor

A Spring Boot and Spring AI demo of tool calling with a custom `AvailableToolsLoggingAdvisor`. It registers a date/time tool, logs the tools available before a model call, and logs tool calls present in the response.

The current implementation demonstrates advisor logging; it does not configure a searchable tool index.

## Prerequisites

- JDK 26, as configured in [`pom.xml`](pom.xml).
- A Google GenAI API key and an available chat model ID.
- Network access to download Maven dependencies and call the model provider.

## Run

From this directory, set the values referenced by [`application.yaml`](src/main/resources/application.yaml):

```sh
export GOOGLE_GENAI_API_KEY='your-api-key'
export GOOGLE_GENAI_CHAT_MODEL='your-chat-model-id'
./mvnw spring-boot:run
```

## Try the endpoint

```sh
curl 'http://localhost:8080/before'
```

`GET /before` submits the fixed prompt, “What is the capital of India and current date & time”, and returns the model's response as text. It does not accept a custom query. Check the application logs for available tools and tool calls.

[`DateTimeTools`](src/main/java/com/malviys/tool_search_advisor/tools/DateTimeTools.java) uses Spring's locale-context timezone to produce the current local date and time.

[Back to project index](../readme.md)
