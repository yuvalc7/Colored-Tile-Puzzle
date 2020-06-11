# Colored-Tile-Puzzle
implement the Tile Puzzle solving using BFS,DFID,A*,IDA*,DFBnB algorithms. 

Unlike the usual game where each sliding "cost" one ,
in this game each sliding "cost" is depend on the color of the block.

### there are 3 possible colors for each block:

black - Can't move at all

red - move cost 30

green - move cost 1 

## input
first line of the file - determine which algorithm to use 

seconde line of the file - with time or without time for algorithm running time

third line of file - print or not print to screen the open list

fourth line of file - contain the size of the board   and then the list of numbers for all the blocks is red.

fifth line of the file - the blocks that black

sixth line of the file - the blocks that red (all other blocks are green)

then the initial order of the board will appear in rows, with commas in between the block numbers. The blank block will be marked as. "_"

## output

The first line of the file - the series of operations found by the algorithm

seconde line of the file - The number of vertices produced includes vertices that did not enter the openlist

third line of file - the cost of the solution

fourth line of file - the run time that take the algorithm depend on seconde line in input

### remark
The order of creating vertices with same "parent" will be according to the operator who created them in the following order: left , up , right , down

If the value of f(n) equals
The priority will be given in the order of production of the vertices

