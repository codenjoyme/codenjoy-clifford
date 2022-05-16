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