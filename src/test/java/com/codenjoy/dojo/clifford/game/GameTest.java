package com.codenjoy.dojo.clifford.game;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.clifford.game.check.AbstractGameCheckTest;
import com.codenjoy.dojo.clifford.model.items.Brick;
import com.codenjoy.dojo.clifford.model.items.Potion;
import com.codenjoy.dojo.services.Point;
import org.junit.Ignore;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.PointImpl.pt;

public class GameTest extends AbstractGameCheckTest {

    // есть карта со мной
    @Test
    public void shouldFieldAtStart() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу простреливать дырки
    @Test
    public void shouldCrackLeft() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCrackCounter() {
        shouldCrackLeft();

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼4##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼3##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼2##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼1##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCrackRight() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ R ☼\n" +
                "☼##*☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ R ☼\n" +
                "☼## ☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу ходить влево и вправо
    @Test
    public void shouldMoveLeft() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldMoveRight() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // если небыло команды я никуда не иду
    @Test
    public void shouldStopWhenNoMoreRightCommand() {
        shouldMoveRight();

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // я останавливаюсь возле границы
    @Test
    public void shouldStopWhenWallRight() {
        shouldMoveRight();

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldStopWhenWallLeft() {
        shouldMoveLeft();

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // яма заростает со временем
    @Test
    public void shouldCrackFilled() {
        shouldCrackLeft();

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼4##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼3##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼2##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼1##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // В простреливаю яму я легко могу упасть
    @Test
    public void shouldFallInPitLeft() {
        shouldCrackLeft();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼]  ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼⍃##☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldFallInPitRight() {
        shouldCrackRight();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ R ☼\n" +
                "☼## ☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ]☼\n" +
                "☼## ☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼##⍃☼\n" +
                "☼☼☼☼☼\n");
    }

    // я если упал то не могу передвигаться влево и вправо поскольку мне мешают стены
    @Test
    public void shouldCantGoLeftIfWall() {
        shouldFallInPitRight();

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼##⍃☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantGoRightIfWall() {
        shouldFallInPitLeft();

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼⍃##☼\n" +
                "☼☼☼☼☼\n");
    }

    // я если упал, то могу перемещаться влево и вправо, если мне не мешают стены
    @Test
    public void shouldСanGoInPitIfNoWall() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ►☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  Я☼\n" +
                "☼ *#☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ] ☼\n" +
                "☼  #☼\n" +
                "☼☼☼☼☼\n");

        hero().left();  // при падении я не могу передвигаться
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ ⍃#☼\n" +
                "☼☼☼☼☼\n");

        hero().left(); // а вот в яме куда угодно, пока есть место
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼⍃ #☼\n" +
                "☼☼☼☼☼\n");

        hero().right(); // пока стенки не заростут
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ ⍃#☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼4⍃#☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼⍃ #☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼2⍃#☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼⍃3#☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼#⍃#☼\n" +
                "☼☼☼☼☼\n");

    }

    // если стенка замуровывается вместе со мной, то я умираю и появляюсь в рендомном месте
    @Test
    public void shouldIDieIPitFillWithMe() {
        shouldCrackLeft();

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼2##☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼]  ☼\n" +
                "☼1##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼Ѡ##☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE, SUICIDE]");

        dice(2, 3);
        tick();         // ну а после смерти он появляется в рендомном месте
        field().newGame(player());

        assertF("☼☼☼☼☼\n" +
                "☼ ] ☼\n" +
                "☼   ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldIDieIPitFillWithMe2() {
        shouldCrackLeft();

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼3##☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼]  ☼\n" +
                "☼2##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼⍃##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼Ѡ##☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE, SUICIDE]");

        dice(2, 3);
        tick();         // ну а после смерти он появляется в рендомном месте
        field().newGame(player());

        assertF("☼☼☼☼☼\n" +
                "☼ ] ☼\n" +
                "☼   ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

    }

    // я не могу простреливать уже прострелиную дырку, я даже не делаю вид что пытался
    @Test
    public void shouldCantCrackPit() {
        shouldCrackLeft();

        hero().right();
        tick();
        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");
    }

    // выполнения команд left + act не зависят от порядка - если они сделаны в одном тике, то будет дырка слева без перемещения
    @Test
    public void shouldCrackLeft_otherCommandSequence() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

//        hero().act();
        hero().left();
        hero().act();

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");
    }

    // если я повернут в какую-то сторону и просто нажимаю прострелить то будет с той стороны дырка
    @Test
    public void shouldCrackLeft_onyActCommand() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Я ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCrackRight_onyActCommand() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ R ☼\n" +
                "☼##*☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ R ☼\n" +
                "☼## ☼\n" +
                "☼☼☼☼☼\n");
    }

    // на карте появляется улика, если я его беру то получаю +
    @Test
    public void shouldClueOnMap_iCanGetIt() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►$☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        dice(2, 3);
        hero().right();
        tick();
        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        assertF("☼☼☼☼☼\n" +
                "☼ $ ☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼ $ ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // проверить, что если новому обекту не где появится то программа не зависает - там бесконечный цикл потенциальный есть
    @Test(timeout = 1000)
    public void shouldNoDeadLoopWhenNewObjectCreation() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►$☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        dice(3, 3);
        hero().right();
        tick();
        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        assertF("☼☼☼☼☼\n" +
                "☼  $☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // на карте появляются лестницы, я могу зайти на нее и выйти обратно
    @Test
    public void shouldICanGoOnLadder() {
        givenFl("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ ►H☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  Y☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ ◄H☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу карабкаться по лестнице вверх
    @Test
    public void shouldICanGoOnLadderUp() {
        givenFl("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ ►H☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  Y☼\n" +
                "☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  Y☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  Y☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
    }

    // я не могу вылезти с лестницей за границы
    @Test
    public void shouldICantGoOnBarrierFromLadder() {
        shouldICanGoOnLadderUp();

        assertF("☼☼☼☼☼\n" +
                "☼  Y☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  Y☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  Y☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу спустится вниз, но не дальше границы экрана
    @Test
    public void shouldICanGoOnLadderDown() {
        shouldICanGoOnLadderUp();

        assertF("☼☼☼☼☼\n" +
                "☼  Y☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  Y☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  Y☼\n" +
                "☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  Y☼\n" +
                "☼☼☼☼☼\n");
    }

    // я мошгу в любой момент спрыгнуть с лестницы и я буду падать до тех пор пока не наткнусь на препятствие
    @Test
    public void shouldICanFly() {
        shouldICanGoOnLadderUp();

        assertF("☼☼☼☼☼\n" +
                "☼  Y☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼ ]H☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼ ]H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ ◄H☼\n" +
                "☼☼☼☼☼\n");
    }

    // под стеной я не могу прострелить
    @Test
    public void shouldICantCrackUnderLadder() {
        givenFl("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼ ►H☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼ ►H☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // под уликой я не могу прострелить
    @Test
    public void shouldICantCrackUnderClue() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►$☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►$☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу поднятся по лестнице и зайти на площадку
    @Test
    public void shouldICanGoFromLadderToArea() {
        givenFl("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼H◄ ☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼Y  ☼\n" +
                "☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼Y# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼Y  ☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼H► ☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // и упасть
        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼H ]☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼H#]☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼H ►☼\n" +
                "☼☼☼☼☼\n");

    }

    // я не могу прострелить бетон
    @Test
    public void shouldICantCrackWall() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");
    }

    // пока я падаю я не могу двигаться влево и справо, даже если там есть площадки
    @Test
    public void shouldICantMoveWhenFall() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ]  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ]  ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ]  ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼##◄##☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // появляются на карте трубы, если я с площадки захожу на трубу то я ползу по ней
    @Test
    public void shouldIPipe() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼►~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ }~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~}~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~} ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // с трубы я могу спрыгунть и тогда я буду падать до препятствия
    @Test
    public void shouldIFallFromPipe() {
        shouldIPipe();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~} ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~~]☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#   ]☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼    ]☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼    ►☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // если по дороге я встречаюсь с уликой, то я его захвачу
    @Test
    public void shouldIGetClueWhenFallenFromPipe() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ $~~◄☼\n" +
                "☼ $  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ $~{ ☼\n" +
                "☼ $  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ${~ ☼\n" +
                "☼ $  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        dice(1, 5);
        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼$    ☼\n" +
                "☼ ]~~ ☼\n" +
                "☼ $  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        dice(2, 5);
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼$$   ☼\n" +
                "☼  ~~ ☼\n" +
                "☼ ]  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(2)]");

        dice(3, 5);
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼$$$  ☼\n" +
                "☼  ~~ ☼\n" +
                "☼    #☼\n" +
                "☼ ]   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(3)]");

        dice(4, 5);
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼$$$$ ☼\n" +
                "☼  ~~ ☼\n" +
                "☼    #☼\n" +
                "☼     ☼\n" +
                "☼ ◄   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(4)]");
    }

    // если я прострелил дырку и падаю в нее, а под ней ничего нет - то я падаю пока не найду препятствие
    @Test
    public void shouldIFallWhenUnderPitIsFree() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼#####☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼  Я  ☼\n" +
                "☼#*###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼ ]   ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼#]###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼ ]   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ]   ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ◄   ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // если в процессе падения я вдург наткнулся на трубу то я повисаю на ней
    @Test
    public void shouldIPipeWhenFall() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼ ]   ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼#]###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼ ]   ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ {~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ {~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // TODO я могу прострелить дырку под лестницей, а потом спуститься туда

    // я не могу прострелить дырку под другим камнем
    @Test
    public void shouldICantCrackUnderBrick() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►#☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►#☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу спрыгнуть с трубы
    @Test
    public void shouldCanJumpFromPipe() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  ]  ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~{~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼  ]  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼  ◄  ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // бага: мне нельзя спускаться с лестницы в бетон, так же как и подниматься
    // плюс я должен иметь возможность спустится по лестнице
    @Test
    public void shouldCantWalkThroughWallDown() {
        givenFl("☼☼☼☼☼\n" +
                "☼ ◄ ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Y ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Y ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantWalkThroughWallUp() {
        givenFl("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼ H ☼\n" +
                "☼ H◄☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼ H ☼\n" +
                "☼ Y ☼\n" +
                "☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼ Y ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼ Y ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // бага: мне нельзя проходить с лестницы через бетон направо или налево
    @Test
    public void shouldCantWalkThroughWallLeftRight() {
        givenFl("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼H☼☼\n" +
                "☼ H◄☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼H☼☼\n" +
                "☼ Y ☼\n" +
                "☼☼☼☼☼\n");

        hero().up();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼Y☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼Y☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼Y☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // бага: мне нельзя проходить через бетон
    @Test
    public void shouldCantWalkThroughWallLeftRight2() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼◄☼☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼◄☼☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼►☼☼\n" +
                "☼☼☼☼☼\n");
    }

    // бага: мне нельзя спрыгивать с трубы что сразу над бетоном, протелая сквозь него
    @Test
    public void shouldCantJumpThroughWall() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼  ◄ ☼\n" +
                "☼  ~ ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  { ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  { ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    @Test // TODO fix me in *.data
    public void shouldBoardIsFree() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼~◄$H☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼☼☼☼☼☼\n");

        for (int x = 0; x < field().size(); x++) {
            for (int y = 0; y < field().size(); y++) {
                Point pt = pt(x, y);
                assertEquals("At:" + pt, false, field().isFree(pt));
            }

        }
    }

    // Есть хитрый хак, когда спрыгиваешь с трубы, то можно при падении простреливать под собой
    // но она многоптточная
    @Test
    public void shouldCrackDown() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼  ◄ ☼\n" +
                "☼  ~ ☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  { ☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        hero().down();
        hero().act();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  { ☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  ~ ☼\n" +
                "☼  ◄ ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  ~ ☼\n" +
                "☼  Я ☼\n" +
                "☼#*##☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // если я сам себя закопаю, то получу ли я за это очки? не должен!
    @Test
    public void shouldNoScoreWhenKamikadze() {
        givenFl("☼☼☼☼\n" +
                "☼ ◄☼\n" +
                "☼##☼\n" +
                "☼☼☼☼\n");

        hero().act();
        tick();
        hero().left();
        tick();
        tick();

        assertF("☼☼☼☼\n" +
                "☼  ☼\n" +
                "☼⍃#☼\n" +
                "☼☼☼☼\n");

        for (int c = 3; c < Brick.CRACK_TIMER; c++) {
            tick();
        }

        assertF("☼☼☼☼\n" +
                "☼  ☼\n" +
                "☼Ѡ#☼\n" +
                "☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼\n" +
                "☼  ☼\n" +
                "☼Ѡ#☼\n" +
                "☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE, SUICIDE]");
    }

    // я могу прострелить стенки под стенками, если те разрушены
    @Test
    public void shouldCrackUnderCrackedBrick() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ◄  ☼\n" +
                "☼####☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        hero().act();
        tick();

        hero().right();
        tick();

        hero().left();
        hero().act();
        tick();

        hero().right();
        tick();

        hero().left();
        hero().act();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼   Я☼\n" +
                "☼  *#☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        hero().left();
        tick();
        tick();

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ ⍃ #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        hero().right();
        hero().act();
        tick();

        hero().left();
        hero().act();
        tick();

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼2Я #☼\n" +
                "☼ # #☼\n" +
                "☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼]3 #☼\n" +
                "☼ # #☼\n" +
                "☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼#24#☼\n" +
                "☼⍃# #☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // если я спрыгива с последней секции лестницы, то как-то неудачно этто делаю. Бага!
    @Test
    public void shouldJumpFromLadderDown() {
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ◄  ☼\n" +
                "☼ H##☼\n" +
                "☼#H  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        hero().down();
        tick();

        hero().down();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#Y  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#Y  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#H] ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#H  ☼\n" +
                "☼  ► ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // чертик двигается так же как и обычный игрок - мжет ходить влево и вправо
    @Test
    public void shouldRobberMoveLeft() {
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ « ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼«  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberMoveRight() {
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ « ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼  »☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // если небыло команды чертик никуда не идет
    @Test
    public void shouldRobberStopWhenNoMoreRightCommand() {
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼  «☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ « ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ « ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертик останавливается возле границы
    @Test
    public void shouldRobberStopWhenWallRight() {
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼  »☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼  »☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberStopWhenWallLeft() {
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼«  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼«  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // В простреленную яму чертик легко может упасть
    @Test
    public void shouldRobberFallInPitLeft() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼« ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        robber().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ‹Я☼\n" +
                "☼# #☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  Я☼\n" +
                "☼#⍇#☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberFallInPitRight() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄ «☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼R «☼\n" +
                "☼#*#☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼R‹ ☼\n" +
                "☼# #☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼R  ☼\n" +
                "☼#⍇#☼\n" +
                "☼☼☼☼☼\n");
    }

    // при падении чертик не может передвигаться влево и вправо - ему мешают стены
    @Test
    public void shouldRobberCantGoLeftIfWall() {
        shouldRobberFallInPitRight();

        robber().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼R  ☼\n" +
                "☼#⍇#☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberCantGoRightIfWall() {
        shouldRobberFallInPitLeft();

        robber().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  Я☼\n" +
                "☼#⍇#☼\n" +
                "☼☼☼☼☼\n");
    }

    // монстр сидит в ямке некоторое количество тиков, потом он вылазит
    @Test
    public void shouldMonsterGetUpFromPit() {
        shouldRobberFallInPitLeft();

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  Я☼\n" +
                "☼#⍇#☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ »Я☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        robber().left(); // после этого он может двигаться
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼« Я☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // если чертик попадает на героя - тот погибает
    @Test
    public void shouldHeroDieWhenMeetWithRobber() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄ «☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        robber().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Ѡ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE]");

        dice(1, 3);
        tick();         // ну а после смерти он появляется в рендомном месте причем чертик остается на своем месте
        field().newGame(player());

        assertF("☼☼☼☼☼\n" +
                "☼]  ☼\n" +
                "☼ « ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // другой кейс, когда оба двигаются на встречу друг к другу
    @Test
    public void shouldHeroDieWhenMeetWithRobber2() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄« ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        robber().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼«Ѡ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE]");

        dice(0,  // охотимся за первым игроком
            3, 3);
        tick();         // ну а после смерти он появляется в рендомном месте причем чертик остается на своем месте
        field().newGame(player());

        assertF("☼☼☼☼☼\n" +
                "☼  ]☼\n" +
                "☼«  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // другой кейс, когда игрок идет на чертика
    @Test
    public void shouldHeroDieWhenMeetWithRobber_whenHeroWalk() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄« ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Ѡ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE]");

        dice(3, 3);
        tick();         // ну а после смерти он появляется в рендомном месте причем чертик остается на своем месте
        field().newGame(player());

        assertF("☼☼☼☼☼\n" +
                "☼  ]☼\n" +
                "☼ « ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // другой кейс, когда чертик идет на игрока
    @Test
    public void shouldHeroDieWhenMeetWithRobber_whenRobberWalk() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄« ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼Ѡ  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE]");

        dice(0,  // охотимся за первым игроком
            3, 3);
        tick();         // ну а после смерти он появляется в рендомном месте причем чертик остается на своем месте
        field().newGame(player());

        assertF("☼☼☼☼☼\n" +
                "☼  ]☼\n" +
                "☼«  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертик может зайти на лестницу и выйти обратно
    @Test
    public void shouldRobberCanGoOnLadder() {
        givenFl("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ «H☼\n" +
                "☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  Q☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ «H☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертик может карабкаться по лестнице вверх
    @Test
    public void shouldRobberCanGoOnLadderUp() {
        givenFl("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ «H☼\n" +
                "☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  Q☼\n" +
                "☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  Q☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  Q☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертик не может вылезти с лестницей за границы
    @Test
    public void shouldRobberCantGoOnBarrierFromLadder() {
        shouldRobberCanGoOnLadderUp();

        assertF("☼☼☼☼►\n" +
                "☼  Q☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  Q☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  Q☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертик может спустится вниз, но не дальше границы экрана
    @Test
    public void shouldRobberCanGoOnLadderDown() {
        shouldRobberCanGoOnLadderUp();

        assertF("☼☼☼☼►\n" +
                "☼  Q☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        robber().down();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  Q☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        robber().down();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  Q☼\n" +
                "☼☼☼☼☼\n");

        robber().down();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  Q☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертик может в любой момент спрыгнуть с лестницы и будет падать до тех пор пока не наткнется на препятствие
    @Test
    public void shouldRobberCanFly() {
        shouldRobberCanGoOnLadderUp();

        assertF("☼☼☼☼►\n" +
                "☼  Q☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼ ‹H☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼ ‹H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ «H☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертик может поднятся по лестнице и зайти на площадку
    @Test
    public void shouldRobberCanGoFromLadderToArea() {
        givenFl("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼H« ☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼Q  ☼\n" +
                "☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼Q# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼Q  ☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼H» ☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // и упасть
        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼H ‹☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼H#‹☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼H »☼\n" +
                "☼☼☼☼☼\n");
    }

    // пока чертик падает он не может двигаться влево и справо, даже если там есть площадки
    @Test
    public void shouldRobberCantMoveWhenFall() {
        givenFl("☼☼☼☼☼☼►\n" +
                "☼  »  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼  ‹  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ‹  ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  ‹  ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼##»##☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // если чертик с площадки заходит на трубу то он ползет по ней
    @Test
    public void shouldRobberPipe() {
        givenFl("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼»~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ >~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~>~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~> ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // с трубы чертик может спрыгунть и тогда он будет падать до препятствия
    @Test
    public void shouldRobberFallFromPipe() {
        shouldRobberPipe();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~> ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~~‹☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#   ‹☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼    ‹☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼    »☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // чертик может похитить 1 улику в падении
    @Test
    public void shouldRobberGetClueWhenFallenFromPipe() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  $~~«☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  $~< ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  $<~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ‹~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();      // чертик не берет больше 1 улики

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  ‹  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  ‹   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  «  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberLeaveClueWhenFallInPit() {
        shouldRobberGetClueWhenFallenFromPipe();

        robber().right();
        hero().act();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $» Я☼\n" +
                "☼####*#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().right();     // если чертик с уликой падает в ямку - он оставляет улику на поверхности
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ ‹Я☼\n" +
                "☼#### #☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ $Я☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldIWalkOnRobberInPitAndGetClue() {
        shouldRobberLeaveClueWhenFallInPit();

        hero().left();     //я могу пройти по нему сверху и забрать улику
        dice(1, 6);
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$     ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ ◄ ☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$     ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $◄  ☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().left();
        dice(2, 6);
        tick();

        verifyAllEvents("[GET_CLUE_KNIFE(2)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ◄   ☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldNoMoreClueARobber() {
        shouldIWalkOnRobberInPitAndGetClue();

        for (int c = 4; c < Brick.CRACK_TIMER; c++) { // враг вылазит
            tick();
        }

        robber().left();
        hero().right();
        hero().act();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  R « ☼\n" +
                "☼###*##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  R‹  ☼\n" +
                "☼### ##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();  // уликы у него больше нет

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  R   ☼\n" +
                "☼###⍇##☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // я могу ходить по монстру, который в ямке
    @Test
    public void shouldIWalkOnRobber() {
        shouldRobberLeaveClueWhenFallInPit();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ $Я☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        dice(1, 6);
        hero().left();
        tick();

        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$     ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ ◄ ☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$     ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $◄  ☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        dice(2, 6);
        hero().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ◄   ☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents("[GET_CLUE_KNIFE(2)]");

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ◄ » ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // я не могу прострелить дырку непосредственно под монстром
    // TODO сделать так, чтобы мог
    @Test
    public void shouldICantCrackUnderRobber() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►»☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►»☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // если я прострелил дырку монстр падает в нее, а под ней ничего нет - монстр не проваливается сквозь
    @Test
    public void shouldRobberStayOnPitWhenUnderPitIsFree() {
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼» ◄  ☼\n" +
                "☼#####☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼» Я  ☼\n" +
                "☼#*###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼ ‹Я  ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼  Я  ☼\n" +
                "☼#⍇###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼  Я  ☼\n" +
                "☼#⍇###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼\n" +
                "☼ »Я  ☼\n" +
                "☼#####☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // если в процессе падения чертик вдург наткнулся на трубу то он повисаю на ней
    @Test
    public void shouldRobberPipeWhenFall() {
        givenFl("☼☼☼☼☼☼►\n" +
                "☼  »  ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼ ‹   ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼#‹###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼ ‹   ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ <~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ <~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // чертик может спрыгнуть с трубы
    @Test
    public void shouldRobberCanJumpFromPipe() {
        givenFl("☼☼☼☼☼☼►\n" +
                "☼  «  ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼  ‹  ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~<~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        robber().down();
        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼  ‹  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼  «  ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // чертику нельзя спускаться с лестницы в бетон, так же как и подниматься
    // плюс чертик должен иметь возможность спустится по лестнице
    @Test
    public void shouldRobberCantWalkThroughWallDown() {
        givenFl("☼☼☼☼►\n" +
                "☼ « ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");

        robber().down();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ Q ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");

        robber().down();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ Q ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberCantWalkThroughWallUp() {
        settings().integer(ROBBERS_COUNT, 1);
        givenFl("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼ H ☼\n" +
                "☼ H«☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼ H ☼\n" +
                "☼ Q ☼\n" +
                "☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼ Q ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼ Q ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертику нельзя проходить с лестницы через бетон направо или налево
    @Test
    public void shouldRobberCantWalkThroughWallLeftRight() {
        givenFl("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼H☼☼\n" +
                "☼ H«☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼H☼☼\n" +
                "☼ Q ☼\n" +
                "☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼Q☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼Q☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼Q☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // чертику нельзя проходить через бетон
    @Test
    public void shouldRobberCantWalkThroughWallLeftRight2() {
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼«☼☼\n" +
                "☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼«☼☼\n" +
                "☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼»☼☼\n" +
                "☼☼☼☼☼\n");
    }

    // Чертику нельзя спрыгивать с трубы что сразу над бетоном, протелая сквозь него
    @Test
    public void shouldRobberCantJumpThroughWall() {
        givenFl("☼☼☼☼☼►\n" +
                "☼  » ☼\n" +
                "☼  ~ ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼  > ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        robber().down();
        tick();

        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼  > ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // если чертик спрыгивает с последней секции лестницы
    @Test
    public void shouldRobberJumpFromLadderDown() {
        givenFl("☼☼☼☼☼►\n" +
                "☼ »  ☼\n" +
                "☼ H##☼\n" +
                "☼#H  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        robber().down();
        tick();

        robber().down();
        tick();

        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#Q  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#Q  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#H‹ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#H  ☼\n" +
                "☼  » ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // Чертик не может прыгять вверх :)
    @Test
    public void shouldRobberCantJump() {
        givenFl("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ »  ☼\n" +
                "☼☼☼☼☼☼\n");

        robber().up();
        tick();

        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ »  ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // я могу прыгнуть на голову монстру и мне ничего не будет
    @Test
    public void shouldICanJumpAtRobberHead() {
        givenFl("☼☼☼☼☼\n" +
                "☼ ◄ ☼\n" +
                "☼   ☼\n" +
                "☼ » ☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ » ☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ » ☼\n" +
                "☼☼☼☼☼\n");

        robber().right();     // если он отойдет - я упаду дальше
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ] ☼\n" +
                "☼  »☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ ◄»☼\n" +
                "☼☼☼☼☼\n");
    }

    // если чертик после того, как упал в ямку оставил улику, то когда он выберется - он свою улику заберет
    // А когда снова упадет, то оставит
    @Test
    public void shouldGetClueWheExitFromPit() {
        shouldRobberLeaveClueWhenFallInPit();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ $Я☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        for (int c = 3; c < Brick.CRACK_TIMER; c++) { // враг вылазит
            tick();
        }
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ »Я☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        hero().act();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $« Я☼\n" +
                "☼####*#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().right();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ $Я☼\n" +
                "☼####⍇#☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // Если чертик упал на другого чертика который был на трубе, то они складываются в один :)
    @Test
    public void shouldRobberStayOnOtherAtThePipe() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼  «   ☼\n" +
                "☼      ☼\n" +
                "☼  «   ☼\n" +
                "☼  ~   ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ‹   ☼\n" +
                "☼      ☼\n" +
                "☼  <   ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  «   ☼\n" +
                "☼  <   ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().down();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  <   ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().left();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  <   ☼\n" +
                "☼ «   ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberDontStopOnPipe() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ««  ☼\n" +
                "☼  ~~  ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  <<  ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ~<  ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().right();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ~<  ☼\n" +
                "☼    »◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // Чертик должен сам проваливаться на героя, а не впрыгивать в него
    @Test
    public void shouldRobberStayOnHeroAtThePipe() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼  «   ☼\n" +
                "☼      ☼\n" +
                "☼  ◄   ☼\n" +
                "☼  ~   ☼\n" +
                "☼      ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  ‹   ☼\n" +
                "☼      ☼\n" +
                "☼  {   ☼\n" +
                "☼      ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();
        tick();

        verifyAllEvents("[HERO_DIE]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  Ѡ   ☼\n" +
                "☼      ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().down();
        dice(2, 3);
        field().newGame(player());
        dice(0);  // охотимся за первым игроком
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ~   ☼\n" +
                "☼ ►«   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // Чертик проваливается в яму за героем и там его находит
    @Test
    public void shouldRobberFindHeroAtPit() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  « ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().act();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  « Я ☼\n" +
                "☼###*##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().left();
        tick();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  «   ☼\n" +
                "☼###⍃##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().right();
        tick();
        tick();

        verifyAllEvents("[HERO_DIE, SUICIDE]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼###Ѡ##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        dice(2, 3);
        field().newGame(player());
        dice(0);  // охотимся за первым игроком
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►    ☼\n" +
                "☼###⍇##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        for (int c = 5; c < Brick.CRACK_TIMER; c++) { // враг вылазит
            tick();
        }

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ► »  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    @Test
    public void iLooseScoresWhenDoHarakiri() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().act(0);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ Ѡ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIE, SUICIDE]");
    }

    @Test
    public void iCanJumpThroughBackways() {
        settings().integer(BACKWAYS_COUNT, 2);

        dice(1, 2,
            3, 3);
        givenFl("☼☼☼☼☼\n" +
                "☼  ⊛☼\n" +
                "☼⊛◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  ]☼\n" +
                "☼⊛  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  ⊛☼\n" +
                "☼⊛ ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼\n" +
                "☼  ⊛☼\n" +
                "☼⊛ ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void backwaysAreRecreatedEveryFewTicks() {
        settings().integer(BACKWAYS_COUNT, 2)
                .integer(BACKWAY_TICKS, 5);

        givenFl("☼☼☼☼☼\n" +
                "☼  ⊛☼\n" +
                "☼⊛◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertEquals(5, field().getBackwaysTimer());

        hero().left();
        tick();

        assertEquals(4, field().getBackwaysTimer());

        assertF("☼☼☼☼☼\n" +
                "☼  ]☼\n" +
                "☼⊛  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertEquals(3, field().getBackwaysTimer());

        assertF("☼☼☼☼☼\n" +
                "☼  ⊛☼\n" +
                "☼⊛ ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertEquals(2, field().getBackwaysTimer());

        tick();

        assertEquals(1, field().getBackwaysTimer());

        tick();

        assertEquals(0, field().getBackwaysTimer());

        assertF("☼☼☼☼☼\n" +
                "☼  ⊛☼\n" +
                "☼⊛ ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        dice(1, 3,  // new backways
            2, 3);
        tick();

        assertEquals(5, field().getBackwaysTimer());

        assertF("☼☼☼☼☼\n" +
                "☼⊛⊛ ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldResetHeroAndRobber_whenClearBoard() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  « ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  « ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        robber().left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ «  ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(1, 2); // new hero coordinates
        field().clearScore();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼► «   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldResetHeroScores_whenClearBoard() {
        // given
        Brick.CRACK_TIMER = 4;

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ◄ ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ◄ ) ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero(0).act();
        hero(0).right();
        hero(1).left();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  R⊐  ☼\n" +
                "☼###*##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  R   ☼\n" +
                "☼###ᗉ##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertEquals(0, hero(0).scores());
        assertEquals(0, hero(1).scores());
        assertEquals(true, hero(0).isAlive());
        assertEquals(true, hero(1).isAlive());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  R   ☼\n" +
                "☼###ᗉ##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");

        assertEquals(20, hero(0).scores());
        assertEquals(0, hero(1).scores());
        assertEquals(true, hero(0).isAlive());
        assertEquals(false, hero(1).isAlive());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  R   ☼\n" +
                "☼###Z##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(1, 2,
            6, 2);
        field().clearScore();

        // then
        assertEquals(0, hero(0).scores());
        assertEquals(0, hero(1).scores());
        assertEquals(true, hero(0).isAlive());
        assertEquals(true, hero(1).isAlive());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►    (☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldCollectAllClue() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►$$&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►$$&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►$&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        verifyAllEvents("[GET_CLUE_KNIFE(2)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        verifyAllEvents("[GET_CLUE_GLOVE(1)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ►@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        verifyAllEvents("[GET_CLUE_RING(1)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    ►@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        verifyAllEvents("[GET_CLUE_RING(2)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼     ►☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldResetClue_whenClearBoard() {
        // given
        shouldCollectAllClue();

        // when
        dice(1, 2);
        field().clearScore();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►$$&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // добавим еще улик
        settings().integer(CLUE_COUNT_KNIFE, settings().integer(CLUE_COUNT_KNIFE) + 2)
                .integer(CLUE_COUNT_RING,    settings().integer(CLUE_COUNT_RING) + 3)
                .integer(CLUE_COUNT_GLOVE,  settings().integer(CLUE_COUNT_GLOVE) + 1);
        dice(
            2, 3, // knife
            3, 3, // knife
            4, 3, // glove
            5, 3, // ring
            6, 3, // ring
            6, 4, // ring
            1, 6  // герой
        );
        field().clearScore();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼]     ☼\n" +
                "☼      ☼\n" +
                "☼     @☼\n" +
                "☼ $$&@@☼\n" +
                "☼ $$&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // удалим улики
        settings().integer(CLUE_COUNT_KNIFE, 1)
                .integer(CLUE_COUNT_RING, 1)
                .integer(CLUE_COUNT_GLOVE, 1);

        dice(2, 6);  // герой
        field().clearScore();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼ ]    ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ $ &@ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldEndlesslyWalkThroughTheBackways_untilExit() {
        // given
        settings().integer(BACKWAYS_COUNT, 3);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼►⊛    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼►⊛    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ]  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ⊛    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼   ]  ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ⊛    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // что интересно, если ты не выйдешь из черный хода то в следующий тик отправишься дальше
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ]  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ⊛    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼   ]  ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ⊛    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ⊛►   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldResetBackways_whenClearBoard() {
        shouldEndlesslyWalkThroughTheBackways_untilExit();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ⊛►   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // добавим еще один черный ход
        settings().integer(BACKWAYS_COUNT, settings().integer(BACKWAYS_COUNT) + 1);
        dice(2, 4, // new backway
            1, 2); // hero
        field().clearScore();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼ ⊛ ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼►⊛    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // оставим два черный хода
        settings().integer(BACKWAYS_COUNT, 2);
        dice(1, 2); // hero
        field().clearScore();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼   ⊛  ☼\n" +
                "☼      ☼\n" +
                "☼►     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldResetPotions_whenClearBoard() {
        // given
        settings().integer(MASK_POTIONS_COUNT, 3);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►S S S☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►S S S☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals(false, hero().under(Potion.PotionType.MASK_POTION));

        hero().right();
        tick();

        hero().right();
        tick();

        hero().right();
        tick();

        hero().right();
        tick();

        hero().right();
        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼     ⊳☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals(true, hero().under(Potion.PotionType.MASK_POTION));

        // when
        // почистим все
        dice(1, 2);  // hero
        field().clearScore();

        assertEquals(false, hero().under(Potion.PotionType.MASK_POTION));

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►S S S☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // добавим еще
        settings().integer(MASK_POTIONS_COUNT, settings().integer(MASK_POTIONS_COUNT) + 2);
        dice(
                3, 3, // new potion
                5, 3,
                1, 6  // hero
        );
        field().clearScore();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼]     ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  S S ☼\n" +
                "☼ S S S☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // оставим только 1
        settings().integer(MASK_POTIONS_COUNT, 1);
        dice(1, 2);  // hero
        field().clearScore();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►S    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldRemoveOldWalls_whenClearBoard() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼     ►☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        tick();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼     ►☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals(7 * 4, field().borders().size());

        // when
        dice(1, 2); // new hero
        field().clearScore();

        // then
        assertEquals(7 * 4, field().borders().size());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►     ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(2, 2); // new hero
        field().clearScore();

        // then
        assertEquals(7 * 4, field().borders().size());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►    ☼\n" +  // TODO героя приходится смещать, потому что при очистке его прошлое место занято им самим
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldResetBackwaysTimeout_whenClearBoard() {
        backwaysAreRecreatedEveryFewTicks();

        assertF("☼☼☼☼☼\n" +
                "☼⊛⊛ ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertEquals(5, field().getBackwaysTimer());

        tick();
        tick();
        tick();

        assertEquals(2, field().getBackwaysTimer());

        // when
        dice(3, 3, // new backways
            3, 2,
            1, 2); // new hero
        field().clearScore();

        // then
        assertEquals(5, field().getBackwaysTimer());
    }

    @Ignore // TODO please fix me
    @Test
    public void accessGivenBullets() {
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ► •••☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals(3, field().bullets().all().size());
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ► •••☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldClearField_whenClearScoresOnGame() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►$ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when then
        assertWalkThenClearScore();
    }

    @Test
    public void shouldHeroCanWalk_whenClearScoresOnGame() {
        // given
        shouldClearField_whenClearScoresOnGame();

        // when
        hero(0).right();
        dice(1, 3);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼$  ☼\n" +
                "☼ ► ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents(
                "[GET_CLUE_KNIFE(1)]");
    }

    @Test
    public void shouldSameLevel_whenClearScoresOnGame_andSeveralLevelsInTheSettings() {
        // given
        dice(1); // second level selected
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼☼☼☼☼\n",

                "☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►$ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n",

                "☼☼☼☼☼\n" +
                "☼ $ ☼\n" +
                "☼►  ☼\n" +
                "☼# #☼\n" +
                "☼☼☼☼☼\n");

        // when then
        assertWalkThenClearScore();
    }

    private void assertWalkThenClearScore() {
        hero().right();
        dice(1, 3);
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼$  ☼\n" +
                "☼ ► ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents(
                "[GET_CLUE_KNIFE(1)]");

        assertEquals(2, hero(0).scores());

        hero().act();
        hero().left();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼$  ☼\n" +
                "☼ Я ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        hero().right();
        tick();

        assertF("☼☼☼☼☼\n" +
                "☼$  ☼\n" +
                "☼  ►☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        // when
        dice(1, 2); // new hero position
        field().clearScore();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►$ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertEquals(0, hero(0).scores());
    }

    // прострелить находясь на трубе нельзя, в оригинале только находясь на краю трубы

    // карта намного больше, чем квардартик вьюшка, и я подходя к границе просто передвигаю вьюшку
    // повляется многопользовательский режим игры в формате "стенка на стенку"
}
