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

## Settings

The parameters will change[(?)](#ask) as the game progresses. 
The default values are shown in the table below:

| Action | Settings name | Points |
|--------|--------|--------|
| Number of potions on the map | MASK_POTIONS_COUNT | 0[(?)](#ask) |
| Potion duration (ticks) | MASK_TICKS | 15[(?)](#ask) |
| Number of back doors on the map | BACKWAYS_COUNT | 5[(?)](#ask) |
| Number of ticks before changing the position of the back doors | BACKWAY_TICKS | 50[(?)](#ask) |
| Number of robbers | ROBBERS_COUNT | 3[(?)](#ask) |
| Whether to generate new keys on the map | GENERATE_KEYS | false[(?)](#ask) |
| Number of clues-glove on the map | CLUE_COUNT_GLOVE | 20[(?)](#ask) |
| Points for clue-glove | CLUE_SCORE_GLOVE | 1[(?)](#ask) |
| Increment points for the next collected clues-gloves in a series without deaths | CLUE_SCORE_GLOVE_INCREMENT | 1[(?)](#ask) |
| Number of clues-knife on the map | CLUE_COUNT_KNIFE | 10[(?)](#ask) |
| Points for clue-knife | CLUE_SCORE_KNIFE | 2[(?)](#ask) |
| Increment points for the next collected clues-knife in a series without deaths | CLUE_SCORE_KNIFE_INCREMENT | 1[(?)](#ask) |
| Number of clues-ring on the map | CLUE_COUNT_RING | 5[(?)](#ask) |
| Points for clue-ring | CLUE_SCORE_RING | 5[(?)](#ask) |
| Increment points for the next collected clues-ring in a series without deaths | CLUE_SCORE_RING_INCREMENT | 1[(?)](#ask) |
| The number of cartridges in the clip | HANDGUN_CLIP_SIZE | 12[(?)](#ask) |
| Number of ticks per cooldown | HANDGUN_TICKS_PER_SHOOT | 0[(?)](#ask) |
| Unlimited ammo mode  | HANDGUN_UNLIMITED_AMMO | true[(?)](#ask) |
| Ammo clip count  | AMMO_CLIP_COUNT | 0[(?)](#ask) |
| Points for winning a round | ROUND_WIN_SCORE | 20[(?)](#ask) |
| Points for killing another player (as shadow or buried) | KILL_OTHER_HERO_SCORE | 20[(?)](#ask) |
| Points for killing enemy player (as shadow or buried) | KILL_ENEMY_HERO_SCORE 50[(?)](#ask) |
| Death penalty | HERO_DIED_PENALTY | -1[(?)](#ask) |
| Penalty for suicide | SUICIDE_PENALTY | -10[(?)](#ask) |
| Counting score mode | SCORE_COUNTING_TYPE | 0 (0 - Accumulate points consistently, 1 - Maximum points from the event, 2 - Maximum points from the series)[(?)](#ask) |

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