# Galton board — combinatorics + binomial probability

## Structure
- `algorithm.py` — the math: `nCk`, `binomial_pmf`, `generate_path` (one random ball trial), `simulate_binomial`. All randomness and probability computation lives here.
- `index.html` — page structure and controls.
- `style.css` — all styling.
- `script.js` — UI wiring, canvas rendering, and animation only. It never computes probabilities itself — it calls into `algorithm.py` (loaded through Pyodide, Python compiled to WebAssembly) for every number shown.

## Running it
Browsers block `fetch()` of local files opened directly (`file://...`), and `script.js` fetches `algorithm.py` at startup. So serve the folder instead of double-clicking `index.html`:

```
cd galton-board
python3 -m http.server 8000
```

Then open `http://localhost:8000`. Most code editors (VS Code's "Live Server", for example) offer the same one click.
