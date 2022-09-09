<meta charset="UTF-8">

## Symbol breakdown
| Sprite | Code | Description |
| -------- | -------- | -------- |
|<img src="/codenjoy-contest/resources/clifford/sprite/none.png" style="height:auto;" /> | `NONE(' ')` | Пустое место – по которому может двигаться детектив. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/brick.png" style="height:auto;" /> | `BRICK('#')` | Стена в которой можно прострелить дырочку. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pit_fill_1.png" style="height:auto;" /> | `PIT_FILL_1('1')` | Стена со временем зарастает. Когда процесс начинается - мы видим таймер. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pit_fill_2.png" style="height:auto;" /> | `PIT_FILL_2('2')` | Стена со временем зарастает. Когда процесс начинается - мы видим таймер. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pit_fill_3.png" style="height:auto;" /> | `PIT_FILL_3('3')` | Стена со временем зарастает. Когда процесс начинается - мы видим таймер. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pit_fill_4.png" style="height:auto;" /> | `PIT_FILL_4('4')` | Стена со временем зарастает. Когда процесс начинается - мы видим таймер. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/stone.png" style="height:auto;" /> | `STONE('☼')` | Неразрушаемая стена - в ней ничего прострелить не получится. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/crack_pit.png" style="height:auto;" /> | `CRACK_PIT('*')` | В момент выстрела мы видим стену именно так. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/clue_knife.png" style="height:auto;" /> | `CLUE_KNIFE('$')` | Улика нож. Собирай серии улик, чтобы получить максимум очков. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/clue_glove.png" style="height:auto;" /> | `CLUE_GLOVE('&')` | Улика перчатка. Собирай серии улик, чтобы получить максимум очков. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/clue_ring.png" style="height:auto;" /> | `CLUE_RING('@')` | Улика кольцо. Собирай серии улик, чтобы получить максимум очков. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_die.png" style="height:auto;" /> | `HERO_DIE('O')` | Твой детектив переживает процесс умирания. В следующем тике он пропадет и появится в новом месте на поле. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_ladder.png" style="height:auto;" /> | `HERO_LADDER('A')` | Твой детектив карабкается по лестнице. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_left.png" style="height:auto;" /> | `HERO_LEFT('◄')` | Твой детектив бежит влево. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_right.png" style="height:auto;" /> | `HERO_RIGHT('►')` | Твой детектив бежит вправо. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_fall.png" style="height:auto;" /> | `HERO_FALL('U')` | Твой детектив падает вниз. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_pipe.png" style="height:auto;" /> | `HERO_PIPE('I')` | Твой детектив ползёт по трубе. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_pit.png" style="height:auto;" /> | `HERO_PIT('E')` | Твой детектив находится в яме. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_die.png" style="height:auto;" /> | `HERO_MASK_DIE('o')` | Твой замаскированный детектив переживает процесс умирания. В следующем тике он пропадет и появится в новом месте на поле. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_ladder.png" style="height:auto;" /> | `HERO_MASK_LADDER('a')` | Твой замаскированный детектив карабкается по лестнице. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_left.png" style="height:auto;" /> | `HERO_MASK_LEFT('h')` | Твой замаскированный детектив бежит влево. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_right.png" style="height:auto;" /> | `HERO_MASK_RIGHT('w')` | Твой замаскированный детектив бежит вправо. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_fall.png" style="height:auto;" /> | `HERO_MASK_FALL('u')` | Твой замаскированный детектив падает вниз. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_pipe.png" style="height:auto;" /> | `HERO_MASK_PIPE('i')` | Твой замаскированный детектив ползёт по трубе. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/hero_mask_pit.png" style="height:auto;" /> | `HERO_MASK_PIT('e')` | Твой замаскированный детектив находится в яме. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_die.png" style="height:auto;" /> | `OTHER_HERO_DIE('C')` | Другой детектив переживает процесс умирания. В следующем тике он пропадет и появится в новом месте на поле. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_ladder.png" style="height:auto;" /> | `OTHER_HERO_LADDER('D')` | Другой детектив карабкается по лестнице. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_left.png" style="height:auto;" /> | `OTHER_HERO_LEFT('«')` | Другой детектив бежит влево. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_right.png" style="height:auto;" /> | `OTHER_HERO_RIGHT('»')` | Другой детектив бежит вправо. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_fall.png" style="height:auto;" /> | `OTHER_HERO_FALL('F')` | Другой детектив падает вниз. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_pipe.png" style="height:auto;" /> | `OTHER_HERO_PIPE('J')` | Другой детектив ползёт по трубе. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_pit.png" style="height:auto;" /> | `OTHER_HERO_PIT('K')` | Другой детектив находится в яме. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_die.png" style="height:auto;" /> | `OTHER_HERO_MASK_DIE('c')` | Другой замаскированный детектив переживает процесс умирания. В следующем тике он пропадет и появится в новом месте на поле. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_ladder.png" style="height:auto;" /> | `OTHER_HERO_MASK_LADDER('d')` | Другой замаскированный детектив карабкается по лестнице. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_left.png" style="height:auto;" /> | `OTHER_HERO_MASK_LEFT('Z')` | Другой замаскированный детектив бежит влево. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_right.png" style="height:auto;" /> | `OTHER_HERO_MASK_RIGHT('z')` | Другой замаскированный детектив бежит вправо. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_fall.png" style="height:auto;" /> | `OTHER_HERO_MASK_FALL('f')` | Другой замаскированный детектив падает вниз. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_pipe.png" style="height:auto;" /> | `OTHER_HERO_MASK_PIPE('j')` | Другой замаскированный детектив ползёт по трубе. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/other_hero_mask_pit.png" style="height:auto;" /> | `OTHER_HERO_MASK_PIT('k')` | Другой замаскированный детектив находится в яме. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_die.png" style="height:auto;" /> | `ENEMY_HERO_DIE('L')` | Вражеский детектив переживает процесс умирания. В следующем тике он пропадет и появится в новом месте на поле. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_ladder.png" style="height:auto;" /> | `ENEMY_HERO_LADDER('N')` | Вражеский детектив карабкается по лестнице. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_left.png" style="height:auto;" /> | `ENEMY_HERO_LEFT('P')` | Вражеский детектив бежит влево. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_right.png" style="height:auto;" /> | `ENEMY_HERO_RIGHT('Q')` | Вражеский детектив бежит вправо. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_fall.png" style="height:auto;" /> | `ENEMY_HERO_FALL('R')` | Вражеский детектив падает вниз. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_pipe.png" style="height:auto;" /> | `ENEMY_HERO_PIPE('T')` | Вражеский детектив ползёт по трубе. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_pit.png" style="height:auto;" /> | `ENEMY_HERO_PIT('V')` | Вражеский детектив находится в яме. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_die.png" style="height:auto;" /> | `ENEMY_HERO_MASK_DIE('l')` | Вражеский замаскированный детектив переживает процесс умирания. В следующем тике он пропадет и появится в новом месте на поле. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_ladder.png" style="height:auto;" /> | `ENEMY_HERO_MASK_LADDER('n')` | Вражеский замаскированный детектив карабкается по лестнице. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_left.png" style="height:auto;" /> | `ENEMY_HERO_MASK_LEFT('p')` | Вражеский замаскированный детектив бежит влево. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_right.png" style="height:auto;" /> | `ENEMY_HERO_MASK_RIGHT('q')` | Вражеский замаскированный детектив бежит вправо. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_fall.png" style="height:auto;" /> | `ENEMY_HERO_MASK_FALL('r')` | Вражеский замаскированный детектив падает вниз. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_pipe.png" style="height:auto;" /> | `ENEMY_HERO_MASK_PIPE('t')` | Вражеский замаскированный детектив ползёт по трубе. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/enemy_hero_mask_pit.png" style="height:auto;" /> | `ENEMY_HERO_MASK_PIT('v')` | Вражеский замаскированный детектив находится в яме. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_ladder.png" style="height:auto;" /> | `ROBBER_LADDER('X')` | Вор карабкается по лестнице. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_left.png" style="height:auto;" /> | `ROBBER_LEFT(')')` | Вор бежит влево. Вор выбирает ближайшую добычу и охотится за ней, пока не настигнет. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_right.png" style="height:auto;" /> | `ROBBER_RIGHT('(')` | Вор бежит вправо. Вор выбирает ближайшую добычу и охотится за ней, пока не настигнет. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_fall.png" style="height:auto;" /> | `ROBBER_FALL('x')` | Вор падает вниз. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_pipe.png" style="height:auto;" /> | `ROBBER_PIPE('Y')` | Вор ползёт по трубе. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/robber_pit.png" style="height:auto;" /> | `ROBBER_PIT('y')` | Вор находится в яме. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/opened_door_gold.png" style="height:auto;" /> | `OPENED_DOOR_GOLD('g')` | Открытые золотые ворота. Запирается только на золотой ключ. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/opened_door_silver.png" style="height:auto;" /> | `OPENED_DOOR_SILVER('s')` | Открытые серебряные ворота. Запирается только на серебряный ключ. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/opened_door_bronze.png" style="height:auto;" /> | `OPENED_DOOR_BRONZE('b')` | Открытые бронзовые ворота. Запирается только на бронзовый ключ. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/closed_door_gold.png" style="height:auto;" /> | `CLOSED_DOOR_GOLD('G')` | Закрытые золотые ворота. Отпираются только золотым ключом. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/closed_door_silver.png" style="height:auto;" /> | `CLOSED_DOOR_SILVER('S')` | Закрытые серебряные ворота. Отпираются только серебряным ключом. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/closed_door_bronze.png" style="height:auto;" /> | `CLOSED_DOOR_BRONZE('B')` | Закрытые бронзовые ворота. Отпираются только бронзовым ключом. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/key_gold.png" style="height:auto;" /> | `KEY_GOLD('+')` | Золотой ключ. Помогает отпереть/запереть золотые ворота. Ключ может быть использован только единожды. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/key_silver.png" style="height:auto;" /> | `KEY_SILVER('-')` | Серебряный ключ. Помогает отпереть/запереть серебряные ворота. Ключ может быть использован только единожды. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/key_bronze.png" style="height:auto;" /> | `KEY_BRONZE('!')` | Бронзовый ключ. Помогает отпереть/запереть бронзовые ворота. Ключ может быть использован только единожды. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/bullet.png" style="height:auto;" /> | `BULLET('•')` | Пуля. После выстрела героем пуля летит до тех пор, пока не встретит препятствие. Пуля убивает героя. Рикошетит от нерушимой стены (не более 1 раза). Пуля разрушает разрушаемую стену. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/ladder.png" style="height:auto;" /> | `LADDER('H')` | Лестница - по ней можно перемещаться по уровню вертикально. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/pipe.png" style="height:auto;" /> | `PIPE('~')` | Труба - по ней так же можно перемещаться по уровню, но только горизонтально. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/backway.png" style="height:auto;" /> | `BACKWAY('W')` | Черный ход - позволяет скрыто перемещаться в иное место на карте. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/mask_potion.png" style="height:auto;" /> | `MASK_POTION('m')` | Маскировочное зелье - наделяют детектива дополнительными способностями. Герой временно обретает маскировку. | 
|<img src="/codenjoy-contest/resources/clifford/sprite/ammo_clip.png" style="height:auto;" /> | `AMMO_CLIP('M')` | Патроны - дополнительные патроны для оружия героя. | 
