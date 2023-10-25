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

## Cases

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