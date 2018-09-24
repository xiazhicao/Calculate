This is the Calculator project.
================================

What the function it can implement listed as below:
-----------------------------------------------------------------
* The calculator waits for user input and expects to receive strings containing whitespace separated lists of numbers and
operators.
* Available operators are +, -, *, /, sqrt, undo, clear.
* The ¡®clear¡¯ operator removes all items from the stack.
* The ¡®undo¡¯ operator undoes the previous operation. ¡°undo undo¡± will undo the previo us two operations.
* sqrt performs a square root on the top item from the stack.

Example
-------------------------------------------------------------
#####Example 1
5 2  
stack: 5 2

#####Example 2
2 sqrt  
stack: 1.4142135623  
clear 9 sqrt  
stack: 3

#####Example 3
5 2 -  
stack: 3  
3 -  
stack: 0  
clear  
stack:
