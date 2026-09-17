// script.js
// Owns rendering + UI wiring only. Every combinatorial / probabilistic
// computation (nCk, binomial pmf, each ball's random path) happens in
// Python (see algorithm.py), loaded here through Pyodide.

const els = {
  status: document.getElementById('status'),
  nRange: document.getElementById('n-range'),
  nOut: document.getElementById('n-out'),
  pRange: document.getElementById('p-range'),
  pOut: document.getElementById('p-out'),
  kSelect: document.getElementById('k-select'),
  drop1: document.getElementById('drop1'),
  drop50: document.getElementById('drop50'),
  reset: document.getElementById('reset'),
  trials: document.getElementById('stat-trials'),
  exact: document.getElementById('stat-exact'),
  sim: document.getElementById('stat-sim'),
  canvas: document.getElementById('board'),
};
const ctx = els.canvas.getContext('2d');

let pyodide, pyGeneratePath, pyBinomialPmf;
let n = 10, p = 0.5, k = 3, trials = 0, binCounts = [];
let active = [];

const W = 640, H = 480, boardH = 300, binsTop = 310, binsH = 140;

function isDark() {
  return matchMedia('(prefers-color-scheme: dark)').matches;
}
function colors() {
  return isDark()
    ? { peg: '#5f5e5a', bar: '#7f77dd', hi: '#f5c4b3', text: '#c3c2b7', ball: '#85b7eb' }
    : { peg: '#b4b2a9', bar: '#7f77dd', hi: '#d85a30', text: '#5f5e5a', ball: '#378ade' };
}

async function boot() {
  els.status.textContent = 'Loading Python runtime…';
  pyodide = await loadPyodide();
  const src = await (await fetch('algorithm.py')).text();
  await pyodide.runPythonAsync(src);
  pyGeneratePath = pyodide.globals.get('generate_path');
  pyBinomialPmf = pyodide.globals.get('binomial_pmf');

  els.status.textContent = 'Python runtime ready (algorithm.py loaded via Pyodide).';
  [els.drop1, els.drop50, els.reset].forEach((b) => (b.disabled = false));

  rebuildKSelect();
  reset();
  requestAnimationFrame(tick);
}

function rebuildKSelect() {
  els.kSelect.innerHTML = '';
  for (let i = 0; i <= n; i++) {
    const opt = document.createElement('option');
    opt.value = i;
    opt.textContent = i;
    if (i === k) opt.selected = true;
    els.kSelect.appendChild(opt);
  }
}

function reset() {
  trials = 0;
  active = [];
  binCounts = new Array(n + 1).fill(0);
  if (k > n) k = n;
  updateStats();
  draw();
}

function pegSpacing() {
  return (W - 80) / (n + 1);
}
function centerX() {
  return W / 2;
}

function binomialPmf(nn, kk, pp) {
  return pyBinomialPmf(nn, kk, pp);
}

function drawPegs(cols) {
  const sp = pegSpacing();
  const rowGap = (boardH - 30) / n;
  for (let r = 0; r < n; r++) {
    const count = r + 1;
    for (let c = 0; c < count; c++) {
      const xOff = 2 * c - (count - 1);
      const x = centerX() + (xOff * sp) / 2;
      const y = 20 + r * rowGap;
      ctx.beginPath();
      ctx.arc(x, y, 3, 0, Math.PI * 2);
      ctx.fillStyle = cols.peg;
      ctx.fill();
    }
  }
}

function drawBins(cols) {
  const sp = pegSpacing();
  const maxCount = Math.max(1, ...binCounts);
  const barW = Math.min(sp * 0.8, 30);
  for (let i = 0; i <= n; i++) {
    const xOff = 2 * i - n;
    const x = centerX() + (xOff * sp) / 2;
    const h = (binCounts[i] / maxCount) * (binsH - 30);
    ctx.fillStyle = i === k ? cols.hi : cols.bar;
    ctx.fillRect(x - barW / 2, binsTop + binsH - h, barW, h);

    const expected = binomialPmf(n, i, p) * trials;
    const ey = binsTop + binsH - (expected / maxCount) * (binsH - 30);
    ctx.beginPath();
    ctx.arc(x, ey, 3, 0, Math.PI * 2);
    ctx.fillStyle = cols.text;
    ctx.fill();

    ctx.fillStyle = cols.text;
    ctx.font = '11px sans-serif';
    ctx.textAlign = 'center';
    ctx.fillText(i, x, binsTop + binsH + 14);
  }
}

function draw() {
  ctx.clearRect(0, 0, W, H);
  const cols = colors();
  drawPegs(cols);
  for (const b of active) {
    ctx.beginPath();
    ctx.arc(b.x, b.y, 5, 0, Math.PI * 2);
    ctx.fillStyle = cols.ball;
    ctx.fill();
  }
  drawBins(cols);
}

function updateStats() {
  els.trials.textContent = trials;
  els.exact.textContent = binomialPmf(n, k, p).toFixed(4);
  els.sim.textContent = trials > 0 ? (binCounts[k] / trials).toFixed(4) : '–';
}

function spawnBall() {
  // The only random decisions happen in Python: generate_path() flips
  // a p-weighted coin n times and returns the running success count
  // per row (for animation) plus the final bin.
  const resultProxy = pyGeneratePath(n, p);
  const result = resultProxy.toJs({ dict_converter: Object.fromEntries });
  resultProxy.destroy();
  active.push({ row: 0, path: result.path, bin: result.bin, x: centerX(), y: 20, t: 0 });
}

function tick() {
  const sp = pegSpacing();
  const rowGap = (boardH - 30) / n;
  const done = [];
  for (let i = active.length - 1; i >= 0; i--) {
    const b = active[i];
    b.t += 1;
    if (b.t % 3 === 0 && b.row < n) b.row++;
    const succSoFar = b.row === 0 ? 0 : b.path[Math.min(b.row - 1, n - 1)];
    const xOff = b.row === 0 ? 0 : 2 * succSoFar - b.row;
    b.x = centerX() + (xOff * sp) / 2;
    b.y = 20 + Math.min(b.row, n) * rowGap;
    if (b.row >= n && b.t % 3 === 0) {
      binCounts[b.bin]++;
      trials++;
      done.push(i);
    }
  }
  for (const j of done) active.splice(j, 1);
  if (done.length) updateStats();
  draw();
  requestAnimationFrame(tick);
}

els.nRange.addEventListener('input', (e) => {
  n = parseInt(e.target.value, 10);
  els.nOut.textContent = n;
  rebuildKSelect();
  reset();
});
els.pRange.addEventListener('input', (e) => {
  p = parseFloat(e.target.value);
  els.pOut.textContent = p.toFixed(2);
  updateStats();
  draw();
});
els.kSelect.addEventListener('change', (e) => {
  k = parseInt(e.target.value, 10);
  updateStats();
  draw();
});
els.drop1.addEventListener('click', spawnBall);
els.drop50.addEventListener('click', () => {
  for (let i = 0; i < 50; i++) setTimeout(spawnBall, i * 40);
});
els.reset.addEventListener('click', reset);

boot();
