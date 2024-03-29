<meta charset="UTF-8">

## Symbol breakdown
| Sprite | Code | Description |
| -------- | -------- | -------- |
|<img src="/codenjoy-contest/resources/clifford/sprite/none.png" style="height:auto;" /> | `NONE(' ')` | Empty space - where the hero can move. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/brick.png" style="height:auto;" /> | `BRICK('#')` | A wall where you can shoot a hole. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pit_fill_1.png" style="height:auto;" /> | `PIT_FILL_1('1')` | The wall is restored over time. When the process begins, we see a timer. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pit_fill_2.png" style="height:auto;" /> | `PIT_FILL_2('2')` | The wall is restored over time. When the process begins, we see a timer. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pit_fill_3.png" style="height:auto;" /> | `PIT_FILL_3('3')` | The wall is restored over time. When the process begins, we see a timer. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pit_fill_4.png" style="height:auto;" /> | `PIT_FILL_4('4')` | The wall is restored over time. When the process begins, we see a timer. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/stone.png" style="height:auto;" /> | `STONE('☼')` | Indestructible wall - It cannot be destroyed with a shot. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/crack_pit.png" style="height:auto;" /> | `CRACK_PIT('*')` | At the moment of the shot, we see the wall like this. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/clue_knife.png" style="height:auto;" /> | `CLUE_KNIFE('$')` | Clue knife. Collect a series of clues to get the maximum points. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/clue_glove.png" style="height:auto;" /> | `CLUE_GLOVE('&')` | Clue glove. Collect a series of clues to get the maximum points. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/clue_ring.png" style="height:auto;" /> | `CLUE_RING('@')` | Clue ring. Collect a series of clues to get the maximum points. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_die.png" style="height:auto;" /> | `HERO_DIE('O')` | Your hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_ladder.png" style="height:auto;" /> | `HERO_LADDER('A')` | Your hero is climbing the ladder. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_left.png" style="height:auto;" /> | `HERO_LEFT('◄')` | Your hero runs to the left. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_right.png" style="height:auto;" /> | `HERO_RIGHT('►')` | Your hero runs to the right. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_fall.png" style="height:auto;" /> | `HERO_FALL('U')` | Your hero is falling. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_pipe.png" style="height:auto;" /> | `HERO_PIPE('I')` | Your hero is crawling along the pipe. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_pit.png" style="height:auto;" /> | `HERO_PIT('E')` | Your hero in the pit. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_die.png" style="height:auto;" /> | `HERO_MASK_DIE('o')` | Your shadow-hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_ladder.png" style="height:auto;" /> | `HERO_MASK_LADDER('a')` | Your shadow-hero is climbing the ladder. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_left.png" style="height:auto;" /> | `HERO_MASK_LEFT('h')` | Your shadow-hero runs to the left. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_right.png" style="height:auto;" /> | `HERO_MASK_RIGHT('w')` | Your shadow-hero runs to the right. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_fall.png" style="height:auto;" /> | `HERO_MASK_FALL('u')` | Your shadow-hero is falling. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_pipe.png" style="height:auto;" /> | `HERO_MASK_PIPE('i')` | Your shadow-hero is crawling along the pipe. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_pit.png" style="height:auto;" /> | `HERO_MASK_PIT('e')` | Your shadow-hero in the pit. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_die.png" style="height:auto;" /> | `OTHER_HERO_DIE('C')` | Other hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_ladder.png" style="height:auto;" /> | `OTHER_HERO_LADDER('D')` | Other hero is climbing the ladder. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_left.png" style="height:auto;" /> | `OTHER_HERO_LEFT('«')` | Other hero runs to the left. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_right.png" style="height:auto;" /> | `OTHER_HERO_RIGHT('»')` | Other hero runs to the right. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_fall.png" style="height:auto;" /> | `OTHER_HERO_FALL('F')` | Other hero is falling. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_pipe.png" style="height:auto;" /> | `OTHER_HERO_PIPE('J')` | Other hero is crawling along the pipe. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_pit.png" style="height:auto;" /> | `OTHER_HERO_PIT('K')` | Other hero in the pit. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_die.png" style="height:auto;" /> | `OTHER_HERO_MASK_DIE('c')` | Other shadow-hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_ladder.png" style="height:auto;" /> | `OTHER_HERO_MASK_LADDER('d')` | Other shadow-hero is climbing the ladder. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_left.png" style="height:auto;" /> | `OTHER_HERO_MASK_LEFT('Z')` | Other shadow-hero runs to the left. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_right.png" style="height:auto;" /> | `OTHER_HERO_MASK_RIGHT('z')` | Other shadow-hero runs to the right. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_fall.png" style="height:auto;" /> | `OTHER_HERO_MASK_FALL('f')` | Other shadow-hero is falling. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_pipe.png" style="height:auto;" /> | `OTHER_HERO_MASK_PIPE('j')` | Other shadow-hero is crawling along the pipe. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_pit.png" style="height:auto;" /> | `OTHER_HERO_MASK_PIT('k')` | Other shadow-hero in the pit. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_die.png" style="height:auto;" /> | `ENEMY_HERO_DIE('L')` | Enemy hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_ladder.png" style="height:auto;" /> | `ENEMY_HERO_LADDER('N')` | Enemy hero is climbing the ladder. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_left.png" style="height:auto;" /> | `ENEMY_HERO_LEFT('P')` | Enemy hero runs to the left. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_right.png" style="height:auto;" /> | `ENEMY_HERO_RIGHT('Q')` | Enemy hero runs to the right. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_fall.png" style="height:auto;" /> | `ENEMY_HERO_FALL('R')` | Enemy hero is falling. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_pipe.png" style="height:auto;" /> | `ENEMY_HERO_PIPE('T')` | Enemy hero is crawling along the pipe. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_pit.png" style="height:auto;" /> | `ENEMY_HERO_PIT('V')` | Enemy hero in the pit. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_die.png" style="height:auto;" /> | `ENEMY_HERO_MASK_DIE('l')` | Enemy shadow-hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_ladder.png" style="height:auto;" /> | `ENEMY_HERO_MASK_LADDER('n')` | Enemy shadow-hero is climbing the ladder. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_left.png" style="height:auto;" /> | `ENEMY_HERO_MASK_LEFT('p')` | Enemy shadow-hero runs to the left. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_right.png" style="height:auto;" /> | `ENEMY_HERO_MASK_RIGHT('q')` | Enemy shadow-hero runs to the right. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_fall.png" style="height:auto;" /> | `ENEMY_HERO_MASK_FALL('r')` | Enemy shadow-hero is falling. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_pipe.png" style="height:auto;" /> | `ENEMY_HERO_MASK_PIPE('t')` | Enemy shadow-hero is crawling along the pipe. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_pit.png" style="height:auto;" /> | `ENEMY_HERO_MASK_PIT('v')` | Enemy shadow-hero in the pit. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_ladder.png" style="height:auto;" /> | `ROBBER_LADDER('X')` | Robber is climbing the ladder. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_left.png" style="height:auto;" /> | `ROBBER_LEFT(')')` | Robber runs to the left. Robber picks up the nearest prey and hunts for it until it overtakes it. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_right.png" style="height:auto;" /> | `ROBBER_RIGHT('(')` | Robber runs to the right. Robber picks up the nearest prey and hunts for it until it overtakes it. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_fall.png" style="height:auto;" /> | `ROBBER_FALL('x')` | Robber is falling. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_pipe.png" style="height:auto;" /> | `ROBBER_PIPE('Y')` | Robber is crawling along the pipe. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_pit.png" style="height:auto;" /> | `ROBBER_PIT('y')` | Robber in the pit. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/opened_door_gold.png" style="height:auto;" /> | `OPENED_DOOR_GOLD('g')` | Opened golden gates. Can only be locked with a golden key. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/opened_door_silver.png" style="height:auto;" /> | `OPENED_DOOR_SILVER('s')` | Opened silver gates. Can only be locked with a silver key. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/opened_door_bronze.png" style="height:auto;" /> | `OPENED_DOOR_BRONZE('b')` | Opened bronze gates. Can only be locked with a bronze key. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/closed_door_gold.png" style="height:auto;" /> | `CLOSED_DOOR_GOLD('G')` | Closed golden gates. Can only be opened with a golden key. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/closed_door_silver.png" style="height:auto;" /> | `CLOSED_DOOR_SILVER('S')` | Closed silver gates. Can only be opened with a silver key. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/closed_door_bronze.png" style="height:auto;" /> | `CLOSED_DOOR_BRONZE('B')` | Closed bronze gates. Can only be opened with a bronze key. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/key_gold.png" style="height:auto;" /> | `KEY_GOLD('+')` | Golden key. Helps open/close golden gates. The key can only be used once. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/key_silver.png" style="height:auto;" /> | `KEY_SILVER('-')` | Silver key. Helps open/close silver gates. The key can only be used once. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/key_bronze.png" style="height:auto;" /> | `KEY_BRONZE('!')` | Bronze key. Helps open/close bronze gates. The key can only be used once. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/bullet.png" style="height:auto;" /> | `BULLET('•')` | Bullet. After the shot by the hero, the bullet flies until it meets an obstacle. The bullet kills the hero. It ricochets from the indestructible wall (no more than 1 time). The bullet destroys the destructible wall. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/ladder.png" style="height:auto;" /> | `LADDER('H')` | Ladder - the hero can move along the level along it. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pipe.png" style="height:auto;" /> | `PIPE('~')` | Pipe - the hero can also move along the level along it, but only horizontally. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/backway.png" style="height:auto;" /> | `BACKWAY('W')` | Back door - allows the hero to secretly move to another random place on the map. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/mask_potion.png" style="height:auto;" /> | `MASK_POTION('m')` | Disguise potion - endow the hero with additional abilities. The hero goes into shadow mode. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/ammo_clip.png" style="height:auto;" /> | `AMMO_CLIP('M')` | Ammo clip - additional ammo for hero's gun. | 
