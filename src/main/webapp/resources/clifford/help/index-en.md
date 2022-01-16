<meta charset="UTF-8">

## Intro

The game server is available for familiarization reasons
[http://codenjoy.com/codenjoy-contest](http://codenjoy.com/codenjoy-contest).

This is the open source game. To realize your game, correct errors in the current
version and make the other corrections, you should
[fork the project](https://github.com/codenjoyme/codenjoy) at first.
There is the description in the Readme.md file in the repository root.
It is specified in the description what to do next.

If any questions, please write in [skype:alexander.baglay](skype:alexander.baglay)
or Email [apofig@gmail.com](mailto:apofig@gmail.com).

Game project (for writing your bot) can be
found [here](https://github.com/codenjoyme/codenjoy-clients.git)

## What is the game about

Keep in mind: when writing your bot you should stick to its movement logic.
The rest of the game is ready for you.

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

However, this is not all, and closer to the final[*](index-en.md#ask) 
in the game there are a few more mechanics in store.

In particular, back doors may appear on the map. Jumping into 
the back door, in the next tick, the player moves to the place 
of another, randomly chosen back door (you cannot find out 
in advance where you will be on the map). The back doors stay 
in their original places for several game ticks, then they 
change their position at random.

Also, closer to the end[*](index-en.md#ask), special potions will 
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

## Connect to the server

So, the player [registers on the server](../../../register?gameName=clifford)
and joining the game.

Then you should connect from client code to the server via websockets.
This [collection of clients](https://github.com/codenjoyme/codenjoy-clients.git)
for different programming languages will help you. How to start a
client please check at the root of the project in the README.md file.

If you can't find your programming language, you're gonna
have to write your client (and then send us to the mail:
[apofig@gmail.com](mailto:apofig@gmail.com))

Address to connect the game on the server looks like this (you can
copy it from your game room):

`https://[server]/codenjoy-contest/board/player/[user]?code=[code]`

Here `[server]` - domain/id of server, `[user]` is your player id 
and `[code]` is your security token. Make sure you keep the code 
safe from prying eyes. Any participant, knowing your code, can 
play on your behalf.

In the client code, you need to find a similar line and replace it 
with your URL - thereby, you set the login / password to access the 
server. Then start your client and make sure the server receives 
your client's commands. After that, you can start working on the 
logic of the bot.

## Message format

After connection, the client will regularly (every second) receive
a line of characters with the encoded state of the field. The format:

`^board=(.*)$`

You can use this regular expression to extract a board from
the resulting string.

## Field example

Here is an example of a string from the server.

<pre>board=☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼          $                 ☼☼###########################H☼☼   $              $        H☼☼H☼☼#☼☼H    H#########H $   H☼☼H $   H    H         H#####H☼☼H#☼#☼#H    H         H      ☼☼H     H~~~~H~~~~~~   H      ☼☼H     H    H  $  H###☼☼☼☼☼☼H☼☼H     H $  H#####H         H☼☼☼###☼##☼##☼H         H###H##☼☼☼###☼      H         H   H  ☼☼☼$  ☼      H   ~~~}~~H   H $☼☼########H###☼☼☼☼     H  ####☼☼        H     $      H      ☼☼###########################H☼☼    $                      H☼☼#######H#######            H☼☼       H~~~~~~~~~~     $   H☼☼       H    ##H   #######H##☼☼       H    ##H          H  ☼☼##H#####    ########H#######☼☼  H     ►   $       H       ☼☼#########H##########H       ☼☼         H          H       ☼☼       $ H~~~~~~~~~~H   $   ☼☼    H######         #######H☼☼    H           $          H☼☼###########################H☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

The line length is equal to the field square. If to insert a wrapping
character (carriage return) every `sqrt(length(string))` characters,
you obtain the readable image of the field.

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

The first character of the line corresponds to a cell located on the
left-top corner and has the `[0, 29]` coordinate. The following example
shows the position of the hero (the `►` character) – `[9,7]`. left-bottom
corner has the `[0, 0]` coordinate.

This is what you see on UI:

![](board.png)

## Symbol breakdown

Please [check it here](elements.md).

## What to do

The game is turn-based: Each second, the server sends the updated state
of the field to the client and waits for response. Within the next
second the player must give the hero a command. If no command is
given, the hero will stand still.

Your goal is to make the hero move according to your algorithm. The
algorithm must earn points as much as possible. The ultimate goal is
winning the game.

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
* `ACT(1),LEFT`, `ACT(1),RIGHT` - shoot left or right.
  The bullet will fly forward until there is an obstacle:
  - if it's another player, he'll die, and you'll get bonus points;
  - If it's a broken wall, there's a hole;
  - if it's an undestructed wall, the bullet will fly back. 
  The bullet only ricochets once.
* `ACT(2),LEFT`, `ACT(2),RIGHT` - open the door to the left or right 
  of the detective. The doors are available in three colors and can 
  be opened / closed with matching keys. When you open the door, 
  the key disappears from the detective's inventory.
* `ACT(3),LEFT`, `ACT(3),RIGHT` - close the door to the left or 
  right of the detective. The doors are available in three colors and can
  be opened / closed with matching keys. When you open the door,
  the key disappears from the detective's inventory.

## Points

The parameters will change[*](index-md.md#ask) as the game progresses. 
The default values are shown in the table below:

| Action | Settings name | Points |
|--------|--------|--------|
| Number of potions on the map | MASK_POTIONS_COUNT | 0[*](index-en.md#ask) |
| Potion duration (ticks) | MASK_TICKS | 15[*](index-en.md#ask) |
| Number of back doors on the map | BACKWAYS_COUNT | 5[*](index-en.md#ask) |
| Number of ticks before changing the position of the back doors | BACKWAY_TICKS | 50[*](index-en.md#ask) |
| Number of robbers | ROBBERS_COUNT | 3[*](index-en.md#ask) |
| Whether to generate new keys on the map | GENERATE_KEYS | false[*](index-en.md#ask) |
| Number of clues-glove on the map | CLUE_COUNT_GLOVE | 20[*](index-en.md#ask) |
| Points for clue-glove | CLUE_SCORE_GLOVE | 1[*](index-en.md#ask) |
| Increment points for the next collected clues-gloves in a series without deaths | CLUE_SCORE_GLOVE_INCREMENT | 1[*](index-en.md#ask) |
| Number of clues-knife on the map | CLUE_COUNT_KNIFE | 10[*](index-en.md#ask) |
| Points for clue-knife | CLUE_SCORE_KNIFE | 2[*](index-en.md#ask) |
| Increment points for the next collected clues-knife in a series without deaths | CLUE_SCORE_KNIFE_INCREMENT | 1[*](index-en.md#ask) |
| Number of clues-ring on the map | CLUE_COUNT_RING | 5[*](index-en.md#ask) |
| Points for clue-ring | CLUE_SCORE_RING | 5[*](index-en.md#ask) |
| Increment points for the next collected clues-ring in a series without deaths | CLUE_SCORE_RING_INCREMENT | 1[*](index-en.md#ask) |
| The number of cartridges in the clip | HANDGUN_CLIP_SIZE | 12[*](index-en.md#ask) |
| Number of ticks per cooldown | HANDGUN_TICKS_PER_SHOOT | 0[*](index-en.md#ask) |
| Unlimited ammo mode  | HANDGUN_UNLIMITED_AMMO | true[*](index-en.md#ask) |
| Points for winning a round | ROUND_WIN_SCORE | 20[*](index-en.md#ask) |
| Points for killing another player (as shadow or buried) | KILL_OTHER_HERO_SCORE | 20[*](index-en.md#ask) |
| Points for killing enemy player (as shadow or buried) | KILL_ENEMY_HERO_SCORE 50[*](index-en.md#ask) |
| Death penalty | HERO_DIED_PENALTY | -1[*](index-en.md#ask) |
| Penalty for suicide | SUICIDE_PENALTY | -10[*](index-en.md#ask) |

## Cases

## <a id="ask"></a> Ask Sensei

You can always see the settings of the current game 
[here](/codenjoy-contest/rest/settings/player).
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
level API for working with the board are implemented already.

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

## Want to host an event?

It's an open source game. To implement your version of it,
to fix bugs and to add any other logic simply
[fork it](https://github.com/codenjoyme/codenjoy.git).
All instructions are in Readme.md file, you'll know what to do next once you read it.

If you have any questions reach me in [skype alexander.baglay](skype:alexander.baglay)
or email [apofig@gmail.com](mailto:apofig@gmail.com).

Good luck and may the best win!