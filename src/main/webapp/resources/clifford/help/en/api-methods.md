* `Solver` 
  An empty class with one method — you'll have to fill it with smart logic.
* `Direcion` 
  Possible commands for this game.
* `Point`
  `x`, `y` coordinates.
* `Element` 
  Type of the element on the board.
* `Board` 
  Encapsulating the line with useful methods for searching
  elements on the board. The following methods can be found in the board:
* `int boardSize();` 
  Size of the board.
* `boolean isAt(Point point, Element element);` 
  Whether the given element has given coordinate?
* `boolean isAt(Point point, Collection<Element>elements);` 
  Whether any object from the given set is located in given coordinate?
* `boolean isNear(Point point, Element element);` 
  Whether the given element is located near the cell with the given coordinate?
* `int countNear(Point point, Element element);` 
  How many elements of the given type exist around the cell with giv en coordinate?
* `Element getAt(Point point);` 
  Element in the current cell.
* `Point getHero();` 
  Position of my hero on the board.
* `boolean isGameOver();` 
  Whether my hero is alive?
* `Collection<Point> getOtherHeroes();` 
  Positions of all other heroes on the board. 
* `Collection<Point> getEnemyHeroes();` 
  Positions of all enemy heroes on the board (in case team mode). 
* `Collection<Point> getBarriers();` 
  Positions of all objects that hinder the movements.
* `boolean isBarrierAt(Point point);`
  Whether any obstacle in the cell with given coordinate exists? 
* `Collection<Point> getRobbers();` 
  Positions of all dangers that can destroy the hero.
* `Collection<Point> getWalls();` 
  Positions of all walls.
* etc