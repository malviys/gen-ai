import re

class BPE:
    """
    Byte Pair Encoding tokenizer, traverse through entire text and find the most frequent pairs,
    merge those pairs 
    """
    
    _MAX_RUNS = 1000
    
    def __init__(self, max_runs = _MAX_RUNS):
        # split word by space & special characters
        self.REGEX = re.compile(r"\s*\w+|[^\w\s]")
        self.max_runs = max_runs

    def tokenize(self, text: str):
        word_count = self._count_words(text)
        word_splits = {word: [*word] for word in word_count.keys()}
        
        for _ in range(self.max_runs):
            pair_freq: dict[str, int] = {}
            
            for [word, tokens] in word_splits.items():
                weight = word_count[word]
                
                for i in range(len(tokens) - 1):
                    key = tokens[i] + "\0" + tokens[i+1]
                    pair_freq[key] = pair_freq.get(key, 0) + weight
            
            if not len(pair_freq):
                break
            
            best_key = ""
            best_count = 0
            
            for [key, count] in pair_freq.items():
                if count > best_count:
                    best_count = count
                    best_key = key
                    
            [a, b] = best_key.split("\0")
            merged = a + b
            
            for [word, tokens] in word_splits.items():
                word_splits[word] = self._merge_tokens(tokens, (a, b), merged)
                
        return word_splits
        
        
    def _count_words(self, text: str):
        """
            Give text, counts the frequency of each word
        """
        
        matches: list[str] = self.REGEX.findall(text)

        return {word: matches.count(word) for word in matches}
    
    def _merge_tokens(self, tokens: list[str], pair: tuple[str, str], merged: str):
        """
        Given list of tokens if two token matches with the pair we will merge those pairs
        """
        results: list[str] = []
        
        i = 0
        
        while i < len(tokens):
            if i < len(tokens) - 1 and tokens[i] == pair[0] and tokens[i+1] == pair[1]:
                results.append(merged)
                i += 2
                
            else:
                results.append(tokens[i])
                i += 1            
                
                
        return results
        