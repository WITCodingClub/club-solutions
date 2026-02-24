import fs from 'fs';
function solve() {
    const data = fs.readFileSync('input.txt', 'utf8');
    const elfGroups = data.split('\n\n');
    const elves = elfGroups.map(elf => {
        const lines = elf.split('\n');
        const calories = lines.filter(line => line.trim());
        const total = calories.reduce((sum, cal) => sum + parseInt(cal), 0);
        return total;
    });
    const max = Math.max(...elves);
    console.log(max);
}
solve();
