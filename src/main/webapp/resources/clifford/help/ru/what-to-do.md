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

Параметры будут меняться[*](#ask) по ходу игры. Значения по-умолчанию
представлены в таблице ниже:

| Событие | Название | Очки |
|--------|--------|--------|
| Количество маскировочный зелий на карте | MASK_POTIONS_COUNT | 0[*](#ask) |
| Время действия маскировочного зелья (тиков) | MASK_TICKS | 15[*](#ask) |
| Количество черных ходов на карте | BACKWAYS_COUNT | 5[*](#ask) |
| Количество тиков до изменения положения черных ходов | BACKWAY_TICKS | 50[*](#ask) |
| Количество воров-преследователей | ROBBERS_COUNT | 3[*](#ask) |
| Генерировать ли новые ключи на карте | GENERATE_KEYS | false[*](#ask) |
| Количество улик-перчатка на карте | CLUE_COUNT_GLOVE | 20[*](#ask) |
| Очки за улику-перчатка | CLUE_SCORE_GLOVE | 1[*](#ask) |
| Инкремент очков за последующие собранные улики перчатки в серии без смертей | CLUE_SCORE_GLOVE_INCREMENT | 1[*](#ask) |
| Количество улик-нож на карте | CLUE_COUNT_KNIFE | 10[*](#ask) |
| Очки за улику-нож | CLUE_SCORE_KNIFE | 2[*](#ask) |
| Инкремент очков за последующие собранные улики-ножи в серии без смертей | CLUE_SCORE_KNIFE_INCREMENT | 1[*](#ask) |
| Количество улик-кольцо на карте | CLUE_COUNT_RING | 5[*](#ask) |
| Очки за улику-кольцо | CLUE_SCORE_RING | 5[*](#ask) |
| Инкремент очков за последующие собранные улики-кольцо в серии без смертей | CLUE_SCORE_RING_INCREMENT | 1[*](#ask) |
| Количество патронов | HANDGUN_CLIP_SIZE | 12[*](#ask) |
| Количество тиков на перезарядку | HANDGUN_TICKS_PER_SHOOT | 0[*](#ask) |
| Режим неограниченного заряда патронов | HANDGUN_UNLIMITED_AMMO | true[*](#ask) |
| Очки за победу в раунде | ROUND_WIN_SCORE | 20[*](#ask) |
| Очки за убийство другого игрока (под маскировкой или при закапывании) | KILL_OTHER_HERO_SCORE | 20[*](#ask) |
| Очки за убийство вражеского игрока (под маскировкой или при закапывании) | KILL_ENEMY_HERO_SCORE | 50[*](#ask) |
| Пенальти за смерть | HERO_DIED_PENALTY | -1[*](#ask) |
| Пенальти за суицид | SUICIDE_PENALTY | -10[*](#ask) |

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