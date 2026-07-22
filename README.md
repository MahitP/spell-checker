# spell-checker
A spell-checker built on a custom hash table implementation in Java. The hash table uses separate chaining with a polynomial rolling hash function, and the spell-checker benchmarks its own performance with nanosecond precision.

## 🚀 Overview
This project is a command-line spell-checker that compares text files against a dictionary of valid words. Instead of relying on standard library data structures, it features a custom-built hash table to store and retrieve dictionary entries, focusing on algorithmic efficiency and memory management.

## ✨ Features
- Custom hash table from scratch (no `java.util.HashMap`)
- Nanosecond-precision benchmarking for dictionary load time, spell-check time, and total time
- Character-level stream parsing for accurate reading of mixed text and punctuation
- Hash table statistics reporting (load factor, total words, longest chain, used buckets vs total buckets)

## ⭐ Technical Highlights
- **Separate Chaining Collision Resolution:** Uses a custom singly-linked list (`Node` class) allowing O(1) insertion at the head of each chain.
- **Polynomial Rolling Hash:** Implements a base-31 polynomial hash function optimized for ASCII text.
- **Optimized Bucket Sizing:** Uses 262,139 buckets (a prime number) to ensure good distribution and minimize collisions for a dictionary of approximately 143,000 English words.
- **O(1) Average Lookup:** Maintains constant time complexity for dictionary lookups despite hash collisions.

## 🛠️ Technical Implementation
The system consists of three main components. The `Dictionary` class manages the hash table, handling hashing, insertion, and lookups. The `Node` class provides the building blocks for the linked lists used in separate chaining. The `Speller` class handles file I/O, parsing text character by character to identify words while ignoring numbers and punctuation. It then queries the `Dictionary` to check spelling and records execution times using `System.nanoTime()`.

## 🧠 Design Decisions & Challenges
- **Separate Chaining vs Open Addressing:** Separate chaining was chosen because it simplifies deletion (if needed in the future) and prevents the primary and secondary clustering issues common in open addressing, especially as the load factor increases.
- **Polynomial Base-31 Hash:** A base-31 multiplier was selected for the rolling hash because 31 is an odd prime, which reduces the chance of information loss when multiplied, and it provides good distribution for standard ASCII character sets.
- **Character-Level File Parsing:** Rather than using `Scanner.split()`, the parser reads files character by character. This avoids the memory overhead of loading entire files or large substrings into memory and accurately handles complex punctuation and alphanumeric mixtures on the fly.
- **262,139 Buckets:** This specific prime number was chosen as a tradeoff between memory consumption and collision rate. It keeps the load factor below 1.0 for a typical English dictionary, minimizing average chain length and ensuring fast lookups without wasting excessive memory.

## 📁 Project Structure
```text
src/
├── Dictionary.java   # Custom hash table implementation
├── Node.java         # Linked list node for separate chaining
├── Speller.java      # Main execution, parsing, and benchmarking
├── dictionaries/     # Dictionary text files
├── keys/             # Expected output files
└── texts/            # Sample text files to spell-check
```

## ▶️ Running the Project
**Requirements:** Java Development Kit (JDK) 8 or higher.

1. Compile the Java files:
   ```bash
   javac src/*.java
   ```
2. Run the spell-checker with a text file (defaults to `src/dictionaries/large` dictionary):
   ```bash
   java -cp src Speller src/texts/your_text_file.txt
   ```
3. Run with a custom dictionary and text file:
   ```bash
   java -cp src Speller path/to/dictionary path/to/text
   ```

## 🔮 Future Improvements
- `BufferedReader` optimization for faster dictionary loading
- Bloom filter pre-screening to quickly rule out misspelled words before hashing
- Configurable hash functions for testing different distributions
- Spell-correction suggestions using edit distance algorithms (e.g., Levenshtein distance)

## Author
Mahit Pulavarthi — [github.com/MahitP](https://github.com/MahitP)
