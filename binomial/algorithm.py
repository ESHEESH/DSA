"""
algorithm.py
Core combinatorics + binomial-probability logic for the Galton board demo.

This is the same algorithm as the C++ / Java lab versions (nCk computed
safely with the multiplicative formula, then the binomial pmf built on top
of it). It runs in the browser via Pyodide, so script.js calls straight
into these functions instead of re-implementing the math in JavaScript.
"""

import random


def nCk(n: int, k: int) -> float:
    """n choose k, computed without big factorials (numerically stable)."""
    if k < 0 or k > n:
        return 0.0
    if k > n - k:
        k = n - k
    result = 1.0
    for i in range(1, k + 1):
        result = result * (n - k + i) / i
    return result


def binomial_pmf(n: int, k: int, p: float) -> float:
    """Exact P(X = k) for X ~ Binomial(n, p)."""
    return nCk(n, k) * (p ** k) * ((1 - p) ** (n - k))


def generate_path(n: int, p: float):
    """
    Simulate one ball falling through n rows of pegs.

    At each row the ball deflects 'right' with probability p. This returns
    the running count of right-deflections after each row (used by the JS
    side to animate the ball's x-position row by row) plus the ball's final
    bin, which is exactly the number of right-deflections -- the physical
    embodiment of one binomial trial.
    """
    successes = 0
    path = []
    for _ in range(n):
        if random.random() < p:
            successes += 1
        path.append(successes)
    return {"path": path, "bin": successes}


def simulate_binomial(n: int, k: int, p: float, trials: int = 2000) -> float:
    """Estimate P(X = k) by running `trials` independent balls at once."""
    count = 0
    for _ in range(trials):
        successes = sum(1 for _ in range(n) if random.random() < p)
        if successes == k:
            count += 1
    return count / trials
