# Minesweeper Game: #

The user can either expose a cell or seal it. The user seals a cell suspected
to contain a mine. The user can seal and unseal any unexposed cell at anytime.
The game ends with the user losing if a mined cell is exposed. The game ends
with the user winning if all mined cells are sealed and all other cells are
exposed.

When an empty cell is exposed, all empty and adjacent cells next to that cell are exposed. This may result in several cells being exposed as a result. When
a user selects a cell, it is exposed. When an adjacent cell is exposed, no further cells are exposed as a result of that cell being exposed. When a mined
cell is exposed, game ends.

---

This is an educational project to get familiar with the test driven development. The development is entirely driven using unit testing.