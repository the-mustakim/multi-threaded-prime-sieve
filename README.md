# Multithreaded Prime Sieve

This Java program finds all prime numbers up to a large number `N` using the **Sieve of Eratosthenes** algorithm. It is designed using the **Producer-Consumer pattern** and optimized to run efficiently on multiple cores.

## 🔧 Features

- Efficient use of multiple threads based on available CPU cores.
- Uses Java's `BlockingQueue` for thread-safe communication.
- Tracks execution time and number of primes found.
- Implements a single Producer and multiple Consumers.

## 🚀 Usage

### 1. Compile:
```bash
javac Prime_Sieve.java
