# JP Morgan & Chase Interview
2020-11-19

# Problem Statement
Given a prison diagram like

```
    ###...##..
   ....#....#
   #..###.#.#
   S#.#.#.#..
   ...#...E#.
```
where
* \# = wall
* . = empty space
* S = start
* E = finish 

Find the shortest path from S to E and then replace the exit route with *

# Postmortem
This was my first real coding assessment in awhile and I definitely noticed it.
It wasn't the actually algorithm that made things difficult but I felt slower
while implementing it and definitely had to look up some type definitions
and Stream operations that didn't fit with my recent work with Typescript.

There was also some major architecture issues. For example, if there is a path
it's printed within the BFS function but if there isn't, "TRAPPED" is 
printed inside the main function. Very messy. 

Also I felt a little constrained by the HireVue editor. I'm used to seperating
classes out into seperate files where each class has a specific function
but everything felt extremely clunky as one big MonoClass with static classes
inside.

Also, I was a little short sighted with my BFS implementation. I latched onto the
distance from S for each node, but BFS guarantees the first path found is the shortest
so it was a little irrelevant.