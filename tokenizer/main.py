from fastapi import FastAPI
from bpe import BPE

app = FastAPI()
bpe = BPE()

@app.get("/api/v1/bpe")
def bpe(text: str):
    return bpe.tokenize(text)


