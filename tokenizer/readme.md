# Tokenizer

A small Python implementation of byte pair encoding (BPE), exposed through FastAPI.

## How it works

[`bpe.py`](bpe.py) splits input into words and punctuation, counts their frequencies, and repeatedly merges the most frequent adjacent character or token pair. It performs up to 1,000 merges by default and returns a mapping from each unique text fragment to its resulting tokens. Merges are learned from each input; there is no saved vocabulary or token ID encoding.

## Setup

Use Python 3.10 or newer. From this directory:

```sh
python3 -m venv .venv
source .venv/bin/activate
python -m pip install -r requirements.txt
```

## Use the tokenizer directly

```python
from bpe import BPE

tokenizer = BPE(max_runs=10)
print(tokenizer.tokenize("hello hello world"))
```

## FastAPI endpoint

Start the development server:

```sh
python -m uvicorn main:app --reload
```

The declared endpoint is `GET /api/v1/bpe?text=...`, with interactive API documentation at <http://localhost:8000/docs>.

Known issue: in [`main.py`](main.py), the handler function named `bpe` replaces the `BPE` instance stored under the same name. Requests currently fail when the handler calls `bpe.tokenize(text)`. Rename the handler or instance before using the HTTP endpoint. Direct use of `BPE` is unaffected.

[Back to project index](../readme.md)
