<!-- Code generated by ManualGeneratorRunner.java
  !!!DO NOT EDIT!!! -->
<meta charset="UTF-8">

## Intro

Игровой demo-сервер доступен так же в интернете 24/7 в целях ознакомления [Clifford Free-For-All server.](https://dojorena.io/games/5).

Это игра с открытым исходным кодом. Чтобы создать свою игру, исправить ошибки в текущей версии или внести другие исправления, вам следует [форкнуть проект](https://github.com/codenjoyme/codenjoy). Описание есть в файле Readme.md в корне репозитория. В описании указано, что делать дальше.

Проект игры (для написания своего бота) вы можете найти [тут](https://github.com/codenjoyme/codenjoy-clients.git)

## В чем суть игры?

Будь внимателен: во время игры тебе стоит сосредоточиться на реализации логики
передвижения и активности героя. Остальное - подключение по websocket, некоторое 
API для парсинга борды - уже реализовано для тебя.

Тебе необходимо написать своего бота для детектива, который 
обыграет детективов других игроков по очкам. Все играют на одном 
поле. Детектив может передвигаться по свободным ячейкам во все 
четыре стороны.

Детектив может вскарабкаться по лестнице (вверх/вниз), а 
так же передвигаться по трубе (влево/вправо). С трубы можно
спрыгнуть вниз. Детектив падает до тех пор, пока не приземлится. 
Падение с высоты безопасно для детектива.

На своем пути детектив может повстречать улики, врагов, 
других игроков, черные ходы и зелья.

Детектив так же должен быть внимателен, ведь на пути могут 
быть оставлены простреленные другими детективами ямы. Ямы со 
временем зарастают, но если в нее угодить и не успеть выбраться -
быть беде! Нет разницы в чью яму угодил детектив - попав в 
яму он побудет в ней некоторое время и замуруется, штрафные 
очки за смерть отнимутся и новый детектив появится в произвольном 
месте на поле. Если же под ямой есть пустота, лестница или 
труба - детектив проскочит сквозь яму не застряв.

Встав на клетку с уликой, игрок получает за неё очки, а улика, 
которую подобрал игрок, исчезает с карты. Каждый игровой тик 
на карте генерируется одинаковое количество улик разного типа - 
встречаются улики перчатка, нож и кольцо. Количество полученных 
за улики очков зависит от её типа.

По карте снуют враги - это воры, которые следят за жертвой,
и пытаются её догнать. Если злой бот настигает игрока, в этот 
момент его детектив отправляется в байтовый рай. Но не всё так 
страшно, уже в следующем игровом тике игрок получит респаун в 
рандомном месте на игровой карте.

Также, каждый игрок может прострелить ямку в разрушаемой стене 
(есть еще неразрушаемые). Ямка со временем зарастает. Так можно 
спастись от вора.

Штрафные очки так же предусмотрены за падение самого героя в 
свою или чужую ямку из которой он не сможет выбраться. Если ямка 
чужая - конкурент-участник получит очки.

Если простреленная ямка создает дыру в полу, то детектив может 
пролететь сквозь нее не застревая. Таким образом можно собирать 
улики "висящее" в воздухе.

Очки[(?)](#ask) зарабатываются во время собирания улик и устранение 
конкурентов в ямках.

За каждую новую улику детектив получает немного больше[(?)](#ask) очков, 
чем за предыдущую. Счетчик сбрасывается при потере героя - 
выгодно собирать улики и не терять героя. 

Однако, это ещё не всё, и ближе к финалу в игре припасено[(?)](#ask) ещё 
несколько механик.

В частности, на карте могут[(?)](#ask) появляться черные ходы. 
Запрыгивая в черный ход, в следующем тике игрок перемещается на место 
другого, рандомно выбранного черного хода (узнать заранее, в 
каком месте карты вы окажетесь нельзя). Черные ходы находятся 
на своих исходных местах в течение нескольких игровых тиков, 
затем меняют своё расположение случайным образом.

Также, ближе к финалу[(?)](#ask), на карте начнут появляться 
специальные зелья... Игрок, подобрав такое зелье (встав на одну 
клетку с ним), на несколько игровых тиков становится под маскировкой. 
С этого момента ему становятся не страшны боты-воры. Оказавшись 
с ними на одной клетке, игрок просто пройдёт сквозь них. Встреча 
же с другим игроком может быть более коварной. Если игрок-маскировка 
встаёт на одну клетку с обычным игроком, маскировка убивает 
бедолагу. При этом, игрок-маскировка получает дополнительные 
очки, а ушедший в мир иной игрок может, напротив, потерять несколько 
очков (это мы решим ближе к финалу). В случае же, если на одной 
клетке встречаются две маскировки, обе остаются живы, при этом 
не теряют, но и не зарабатывают очков.

Очки суммируются. Побеждает игрок с большим числом очков (до условленного
времени).

[(?)](#ask)Точное количество очков за любое действие, а так же другие
настройки на данный момент игры уточни у Сенсея.

## Подключение к серверу

Игроку необходимо зарегистрироваться(или залогиниться на сервере) и войти в игру.

Затем вам необходимо подключиться к серверу со своего клиента используя вебсокет. Тут [коллекция клиентов](https://github.com/codenjoyme/codenjoy-clients.git) для различных языков программирования, где уже реализована базовая логика и подключение к серверу. Для старта ознакомьтесь с файлом README.md в корневой директории проекта. 

Если вы не можете найти ваш язык программирования, вы можете написать свой клиент и отправить его нам по почте: 
[oleksandr_baglai@epam.com](mailto:oleksandr_baglai@epam.com)

Адрес для подключения к серверу должен выглядеть так:
`https://[server]/codenjoy-contest/board/player/[user]?code=[code]`
Вы можете найти его в игровой комнате. 

Здесь `[server]` - домен/ip сервера, `[user]` id игрока `[code]` ваш секретный токен. Храните его в секрете от других игроков. Любой, кто знает ваш код, сможет управлять вашим ботом. 

## Формат сообщений

После подключения клиент будет регулярно (каждую секунду) получать строку
символов с закодированным состоянием поля. Формат таков:

`^board=(.*)$`

C помощью этого regexp можно выкусить строку доски.

## Пример поля

Вот пример строки от сервера:

<pre>board=☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼          $                 ☼☼###########################H☼☼   $              $        H☼☼H☼☼#☼☼H    H#########H $   H☼☼H $   H    H         H#####H☼☼H#☼#☼#H    H         H      ☼☼H     H~~~~H~~~~~~   H      ☼☼H     H    H  $  H###☼☼☼☼☼☼H☼☼H     H $  H#####H         H☼☼☼###☼##☼##☼H         H###H##☼☼☼###☼      H         H   H  ☼☼☼$  ☼      H   ~~~}~~H   H $☼☼########H###☼☼☼☼     H  ####☼☼        H     $      H      ☼☼###########################H☼☼    $                      H☼☼#######H#######            H☼☼       H~~~~~~~~~~     $   H☼☼       H    ##H   #######H##☼☼       H    ##H          H  ☼☼##H#####    ########H#######☼☼  H     ►   $       H       ☼☼#########H##########H       ☼☼         H          H       ☼☼       $ H~~~~~~~~~~H   $   ☼☼    H######         #######H☼☼    H           $          H☼☼###########################H☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼</pre>

Длинна строки равна площади поля `N*N`. Если вставить символ 
переноса строки каждые `N=sqrt(length(string))` символов, то 
получится читабельное изображение поля:

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

Первый символ строки соответствует ячейке расположенной в 
левом верхнем углу и имеет координату `[0, 29]`.
Координата `[0, 0]` соответствует левому нижнему углу.
В этом примере — позиция героя (символ `►`) — `[9, 7]`.

Как это поле выглядит в реале:

![](https://dojorena.io/codenjoy-contest/resources/clifford/help/board.png)

<meta charset="UTF-8">

## Symbol breakdown
| Sprite | Code | Description |
| -------- | -------- | -------- |
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/none.png" style="width:40px;" /> | `NONE(' ')` | Empty space - where the hero can move. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/brick.png" style="width:40px;" /> | `BRICK('#')` | A wall where you can shoot a hole. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/pit_fill_1.png" style="width:40px;" /> | `PIT_FILL_1('1')` | The wall is restored over time. When the process begins, we see a timer. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/pit_fill_2.png" style="width:40px;" /> | `PIT_FILL_2('2')` | The wall is restored over time. When the process begins, we see a timer. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/pit_fill_3.png" style="width:40px;" /> | `PIT_FILL_3('3')` | The wall is restored over time. When the process begins, we see a timer. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/pit_fill_4.png" style="width:40px;" /> | `PIT_FILL_4('4')` | The wall is restored over time. When the process begins, we see a timer. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/stone.png" style="width:40px;" /> | `STONE('☼')` | Indestructible wall - It cannot be destroyed with a shot. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/crack_pit.png" style="width:40px;" /> | `CRACK_PIT('*')` | At the moment of the shot, we see the wall like this. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/clue_knife.png" style="width:40px;" /> | `CLUE_KNIFE('$')` | Clue knife. Collect a series of clues to get the maximum points. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/clue_glove.png" style="width:40px;" /> | `CLUE_GLOVE('&')` | Clue glove. Collect a series of clues to get the maximum points. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/clue_ring.png" style="width:40px;" /> | `CLUE_RING('@')` | Clue ring. Collect a series of clues to get the maximum points. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_die.png" style="width:40px;" /> | `HERO_DIE('O')` | Your hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_ladder.png" style="width:40px;" /> | `HERO_LADDER('A')` | Your hero is climbing the ladder. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_left.png" style="width:40px;" /> | `HERO_LEFT('◄')` | Your hero runs to the left. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_right.png" style="width:40px;" /> | `HERO_RIGHT('►')` | Your hero runs to the right. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_fall.png" style="width:40px;" /> | `HERO_FALL('U')` | Your hero is falling. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_pipe.png" style="width:40px;" /> | `HERO_PIPE('I')` | Your hero is crawling along the pipe. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_pit.png" style="width:40px;" /> | `HERO_PIT('E')` | Your hero in the pit. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_mask_die.png" style="width:40px;" /> | `HERO_MASK_DIE('o')` | Your shadow-hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_mask_ladder.png" style="width:40px;" /> | `HERO_MASK_LADDER('a')` | Your shadow-hero is climbing the ladder. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_mask_left.png" style="width:40px;" /> | `HERO_MASK_LEFT('h')` | Your shadow-hero runs to the left. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_mask_right.png" style="width:40px;" /> | `HERO_MASK_RIGHT('w')` | Your shadow-hero runs to the right. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_mask_fall.png" style="width:40px;" /> | `HERO_MASK_FALL('u')` | Your shadow-hero is falling. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_mask_pipe.png" style="width:40px;" /> | `HERO_MASK_PIPE('i')` | Your shadow-hero is crawling along the pipe. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/hero_mask_pit.png" style="width:40px;" /> | `HERO_MASK_PIT('e')` | Your shadow-hero in the pit. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_die.png" style="width:40px;" /> | `OTHER_HERO_DIE('C')` | Other hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_ladder.png" style="width:40px;" /> | `OTHER_HERO_LADDER('D')` | Other hero is climbing the ladder. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_left.png" style="width:40px;" /> | `OTHER_HERO_LEFT('«')` | Other hero runs to the left. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_right.png" style="width:40px;" /> | `OTHER_HERO_RIGHT('»')` | Other hero runs to the right. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_fall.png" style="width:40px;" /> | `OTHER_HERO_FALL('F')` | Other hero is falling. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_pipe.png" style="width:40px;" /> | `OTHER_HERO_PIPE('J')` | Other hero is crawling along the pipe. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_pit.png" style="width:40px;" /> | `OTHER_HERO_PIT('K')` | Other hero in the pit. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_mask_die.png" style="width:40px;" /> | `OTHER_HERO_MASK_DIE('c')` | Other shadow-hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_mask_ladder.png" style="width:40px;" /> | `OTHER_HERO_MASK_LADDER('d')` | Other shadow-hero is climbing the ladder. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_mask_left.png" style="width:40px;" /> | `OTHER_HERO_MASK_LEFT('Z')` | Other shadow-hero runs to the left. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_mask_right.png" style="width:40px;" /> | `OTHER_HERO_MASK_RIGHT('z')` | Other shadow-hero runs to the right. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_mask_fall.png" style="width:40px;" /> | `OTHER_HERO_MASK_FALL('f')` | Other shadow-hero is falling. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_mask_pipe.png" style="width:40px;" /> | `OTHER_HERO_MASK_PIPE('j')` | Other shadow-hero is crawling along the pipe. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/other_hero_mask_pit.png" style="width:40px;" /> | `OTHER_HERO_MASK_PIT('k')` | Other shadow-hero in the pit. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_die.png" style="width:40px;" /> | `ENEMY_HERO_DIE('L')` | Enemy hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_ladder.png" style="width:40px;" /> | `ENEMY_HERO_LADDER('N')` | Enemy hero is climbing the ladder. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_left.png" style="width:40px;" /> | `ENEMY_HERO_LEFT('P')` | Enemy hero runs to the left. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_right.png" style="width:40px;" /> | `ENEMY_HERO_RIGHT('Q')` | Enemy hero runs to the right. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_fall.png" style="width:40px;" /> | `ENEMY_HERO_FALL('R')` | Enemy hero is falling. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_pipe.png" style="width:40px;" /> | `ENEMY_HERO_PIPE('T')` | Enemy hero is crawling along the pipe. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_pit.png" style="width:40px;" /> | `ENEMY_HERO_PIT('V')` | Enemy hero in the pit. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_mask_die.png" style="width:40px;" /> | `ENEMY_HERO_MASK_DIE('l')` | Enemy shadow-hero is dead. In the next tick, it will disappear and appear in a new location. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_mask_ladder.png" style="width:40px;" /> | `ENEMY_HERO_MASK_LADDER('n')` | Enemy shadow-hero is climbing the ladder. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_mask_left.png" style="width:40px;" /> | `ENEMY_HERO_MASK_LEFT('p')` | Enemy shadow-hero runs to the left. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_mask_right.png" style="width:40px;" /> | `ENEMY_HERO_MASK_RIGHT('q')` | Enemy shadow-hero runs to the right. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_mask_fall.png" style="width:40px;" /> | `ENEMY_HERO_MASK_FALL('r')` | Enemy shadow-hero is falling. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_mask_pipe.png" style="width:40px;" /> | `ENEMY_HERO_MASK_PIPE('t')` | Enemy shadow-hero is crawling along the pipe. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/enemy_hero_mask_pit.png" style="width:40px;" /> | `ENEMY_HERO_MASK_PIT('v')` | Enemy shadow-hero in the pit. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/robber_ladder.png" style="width:40px;" /> | `ROBBER_LADDER('X')` | Robber is climbing the ladder. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/robber_left.png" style="width:40px;" /> | `ROBBER_LEFT(')')` | Robber runs to the left. Robber picks up the nearest prey and hunts for it until it overtakes it. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/robber_right.png" style="width:40px;" /> | `ROBBER_RIGHT('(')` | Robber runs to the right. Robber picks up the nearest prey and hunts for it until it overtakes it. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/robber_fall.png" style="width:40px;" /> | `ROBBER_FALL('x')` | Robber is falling. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/robber_pipe.png" style="width:40px;" /> | `ROBBER_PIPE('Y')` | Robber is crawling along the pipe. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/robber_pit.png" style="width:40px;" /> | `ROBBER_PIT('y')` | Robber in the pit. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/opened_door_gold.png" style="width:40px;" /> | `OPENED_DOOR_GOLD('g')` | Opened golden gates. Can only be locked with a golden key. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/opened_door_silver.png" style="width:40px;" /> | `OPENED_DOOR_SILVER('s')` | Opened silver gates. Can only be locked with a silver key. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/opened_door_bronze.png" style="width:40px;" /> | `OPENED_DOOR_BRONZE('b')` | Opened bronze gates. Can only be locked with a bronze key. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/closed_door_gold.png" style="width:40px;" /> | `CLOSED_DOOR_GOLD('G')` | Closed golden gates. Can only be opened with a golden key. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/closed_door_silver.png" style="width:40px;" /> | `CLOSED_DOOR_SILVER('S')` | Closed silver gates. Can only be opened with a silver key. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/closed_door_bronze.png" style="width:40px;" /> | `CLOSED_DOOR_BRONZE('B')` | Closed bronze gates. Can only be opened with a bronze key. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/key_gold.png" style="width:40px;" /> | `KEY_GOLD('+')` | Golden key. Helps open/close golden gates. The key can only be used once. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/key_silver.png" style="width:40px;" /> | `KEY_SILVER('-')` | Silver key. Helps open/close silver gates. The key can only be used once. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/key_bronze.png" style="width:40px;" /> | `KEY_BRONZE('!')` | Bronze key. Helps open/close bronze gates. The key can only be used once. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/bullet.png" style="width:40px;" /> | `BULLET('•')` | Bullet. After the shot by the hero, the bullet flies until it meets an obstacle. The bullet kills the hero. It ricochets from the indestructible wall (no more than 1 time). The bullet destroys the destructible wall. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/ladder.png" style="width:40px;" /> | `LADDER('H')` | Ladder - the hero can move along the level along it. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/pipe.png" style="width:40px;" /> | `PIPE('~')` | Pipe - the hero can also move along the level along it, but only horizontally. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/backway.png" style="width:40px;" /> | `BACKWAY('W')` | Back door - allows the hero to secretly move to another random place on the map. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/mask_potion.png" style="width:40px;" /> | `MASK_POTION('m')` | Disguise potion - endow the hero with additional abilities. The hero goes into shadow mode. | 
|<img src="https://github.com/codenjoyme/codenjoy-clifford/raw/master/src/main/webapp/resources/clifford/sprite/ammo_clip.png" style="width:40px;" /> | `AMMO_CLIP('M')` | Ammo clip - additional ammo for hero's gun. | 


## Как играть?

Игра пошаговая, каждую секунду сервер посылает твоему клиенту 
состояние обновленного поля на текущий момент и ожидает ответа 
команды герою. За следующую секунду игрок должен успеть дать 
команду герою. Если не успел — герой стоит на месте.

Твоя цель заставить героя двигаться в соответствии с задуманным тобой алгоритмом. 
Герой на поле должен уметь зарабатывать так много очков, как только сможет.
Основная цель игры - обыграть по очкам всех соперников.

## Команды управления

Команд несколько: 

* `UP`, `DOWN`, `LEFT`, `RIGHT` – приводят к движению героя 
  в заданном направлении на 1 клетку; 
* `ACT` - прострелить дырку в направлении, куда смотрит детектив. 
* Команды движения можно комбинировать с командой `ACT`, разделяя 
  их через запятую – это укажет в какую сторону надо прострелить 
  дырку (без передвижения детектива). Порядок `LEFT,ACT` или 
  `ACT,LEFT` - не имеет значения - будет прострелена дырка 
  слева. Если игрок будет использовать только одну команду `ACT` 
  то дырка появится в направлении, куда смотрит детектив.
* Если вдруг детектив попал в ловушку и выбраться оттуда он не 
  может - вызови команду `ACT(0)` и детектив совершит акт суицида, 
  после чего он появится в новом месте.
* `ACT(1),LEFT`, `ACT(1),RIGHT` - выстрелить влево или вправо
  соответственно. Пуля будет лететь вперед, пока не встретит препятствие: 
  - если это другой игрок, он погибнет, а ты получишь очки;
  - если это разрушаемая стена - появится дырка;
  - если это неразрушаемая стена - пуля отрикошетит и полетит назад. 
    Пуля рикошетит только 1 раз.
* `ACT(2),LEFT`, `ACT(2),RIGHT` - открыть дверь слева или справа 
  от детектива. Двери есть трех цветов и открываются/закрываются 
  соответствующими по цвету ключами. При открытии двери ключ из 
  инвентаря детектива пропадает.
* `ACT(3),LEFT`, `ACT(3),RIGHT` - закрыть дверь слева или справа 
  от детектива. Двери есть трех цветов и открываются/закрываются 
  соответствующими по цвету ключами. При открытии двери ключ из 
  инвентаря детектива пропадает. 

## Настройки

Параметры будут меняться[(?)](#ask) по ходу игры. Значения по-умолчанию
представлены в таблице ниже:

| Событие | Название | Очки |
|--------|--------|--------|
| Количество маскировочный зелий на карте | MASK_POTIONS_COUNT | 0[(?)](#ask) |
| Время действия маскировочного зелья (тиков) | MASK_TICKS | 15[(?)](#ask) |
| Количество черных ходов на карте | BACKWAYS_COUNT | 5[(?)](#ask) |
| Количество тиков до изменения положения черных ходов | BACKWAY_TICKS | 50[(?)](#ask) |
| Количество воров-преследователей | ROBBERS_COUNT | 3[(?)](#ask) |
| Генерировать ли новые ключи на карте | GENERATE_KEYS | false[(?)](#ask) |
| Количество улик-перчатка на карте | CLUE_COUNT_GLOVE | 20[(?)](#ask) |
| Очки за улику-перчатка | CLUE_SCORE_GLOVE | 1[(?)](#ask) |
| Инкремент очков за последующие собранные улики перчатки в серии без смертей | CLUE_SCORE_GLOVE_INCREMENT | 1[(?)](#ask) |
| Количество улик-нож на карте | CLUE_COUNT_KNIFE | 10[(?)](#ask) |
| Очки за улику-нож | CLUE_SCORE_KNIFE | 2[(?)](#ask) |
| Инкремент очков за последующие собранные улики-ножи в серии без смертей | CLUE_SCORE_KNIFE_INCREMENT | 1[(?)](#ask) |
| Количество улик-кольцо на карте | CLUE_COUNT_RING | 5[(?)](#ask) |
| Очки за улику-кольцо | CLUE_SCORE_RING | 5[(?)](#ask) |
| Инкремент очков за последующие собранные улики-кольцо в серии без смертей | CLUE_SCORE_RING_INCREMENT | 1[(?)](#ask) |
| Количество патронов | HANDGUN_CLIP_SIZE | 12[(?)](#ask) |
| Количество тиков на перезарядку | HANDGUN_TICKS_PER_SHOOT | 0[(?)](#ask) |
| Режим неограниченного заряда патронов | HANDGUN_UNLIMITED_AMMO | true[(?)](#ask) |
| Очки за победу в раунде | ROUND_WIN_SCORE | 20[(?)](#ask) |
| Очки за убийство другого игрока (под маскировкой или при закапывании) | KILL_OTHER_HERO_SCORE | 20[(?)](#ask) |
| Очки за убийство вражеского игрока (под маскировкой или при закапывании) | KILL_ENEMY_HERO_SCORE | 50[(?)](#ask) |
| Пенальти за смерть | HERO_DIED_PENALTY | -1[(?)](#ask) |
| Пенальти за суицид | SUICIDE_PENALTY | -10[(?)](#ask) |

## Кейзы

## Подсказки

Первостепенная задача – написать websocket клиента, который подключится
к серверу. Затем заставить героя на поле слушаться команд.
Таким образом, игрок подготовится к основной игре.
Основная цель – вести осмысленную игру и победить.

Если ты не знаешь с чего начать, попробуй реализовать следующие алгоритмы:

* Перейти в случайную пустую соседнюю ячейку.
* Продвинуться вперед в свободную клетку в направлении ближайшей улики.
* Попробуй спрятаться от пуль.
* Попробуй избежать грабителей и других героев.
* Попробуй стрелять в других героев.

## <a id="ask"></a> Спроси Сенсея

Параметры могут изменяться по ходу игры. Настройки текущей игры
ты сможешь всегда [подглядеть тут](/codenjoy-contest/rest/settings/player).
Пожалуйста, спроси у Сенсея как интерпретировать эти данные. Ты можешь найти Сенсея
в чате, который подготовили организаторы для обсуждения вопросов.

## Клиент и API

Организаторы предоставляют игрокам подготовленные клиенты в исходном 
коде на нескольких языках. Каждый из этих клиентов уже умеет связываться 
с сервером, принимать и разбирать сообщение от сервера (обычно это называется board)
и отправлять серверу команды.

Слишком много форы клиентский код не дает играющим, поскольку в этом коде
еще надо разобраться, но там реализована логика общения с сервером +
некоторое высокоуровневое API для работы с доской (что уже приятно).

Все языки так или иначе имеют похожий набор методов:

* `Solver`
  Пустой класс с одним методом — ты должен(должна) наполнить его умной логикой.
* `Direcion`
  Возможные направления движения для этой игры.
* `Point`
  `x`, `y` координаты.
* `Element`
  Тип элемента на доске.
* `Board`
  Содержит логику для удобного поиска и манипуляции элементами на поле.
  Ты можешь найти следующие методы в Board классе:
* `int boardSize();` 
  Размер доски.
* `boolean isAt(Point point, Element element);` 
  Находится ли в позиции point заданный элемент? 
* `boolean isAt(Point point, Collection<Element>elements);` 
  Находится ли в позиции point что-нибудь из заданного набора?
* `boolean isNear(Point point, Element element);` 
  Есть ли вокруг клеточки с координатой point заданный элемент?
* `int countNear(Point point, Element element);` 
  Сколько элементов заданного типа есть вокруг клетки с point?
* `Element getAt(Point point);` 
  Элемент в текущей клетке.
* `Point getHero();` 
  Позиция моего героя на доске.
* `boolean isGameOver();` 
  Жив ли мой герой?
* `Collection<Point> getOtherHeroes();` 
  Позиции всех остальных героев на доске.
* `Collection<Point> getEnemyHeroes();` 
  Позиции всех вражеских героев на доске (в случае командной игры).
* `Collection<Point> getBarriers();`
  Позиции всех объектов препятствующих движению.
* `boolean isBarrierAt(Point point);`
  Есть ли препятствие в клеточке point? 
* `Collection<Point> getRobbers();`
  Позиции всех воров.
* `Collection<Point> getWalls();`
  Позиции всех стен.
* и так далее...

## FAQ

## Как провести такой же ивент самостоятельно?

Перед тобой opensource проект. Для реализации своей новой игры, модификации этой игры,
любой другой модификации сервера или исправления найденной ошибки
[форкни проект](https://github.com/codenjoyme/codenjoy.git).
Все инструкции ты найдешь в Readme.md файлах - они подскажут, что делать дальше.

Если у тебя есть вопросы - прошу, задавай их мне 
в [скайпе alexander.baglay](skype:alexander.baglay)
или по почте [apofig@gmail.com](mailto:apofig@gmail.com).

Удачной игры и пусть победит сильнейший! 
