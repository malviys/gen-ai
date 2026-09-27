# Generative AI demos

A collection of projects exploring generative AI.

Read the [Generative AI guide](https://www.malviys.com/blog/gen-ai) for guidance on these topics.

| Project | Description |
| --- | --- |
| [tokenizer](tokenizer/) | A Python byte pair encoding (BPE) implementation with a FastAPI endpoint. |
| [tool-search](tool-search/) | A Spring AI demo with utility tools, a Lucene-backed tool search advisor, and token usage logging. |
| [tool-search-advisor](tool-search-advisor/) | A Spring AI demo that logs available tools and model tool calls using a custom advisor. |
| [rag-demo](rag-demo/) | A Spring AI demo that ingests a PDF into PostgreSQL with pgvector and retrieves context for chat responses. |
| [movie-search](movie-search/) | A Spring Boot demo that ranks movies using a local feature dictionary and cosine similarity. |

Each directory has its own `readme.md` with prerequisites, setup instructions, and usage examples. Run commands from the relevant project directory. The Java demos use port `8080` by default, so run them one at a time or configure different ports.
