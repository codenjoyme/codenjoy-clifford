<meta charset="UTF-8">

# Game details

## What is the game about
You should write your bot for the hero who will beat the other bots
by the score it gets. All bots play on the same field of play. The
hero can move by idle cells to all four directions.

The detective can climb the stairs (up / down), as well as move 
along the pipe (left / right). You can jump down from the pipe. 
The detective falls down until he lands. The fall is safe for 
the detective.

On his way, the detective can meet clues, enemies, other players, 
back doors and potions.

The detective must also be careful, because holes shot by other 
detectives can be left on the way. The holes overgrow over time, 
but if you fall into it and do not have time to get out, there 
will be trouble! It makes no difference whose pit the detective 
fell into - once he got into the pit, he will stay in it for a 
while and immured himself, penalty points for death will be 
deducted and a new detective will appear in an arbitrary place 
on the field. If there is a void under the pit, a ladder or a 
pipe, the detective will slip through the pit without getting 
stuck.

Standing on the cage with evidence, the player gets points for 
it. The clue that the player picked up disappears from the map. 
Each tick on the map generates the same number of different 
types of evidence - there are clues: a glove, a knife and a 
ring. The number of points received for evidence depends on 
its type.

Enemies scurry around the map - these are thieves who follow 
the victim and try to catch up with it. If an evil bot catches 
up with the player, at this moment his detective goes to byte 
paradise. But not everything is so bad, already in the next 
game tick the player will receive a respawn in a random place 
on the game map.

Also, each player can shoot a hole in the destroyed wall (there 
are still indestructible ones). The fossa grows over time. 
So you can be saved from the thief.

Penalty points are also provided for the hero himself falling 
into his own or someone else's hole from which he cannot get 
out. If the hole is someone else's, the competitor will receive 
points.

If there is a shot hole and the hero can fly through it without 
getting stuck. Thus, you can collect evidence "hanging" in the air.

Points are earned by collecting evidence and eliminating 
competitors in the pits.

For each new piece of evidence, the detective gets a little 
more points than the previous one. The counter is reset when 
a hero is lost - it is beneficial to collect evidence and 
not lose a hero. Points are added up. The player with the most 
points wins (before the agreed time).

However, this is not all, and closer to the final[*](#ask-sensei) 
in the game there are a few more mechanics in store.

In particular, back doors may appear on the map. Jumping into 
the back door, in the next tick, the player moves to the place 
of another, randomly chosen back door (you cannot find out 
in advance where you will be on the map). The back doors stay 
in their original places for several game ticks, then they 
change their position at random.

Also, closer to the end[*](#ask-sensei), special potions will 
begin to appear on the map ... The player, having picked up 
such a potion (standing on the same cell with it), becomes 
disguised for several game ticks. From that moment on, he is 
not afraid of bots-thieves. Once on the same cell with them, 
the player will simply walk through them. Meeting another player 
can be more insidious. If the disguise player moves to the same 
square as the normal player, disguise kills the poor fellow. At 
the same time, the disguise player receives additional points, 
and another player who has gone into the world may, on the 
contrary, lose several points (we will decide this closer to the 
final). In the event that two disguises occur on the same cell, 
both remain alive, while they do not lose, but they do not earn 
points either.

## How to play
So, the player registers on the server and joining the game. Then you
should connect from client code to the server via Web Sockets.

Address to connect the game on the server looks like this (you can
copy it from your game room):

`http://codenjoy.com/codenjoy-contest/board/player/your-player?code=123456789012345678`

Here 'your-player' is your player id and 'code' is your security token.
Make sure you keep the code safe from prying eyes. Any participant,
knowing your code, can play on your behalf.

## Board parsing
After connection, the client will regularly (every second) receive
a line of characters with the encoded state of the field. The format:

`^board=(.*)$`

With the help of regexp you can obtain a board line. Example of the
line from the server:

<pre>board=☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼          $                 ☼☼###########################H☼☼   $              $        H☼☼H☼☼#☼☼H    H#########H $   H☼☼H $   H    H         H#####H☼☼H#☼#☼#H    H         H      ☼☼H     H~~~~H~~~~~~   H      ☼☼H     H    H  $  H###☼☼☼☼☼☼H☼☼H     H $  H#####H         H☼☼☼###☼##☼##☼H         H###H##☼☼☼###☼      H         H   H  ☼☼☼$  ☼      H   ~~~}~~H   H $☼☼########H###☼☼☼☼     H  ####☼☼        H     $      H      ☼☼###########################H☼☼    $                      H☼☼#######H#######            H☼☼       H~~~~~~~~~~     $   H☼☼       H    ##H   #######H##☼☼       H    ##H          H  ☼☼##H#####    ########H#######☼☼  H     ►   $       H       ☼☼#########H##########H       ☼☼         H          H       ☼☼       $ H~~~~~~~~~~H   $   ☼☼    H######         #######H☼☼    H           $          H☼☼###########################H☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

The line length is equal to the field square. If to insert a wrapping
character (carriage return) every sqrt(length(string)) characters,
you obtain the readable image of the field.

Field example:

<pre>☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼
☼          $                 ☼
☼###########################H☼
☼   $              $        H☼
☼H☼☼#☼☼H    H#########H $   H☼
☼H $   H    H         H#####H☼
☼H#☼#☼#H    H         H      ☼
☼H     H~~~~H~~~~~~   H      ☼
☼H     H    H  $  H###☼☼☼☼☼☼H☼
☼H     H $  H#####H         H☼
☼☼###☼##☼##☼H         H###H##☼
☼☼###☼      H         H   H  ☼
☼☼$  ☼      H   ~~~}~~H   H $☼
☼########H###☼☼☼☼     H  ####☼
☼        H     $      H      ☼
☼###########################H☼
☼    $                      H☼
☼#######H#######            H☼
☼       H~~~~~~~~~~     $   H☼
☼       H    ##H   #######H##☼
☼       H    ##H          H  ☼
☼##H#####    ########H#######☼
☼  H     ►   $       H       ☼
☼#########H##########H       ☼
☼         H          H       ☼
☼       $ H~~~~~~~~~~H   $   ☼
☼    H######         #######H☼
☼    H           $          H☼
☼###########################H☼
☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

Sprite UI

![](board.png)

The first character of the line corresponds to a cell located on the
left-top corner and has the `[0, 29]` coordinate. The following example
shows the position of the hero (the `►` character) – `[9,7]`. left-bottom
corner has the `[0, 0]` coordinate.

The game is turn-based: Each second, the server sends the updated state
of the field to the client and waits for response. Within the next
second the player must give the hero a command. If no command is
given, the hero will stand still.

Your goal is to make the hero move according to your algorithm. The
algorithm must earn points as much as possible. The ultimate goal is
winning the game.

## Symbol breakdown
Please [check it here](elements.md).

## Commands
* `UP`, `DOWN`, `LEFT`, `RIGHT` - move your hero in the specified 
  direction.
* `ACT` - shoot a hole in the floor in the direction the detective 
  is looking. Movement and `ACT` commands can be combined, separating 
  them by comma. this will indicate in which direction you need to 
  shoot the hole (without moving the detective). Combinations `LEFT,ACT` 
  or `ACT,LEFT` are same. If the player uses only one `ACT` command, 
  the hole will appear in the direction the detective is looking.
* `ACT(0)` - If the detective fell into a trap and he cannot get 
  out of there, call the command `ACT (0)` and the detective will 
  commit an act of suicide, after which he will appear in a new place.

## Points
| Action | Points |
|--------|--------|
| Number of potions on the map | 0[*](#ask-sensei) |
| Potion duration (ticks) | 15[*](#ask-sensei) |
| Number of back doors on the map | 0[*](#ask-sensei) |
| Number of ticks before changing the position of the back doors | 10[*](#ask-sensei) |
| Number of robbers | 5[*](#ask-sensei) |
| Number of clues-glove on the map | 40[*](#ask-sensei) |
| Points for clue-glove | 1[*](#ask-sensei) |
| Increment points for the next collected clues-gloves in a series without deaths | 1[*](#ask-sensei) |
| Number of clues-knife on the map | 20[*](#ask-sensei) |
| Points for clue-knife | 2[*](#ask-sensei) |
| Increment points for the next collected clues-knife in a series without deaths | 1[*](#ask-sensei) |
| Number of clues-ring on the map | 10[*](#ask-sensei) |
| Points for clue-ring | 5[*](#ask-sensei) |
| Increment points for the next collected clues-ring in a series without deaths | 1[*](#ask-sensei) |
| Death penalty | 1[*](#ask-sensei) |
| Points for killing another player (as shadow or buried) | 10[*](#ask-sensei) |
| Penalty for suicide | 10[*](#ask-sensei) |

## Cases

## <a id="ask-sensei"></a> Ask Sensei
Please ask Sensei about current game settings. You can find Sensei in
the chat that the organizers have provided to discuss issues.

## Hints
The first task is to run a client’s WebSocket which will connect to
the server. Then you should “force” the hero to listen to the commands.
This is the way prepare yourself to the game. The main goal is to
play meaningfully and win.

If you are not sure what to do try to implement the following algorithms:

* Move to a random empty adjacent cell.
* Move to a free cell in the direction of the nearest chest.
* Try to hide from bullets.
* Avoid robbers and other heroes.
* Try to shoot another heroes.
 
## Clients and API
The client code does not give a considerable handicap to gamers because
you should spend time to puzzle out the code. However, it is pleasant
to note that the logic of communication with the server plus some high
level API for working with the board are implemented already:

```
// Here:
//     Point - x, y coordinate
//     Collection - a set of several objects
//     Element - type of the element on the board

// position of my hero on the board
Point getHero();

// positions of all other heroes on the board
Collection<Point> getOtherHeroes();

// positions of all enemy heroes on the board
Collection<Point> getEnemyHeroes();

// whether my hero is alive
boolean isGameOver();

// whether the given element has given coordinate?
boolean isAt(Point point, Element element);

// whether any object from the given set is located in given coordinate
boolean isAt(Point point, Collection<Element>elements);

// whether the given element is located near the cell with the given coordinate
boolean isNear(Point point, Element element);

// whether any obstacle in the cell with given coordinate exists
boolean isBarrierAt(Point point);

// how many elements of the given type exist around the cell with given coordinate
int countNear(Point point, Element element);

// returns the element in the current cell
Element getAt(Point point);

// returns the size of the board
int boardSize();

// the coordinates of all objects that hinder the movements
Collection<Point> getBarriers();

// coordinates of all dangers that can destroy the hero
Collection<Point> getRobbers();

// coordinates of all walls
Collection<Point> getWalls();

// etc
```

Good luck and may the best win!