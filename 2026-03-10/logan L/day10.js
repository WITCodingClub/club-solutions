import fs from 'fs';
const input = fs.readFileSync('input.txt', 'utf8').trim().split(/\r?\n/);
const openToClose = { "(": ")", "[": "]", "{": "}", "<": ">" };
const errorScore = { ")": 3, "]": 57, "}": 1197, ">": 25137 };
let total = 0;
for (const line of input) {
  const stack = [];
  for (const ch of line) {
    if (openToClose[ch]) {
      stack.push(ch);
      continue;
    }
    const open = stack.pop();
    if (!open || openToClose[open] !== ch) {
      total += errorScore[ch] || 0;
      break;
    }
  }
}
console.log(`error score: ${total}`);
