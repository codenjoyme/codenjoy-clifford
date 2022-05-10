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

However, this is not all, and closer to the final[*](#ask) 
in the game there are a few more mechanics in store.

In particular, back doors may appear on the map. Jumping into 
the back door, in the next tick, the player moves to the place 
of another, randomly chosen back door (you cannot find out 
in advance where you will be on the map). The back doors stay 
in their original places for several game ticks, then they 
change their position at random.

Also, closer to the end[*](#ask), special potions will 
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
