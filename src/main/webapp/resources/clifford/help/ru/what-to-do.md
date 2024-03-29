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