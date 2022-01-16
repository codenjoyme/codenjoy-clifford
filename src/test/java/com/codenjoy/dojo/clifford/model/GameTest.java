package com.codenjoy.dojo.clifford.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
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
 * Yhttp://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.clifford.model.items.Brick;
import com.codenjoy.dojo.services.Point;
import org.junit.Ignore;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.model.items.potion.PotionType.MASK_POTION;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.Direction.LEFT;
import static com.codenjoy.dojo.services.Direction.RIGHT;
import static com.codenjoy.dojo.services.PointImpl.pt;

public class GameTest extends AbstractGameTest {

    // есть карта со мной
    @Test
    public void shouldFieldAtStart() {
        // given when
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу простреливать дырки
    @Test
    public void shouldCrackLeft() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCrackCounter() {
        // given
        shouldCrackLeft();

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼4##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼3##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼2##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼1##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCrackRight() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack(RIGHT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼##*☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼## ☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу ходить влево и вправо
    @Test
    public void shouldMoveLeft() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldMoveRight() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // если не было команды я никуда не иду
    @Test
    public void shouldStopHero_whenNoMoreRightCommand() {
        // given
        shouldMoveRight();

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // я останавливаюсь возле границы
    @Test
    public void shouldStopHero_whenWallRight() {
        // given
        shouldMoveRight();

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldStopHero_whenWallLeft() {
        // given
        shouldMoveLeft();

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // яма заростает со временем
    @Test
    public void shouldCrackFilled() {
        // given
        shouldCrackLeft();

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼4##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼3##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼2##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼1##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // В простреленную яму я легко могу упасть
    @Test
    public void shouldFallInPitLeft() {
        // given
        shouldCrackLeft();
        
        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼U  ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼E##☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldFallInPitRight() {
        // given
        shouldCrackRight();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼## ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  U☼\n" +
                "☼## ☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼##E☼\n" +
                "☼☼☼☼☼\n");
    }

    // я если упал то не могу передвигаться влево и вправо поскольку мне мешают стены
    @Test
    public void shouldCantGoLeftIfWall() {
        // given
        shouldFallInPitRight();

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼##E☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantGoRightIfWall() {
        // given
        shouldFallInPitLeft();

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼E##☼\n" +
                "☼☼☼☼☼\n");
    }

    // я если упал, то могу перемещаться влево и вправо, если мне не мешают стены
    @Test
    public void shouldСanGoInPitIfNoWall() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ►☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼ *#☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ U ☼\n" +
                "☼  #☼\n" +
                "☼☼☼☼☼\n");

        // when
        // при падении я не могу передвигаться
        hero().left();  
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ E#☼\n" +
                "☼☼☼☼☼\n");

        // when
        // а вот в яме куда угодно, пока есть место
        hero().left(); 
        tick();
        
        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼E #☼\n" +
                "☼☼☼☼☼\n");

        // when
        // пока стенки не заростут
        hero().right(); 
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ E#☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼4E#☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼E #☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼2E#☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼E3#☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼#E#☼\n" +
                "☼☼☼☼☼\n");
    }

    // если стенка замуровывается вместе со мной, то я умираю и появляюсь в рендомном месте
    @Test
    public void shouldIDieIPitFillWithMe() {
        // given
        shouldCrackLeft();

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼2##☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼U  ☼\n" +
                "☼1##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼O##☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED, SUICIDE]");

        // when
        // ну а после смерти он появляется в рендомном месте
        dice(2, 3);
        tick();         
        field().newGame(player());

        // then
        assertF("☼☼☼☼☼\n" +
                "☼ U ☼\n" +
                "☼   ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldIDieIPitFillWithMe2() {
        // given
        shouldCrackLeft();

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼3##☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼U  ☼\n" +
                "☼2##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼E##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼O##☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED, SUICIDE]");

        // when
        // ну а после смерти он появляется в рендомном месте
        dice(2, 3);
        tick();         
        field().newGame(player());

        // then
        assertF("☼☼☼☼☼\n" +
                "☼ U ☼\n" +
                "☼   ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

    }

    // я не могу простреливать уже прострелиную дырку, я даже не делаю вид что пытался
    @Test
    public void shouldCantCrackPit() {
        // given
        shouldCrackLeft();

        // when
        hero().right();
        tick();

        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");
    }

    // выполнения команд left + act не зависят от порядка - если они сделаны в одном тике, то будет дырка слева без перемещения
    @Test
    public void shouldCrackLeft_otherCommandSequence() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        hero().crack();

        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        hero().right();

        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼ #*☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼ # ☼\n" +
                "☼☼☼☼☼\n");
    }

    // если я повернут в какую-то сторону и просто нажимаю прострелить то будет с той стороны дырка
    @Test
    public void shouldCrackLeft_onyActCommand() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCrackRight_onyActCommand() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼##*☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼## ☼\n" +
                "☼☼☼☼☼\n");
    }

    // на карте появляется улика, если я его беру то получаю +
    @Test
    public void shouldClueOnMap_iCanGetIt() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►$☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        dice(2, 3); // gold
        hero().right();
        
        tick();
        
        // then
        verifyAllEvents("[GET_CLUE_KNIFE(1)]");
        
        assertF("☼☼☼☼☼\n" +
                "☼ $ ☼\n" +
                "☼  ►☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼ $ ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // проверить, что если новому объекту негде появится то программа не зависает - там бесконечный цикл потенциальный есть
    @Test
    public void shouldNoDeadLoopWhenNewObjectCreation() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►$☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        // координата пустой ячейки, в которой будет генериться новый объект
        // после того как его подберут
        dice(3, 3);
        hero().right();
        tick();
        
        // then
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
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ ►H☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  A☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ ◄H☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу карабкаться по лестнице вверх
    @Test
    public void shouldICanGoOnLadderUp() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ ►H☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  A☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  A☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  A☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
    }

    // я не могу вылезти с лестницей за границы
    @Test
    public void shouldICantGoOnBarrierFromLadder() {
        // given
        shouldICanGoOnLadderUp();

        assertF("☼☼☼☼☼\n" +
                "☼  A☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  A☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  A☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу спустится вниз, но не дальше границы экрана
    @Test
    public void shouldICanGoOnLadderDown() {
        // given
        shouldICanGoOnLadderUp();

        assertF("☼☼☼☼☼\n" +
                "☼  A☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  A☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  A☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  A☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу в любой момент спрыгнуть с лестницы и я буду падать до тех пор пока не наткнусь на препятствие
    @Test
    public void shouldICanFly() {
        // given
        shouldICanGoOnLadderUp();

        assertF("☼☼☼☼☼\n" +
                "☼  A☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼ UH☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼ UH☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ ◄H☼\n" +
                "☼☼☼☼☼\n");
    }

    // под стеной я не могу прострелить
    @Test
    public void shouldICantCrackUnderLadder() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼ ►H☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  H☼\n" +
                "☼ ►H☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // под уликой я не могу прострелить
    @Test
    public void shouldICantCrackUnderClue() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►$☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►$☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу поднятся по лестнице и зайти на площадку
    @Test
    public void shouldICanGoFromLadderToArea() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼H◄ ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼A  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼A# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼A  ☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼H► ☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        // и упасть
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼H U☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼H#U☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼H ►☼\n" +
                "☼☼☼☼☼\n");

    }

    // я не могу прострелить бетон
    @Test
    public void shouldICantCrackWall() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ► ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");
    }

    // пока я падаю я не могу двигаться влево и справо, даже если там есть площадки
    @Test
    public void shouldICantMoveWhenFall() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ►  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  U  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  U  ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  U  ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼►~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ I~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~I~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~I ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // с трубы я могу спрыгунть и тогда я буду падать до препятствия
    @Test
    public void shouldIFallFromPipe() {
        // given
        shouldIPipe();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~I ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~~U☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#   U☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼    U☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ $~~◄☼\n" +
                "☼ $  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ $~I ☼\n" +
                "☼ $  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼ $I~ ☼\n" +
                "☼ $  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        dice(1, 5); // clue
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼$    ☼\n" +
                "☼ U~~ ☼\n" +
                "☼ $  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        // when
        dice(2, 5); // clue
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼$$   ☼\n" +
                "☼  ~~ ☼\n" +
                "☼ U  #☼\n" +
                "☼ $   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(2)]");

        // when
        dice(3, 5); // clue
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼$$$  ☼\n" +
                "☼  ~~ ☼\n" +
                "☼    #☼\n" +
                "☼ U   ☼\n" +
                "☼ $   ☼\n" +
                "☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(3)]");

        // when
        dice(4, 5); // clue
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼#####☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼#*###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼ U   ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼#U###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼ U   ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ U   ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼ U   ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼#U###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼ U   ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ I~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ I~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // TODO я могу прострелить дырку под лестницей, а потом спуститься туда

    // я не могу прострелить дырку под другим камнем
    @Test
    public void shouldICantCrackUnderBrick() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►#☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►#☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу спрыгнуть с трубы
    @Test
    public void shouldCanJumpFromPipe() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼  U  ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~I~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼  U  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
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
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼ ◄ ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ A ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ A ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldCantWalkThroughWallUp() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼ H ☼\n" +
                "☼ H◄☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼ H ☼\n" +
                "☼ A ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼ A ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼ A ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // бага: мне нельзя проходить с лестницы через бетон направо или налево
    @Test
    public void shouldCantWalkThroughWallLeftRight() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼H☼☼\n" +
                "☼ H◄☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼H☼☼\n" +
                "☼ A ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼A☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼A☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼A☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // бага: мне нельзя проходить через бетон
    @Test
    public void shouldCantWalkThroughWallLeftRight2() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼◄☼☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼◄☼☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼►☼☼\n" +
                "☼☼☼☼☼\n");
    }

    // бага: мне нельзя спрыгивать с трубы что сразу над бетоном, протелая сквозь него
    @Test
    public void shouldCantJumpThroughWall() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼  ◄ ☼\n" +
                "☼  ~ ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  I ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  I ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    @Test // TODO fix me in *.data
    public void shouldBoardIsFree() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼~◄$H☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼☼☼☼☼☼\n");

        // when then
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
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼  ◄ ☼\n" +
                "☼  ~ ☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  I ☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().down();
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  I ☼\n" +
                "☼    ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  ~ ☼\n" +
                "☼  ◄ ☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼  ~ ☼\n" +
                "☼  ◄ ☼\n" +
                "☼#*##☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // если я сам себя закопаю, то получу ли я за это очки? не должен!
    @Test
    public void shouldNoScoreWhenSuicide() {
        // given
        givenFl("☼☼☼☼\n" +
                "☼ ◄☼\n" +
                "☼##☼\n" +
                "☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        hero().left();
        tick();
        tick();

        // then
        assertF("☼☼☼☼\n" +
                "☼  ☼\n" +
                "☼E#☼\n" +
                "☼☼☼☼\n");

        // when
        for (int count = 3; count < Brick.CRACK_TIMER; count++) {
            tick();
        }

        // then
        assertF("☼☼☼☼\n" +
                "☼  ☼\n" +
                "☼O#☼\n" +
                "☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼\n" +
                "☼  ☼\n" +
                "☼O#☼\n" +
                "☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED, SUICIDE]");
    }

    // я могу прострелить стенки под стенками, если те разрушены
    @Test
    public void shouldCrackUnderCrackedBrick() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ ◄  ☼\n" +
                "☼####☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        hero().right();
        tick();

        hero().crack(LEFT);
        tick();

        hero().right();
        tick();

        hero().crack(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼   ◄☼\n" +
                "☼  *#☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();
        tick();

        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ E #☼\n" +
                "☼####☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().crack(RIGHT);
        tick();

        hero().crack(LEFT);
        tick();

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼2E #☼\n" +
                "☼ # #☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼U3 #☼\n" +
                "☼ # #☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼#24#☼\n" +
                "☼E# #☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // если я спрыгива с последней секции лестницы, то как-то неудачно этто делаю. Бага!
    @Test
    public void shouldJumpFromLadderDown() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼ ◄  ☼\n" +
                "☼ H##☼\n" +
                "☼#H  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().down();
        tick();

        hero().down();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#A  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#A  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#HU ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#H  ☼\n" +
                "☼  ► ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // вор двигается так же как и обычный игрок - может ходить влево и вправо
    @Test
    public void shouldRobberMoveLeft() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ ) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼)  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberMoveRight() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ ) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼  (☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // если небыло команды вор никуда не идет
    @Test
    public void shouldRobberStopWhenNoMoreRightCommand() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼  )☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ ) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ ) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вор останавливается возле границы
    @Test
    public void shouldRobberStopWhenWallRight() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼  (☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼  (☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberStopWhenWallLeft() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼)  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼)  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // В простреленную яму вор легко может упасть
    @Test
    public void shouldRobberFallInPitLeft() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼) ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ x◄☼\n" +
                "☼# #☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼#y#☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberFallInPitRight() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄ )☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack(RIGHT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼► )☼\n" +
                "☼#*#☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►x ☼\n" +
                "☼# #☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼#y#☼\n" +
                "☼☼☼☼☼\n");
    }

    // при падении вор не может передвигаться влево и вправо - ему мешают стены
    @Test
    public void shouldRobberCantGoLeftIfWall() {
        // given
        shouldRobberFallInPitRight();

        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►  ☼\n" +
                "☼#y#☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberCantGoRightIfWall() {
        // given
        shouldRobberFallInPitLeft();

        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼#y#☼\n" +
                "☼☼☼☼☼\n");
    }

    // монстр сидит в ямке некоторое количество тиков, потом он вылазит
    @Test
    public void shouldMonsterGetUpFromPit() {
        // given
        shouldRobberFallInPitLeft();

        // when
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

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ◄☼\n" +
                "☼#y#☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ (◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        // после этого он может двигаться
        robber().left(); 
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼) ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // если вор попадает на героя - тот погибает
    @Test
    public void shouldHeroDieWhenMeetWithRobber() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄ )☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ O ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED]");

        // when
        // ну а после смерти он появляется в рендомном месте причем вор остается на своем месте
        dice(1, 3);
        tick();         
        field().newGame(player());

        // then
        assertF("☼☼☼☼☼\n" +
                "☼U  ☼\n" +
                "☼ ) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // другой кейс, когда оба двигаются на встречу друг к другу
    @Test
    public void shouldHeroDieWhenMeetWithRobber2() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼)O ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED]");

        // when
        // ну а после смерти он появляется в рендомном месте 
        // причем вор остается на своем месте
        dice(0,  // охотимся за первым игроком
            3, 3);
        tick();         
        field().newGame(player());

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  U☼\n" +
                "☼)  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // другой кейс, когда игрок идет на вора
    @Test
    public void shouldHeroDieWhenMeetWithRobber_whenHeroWalk() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ O ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED]");

        // when
        // ну а после смерти он появляется в рендомном месте 
        // причем вор остается на своем месте
        dice(3, 3);
        tick();         
        field().newGame(player());

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  U☼\n" +
                "☼ ) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // другой кейс, когда вор идет на игрока
    @Test
    public void shouldHeroDieWhenMeetWithRobber_whenRobberWalk() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼◄) ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼O  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED]");

        // when
        // ну а после смерти он появляется в рендомном месте 
        // причем вор остается на своем месте
        dice(0,  // охотимся за первым игроком
            3, 3);
        tick();         
        field().newGame(player());

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  U☼\n" +
                "☼)  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вор может зайти на лестницу и выйти обратно
    @Test
    public void shouldRobberCanGoOnLadder() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ )H☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  X☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ )H☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вор может карабкаться по лестнице вверх
    @Test
    public void shouldRobberCanGoOnLadderUp() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ )H☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  X☼\n" +
                "☼☼☼☼☼\n");

        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  X☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
                
        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  X☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вор не может вылезти с лестницей за границы
    @Test
    public void shouldRobberCantGoOnBarrierFromLadder() {
        // given
        shouldRobberCanGoOnLadderUp();

        assertF("☼☼☼☼►\n" +
                "☼  X☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  X☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  X☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вор может спускаться, но не дальше границы экрана
    @Test
    public void shouldRobberCanGoOnLadderDown() {
        // given
        shouldRobberCanGoOnLadderUp();

        assertF("☼☼☼☼►\n" +
                "☼  X☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  X☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  X☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼  X☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вор может в любой момент спрыгнуть с лестницы 
    // и будет падать до тех, пор пока не наткнется на препятствие
    @Test
    public void shouldRobberCanFly() {
        // given
        shouldRobberCanGoOnLadderUp();

        assertF("☼☼☼☼►\n" +
                "☼  X☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼ xH☼\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼ xH☼\n" +
                "☼  H☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼  H☼\n" +
                "☼  H☼\n" +
                "☼ )H☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вор может поднятся по лестнице и зайти на площадку
    @Test
    public void shouldRobberCanGoFromLadderToArea() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼H) ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼X  ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼X# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼X  ☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼H( ☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        // и упасть
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼H x☼\n" +
                "☼H# ☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼H#x☼\n" +
                "☼H  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼H  ☼\n" +
                "☼H# ☼\n" +
                "☼H (☼\n" +
                "☼☼☼☼☼\n");
    }

    // пока вор падает он не может двигаться влево и справо, даже если там есть площадки
    @Test
    public void shouldRobberCantMoveWhenFall() {
        // given
        givenFl("☼☼☼☼☼☼►\n" +
                "☼  (  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼  x  ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  x  ☼\n" +
                "☼     ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼  x  ☼\n" +
                "☼## ##☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼##(##☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // если вор с площадки заходит на трубу то он ползет по ней
    @Test
    public void shouldRobberPipe() {
        // given
        givenFl("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼(~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ Y~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~Y~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~Y ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // с трубы вор может спрыгунть и тогда он будет падать до препятствия
    @Test
    public void shouldRobberFallFromPipe() {
        // given
        shouldRobberPipe();

        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~Y ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~~x☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#   x☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼    x☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼#    ☼\n" +
                "☼     ☼\n" +
                "☼    (☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // вор может похитить 1 улику в падении
    @Test
    public void shouldRobberGetClueWhenFallenFromPipe() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  $~~)☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  $~Y ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  $Y~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        dice(-1); // нового не генерим
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  x~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // вор не берет больше 1 улики
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  x  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  x   ☼\n" +
                "☼  $  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  )  ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberLeaveClueWhenFallInPit() {
        // given
        shouldRobberGetClueWhenFallenFromPipe();
        
        // when
        robber().right();

        hero().crack();

        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $( ◄☼\n" +
                "☼####*#☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        // вор с уликой падает в ямку
        robber().right();
        tick();

        // then
        // он оставляет улику на поверхности
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ x◄☼\n" +
                "☼#### #☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ $◄☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldIWalkOnRobberInPitAndGetClue() {
        // given
        shouldRobberLeaveClueWhenFallInPit();

        // when
        // герой может пройти по нему сверху и забрать улику
        hero().left();
        dice(1, 6); // gold
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$     ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ ◄ ☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$     ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $◄  ☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        dice(2, 6); // gold
        tick();

        verifyAllEvents("[GET_CLUE_KNIFE(2)]");

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ◄   ☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldNoMoreClueARobber() {
        // given
        shouldIWalkOnRobberInPitAndGetClue();

        // when
        for (int count = 4; count < Brick.CRACK_TIMER; count++) { // враг вылазит
            tick();
        }
        
        robber().left();
        hero().crack(RIGHT);
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ► ) ☼\n" +
                "☼###*##☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ►x  ☼\n" +
                "☼### ##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // улики у него больше нет
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ►   ☼\n" +
                "☼###y##☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // я могу ходить по монстру, который в ямке
    @Test
    public void shouldIWalkOnRobber() {
        // given
        shouldRobberLeaveClueWhenFallInPit();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ $◄☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(1, 6); // gold
        hero().left();
        tick();

        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$     ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ ◄ ☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$     ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $◄  ☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(2, 6); // gold
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ◄   ☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        verifyAllEvents("[GET_CLUE_KNIFE(2)]");

        // when
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼$$    ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  ◄ ( ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // я не могу прострелить дырку непосредственно под монстром
    // TODO сделать так, чтобы мог
    @Test
    public void shouldICantCrackUnderRobber() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►(☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ►(☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    // если я прострелил дырку монстр падает в нее, а под ней ничего нет - монстр не проваливается сквозь
    @Test
    public void shouldRobberStayOnPitWhenUnderPitIsFree() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
                "☼( ◄  ☼\n" +
                "☼#####☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼( ◄  ☼\n" +
                "☼#*###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼ x◄  ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼#y###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
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

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼  ◄  ☼\n" +
                "☼#y###☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼\n" +
                "☼ (◄  ☼\n" +
                "☼#####☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // если в процессе падения вор вдург наткнулся на трубу то он повисаю на ней
    @Test
    public void shouldRobberPipeWhenFall() {
        // given
        givenFl("☼☼☼☼☼☼►\n" +
                "☼  (  ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼ x   ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼#x###☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼ x   ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ Y~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼# ###☼\n" +
                "☼     ☼\n" +
                "☼ Y~~ ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // вор может спрыгнуть с трубы
    @Test
    public void shouldRobberCanJumpFromPipe() {
        // given
        givenFl("☼☼☼☼☼☼►\n" +
                "☼  )  ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼  x  ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~Y~ ☼\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼  x  ☼\n" +
                "☼     ☼\n" +
                "☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼►\n" +
                "☼     ☼\n" +
                "☼     ☼\n" +
                "☼ ~~~ ☼\n" +
                "☼     ☼\n" +
                "☼  )  ☼\n" +
                "☼☼☼☼☼☼☼\n");
    }

    // вору нельзя спускаться с лестницы в бетон, так же как и подниматься
    // плюс вор должен иметь возможность спустится по лестнице
    @Test
    public void shouldRobberCantWalkThroughWallDown() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼ ) ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ X ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼ X ☼\n" +
                "☼☼☼☼☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberCantWalkThroughWallUp() {
        // given
        settings().integer(ROBBERS_COUNT, 1);
        
        givenFl("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼ H ☼\n" +
                "☼ H)☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼ H ☼\n" +
                "☼ X ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼ X ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼ X ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вору нельзя проходить с лестницы через бетон направо или налево
    @Test
    public void shouldRobberCantWalkThroughWallLeftRight() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼H☼☼\n" +
                "☼ H)☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼H☼☼\n" +
                "☼ X ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼X☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼X☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼☼☼☼☼\n" +
                "☼☼X☼☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // вору нельзя проходить через бетон
    @Test
    public void shouldRobberCantWalkThroughWallLeftRight2() {
        // given
        givenFl("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼)☼☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼)☼☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼►\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼(☼☼\n" +
                "☼☼☼☼☼\n");
    }

    // Вору нельзя спрыгивать с трубы что сразу над бетоном, протелая сквозь него
    @Test
    public void shouldRobberCantJumpThroughWall() {
        // given
        givenFl("☼☼☼☼☼►\n" +
                "☼  ( ☼\n" +
                "☼  ~ ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼  Y ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼  Y ☼\n" +
                "☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // если вор спрыгивает с последней секции лестницы
    @Test
    public void shouldRobberJumpFromLadderDown() {
        // given
        givenFl("☼☼☼☼☼►\n" +
                "☼ (  ☼\n" +
                "☼ H##☼\n" +
                "☼#H  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();
        
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#X  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#X  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#Hx ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼ H##☼\n" +
                "☼#H  ☼\n" +
                "☼  ( ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // Вор не может прыгять вверх :)
    @Test
    public void shouldRobberCantJump() {
        // given
        givenFl("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ (  ☼\n" +
                "☼☼☼☼☼☼\n");
        
        // when
        robber().up();
        tick();

        // then
        assertF("☼☼☼☼☼►\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼ (  ☼\n" +
                "☼☼☼☼☼☼\n");
    }

    // я могу прыгнуть на голову монстру и мне ничего не будет
    @Test
    public void shouldICanJumpAtRobberHead() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼ ◄ ☼\n" +
                "☼   ☼\n" +
                "☼ ( ☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ( ☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼ ( ☼\n" +
                "☼☼☼☼☼\n");
        
        // when
        // если он отойдет
        robber().right();
        tick();

        // then
        // я упаду дальше
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ U ☼\n" +
                "☼  (☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ ◄(☼\n" +
                "☼☼☼☼☼\n");
    }

    // если вор после того, как упал в ямку оставил улику, то когда он выберется - он свою улику заберет
    // А когда снова упадет, то оставит
    @Test
    public void shouldGetClueWheExitFromPit() {
        // given
        shouldRobberLeaveClueWhenFallInPit();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ $◄☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // враг вылазит
        for (int count = 3; count < Brick.CRACK_TIMER; count++) {
            tick();
        }
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ (◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $) ◄☼\n" +
                "☼####*#☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼   ~~ ☼\n" +
                "☼  $  #☼\n" +
                "☼  $   ☼\n" +
                "☼  $ $◄☼\n" +
                "☼####y#☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // Если вор упал на другого вора который был на трубе, то они складываются в один :)
    @Test
    public void shouldRobberStayOnOtherAtThePipe() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼  )   ☼\n" +
                "☼      ☼\n" +
                "☼  )   ☼\n" +
                "☼  ~   ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  x   ☼\n" +
                "☼      ☼\n" +
                "☼  Y   ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  )   ☼\n" +
                "☼  Y   ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().down();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  Y   ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  Y   ☼\n" +
                "☼ )   ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldRobberDontStopOnPipe() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ))  ☼\n" +
                "☼  ~~  ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  YY  ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ~Y  ☼\n" +
                "☼     ◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ~Y  ☼\n" +
                "☼    (◄☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // Вор должен сам проваливаться на героя, а не впрыгивать в него
    @Test
    public void shouldRobberStayOnHeroAtThePipe() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼  )   ☼\n" +
                "☼      ☼\n" +
                "☼  ◄   ☼\n" +
                "☼  ~   ☼\n" +
                "☼      ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼  x   ☼\n" +
                "☼      ☼\n" +
                "☼  I   ☼\n" +
                "☼      ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();
        tick();

        // then
        verifyAllEvents("[HERO_DIED]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  O   ☼\n" +
                "☼      ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().down();
        dice(2, 3);
        field().newGame(player());
        dice(0);  // охотимся за первым игроком
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ~   ☼\n" +
                "☼ ►)   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    // Вор проваливается в яму за героем и там его находит
    @Test
    public void shouldRobberFindHeroAtPit() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ) ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().crack();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ) ◄ ☼\n" +
                "☼###*##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().left();
        tick();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  )   ☼\n" +
                "☼###E##☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().right();
        tick();
        tick();

        // then
        verifyAllEvents("[HERO_DIED, SUICIDE]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼###O##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(2, 3);
        field().newGame(player());
        dice(0);  // охотимся за первым игроком
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►    ☼\n" +
                "☼###y##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // враг вылазит
        for (int count = 5; count < Brick.CRACK_TIMER; count++) {
            tick();
        }

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ► (  ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

    }

    @Test
    public void iLooseScoresWhenDoHarakiri() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().suicide();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ O ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[HERO_DIED, SUICIDE]");
    }

    @Test
    public void iCanJumpThroughBackWays() {
        // given
        settings().integer(BACKWAYS_COUNT, 2);

        dice(1, 2,
            3, 3);
        givenFl("☼☼☼☼☼\n" +
                "☼  W☼\n" +
                "☼W◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  U☼\n" +
                "☼W  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  W☼\n" +
                "☼W ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼  W☼\n" +
                "☼W ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void backWaysAreRecreatedEveryFewTicks() {
        // given
        settings().integer(BACKWAYS_COUNT, 2)
                .integer(BACKWAY_TICKS, 5);

        givenFl("☼☼☼☼☼\n" +
                "☼  W☼\n" +
                "☼W◄ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertEquals(5, field().getBackWaysTimer());

        // when
        hero().left();
        tick();

        // then
        assertEquals(4, field().getBackWaysTimer());

        assertF("☼☼☼☼☼\n" +
                "☼  U☼\n" +
                "☼W  ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertEquals(3, field().getBackWaysTimer());

        assertF("☼☼☼☼☼\n" +
                "☼  W☼\n" +
                "☼W ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertEquals(2, field().getBackWaysTimer());

        // when
        tick();

        // then
        assertEquals(1, field().getBackWaysTimer());

        // when
        tick();

        // then
        assertEquals(0, field().getBackWaysTimer());

        assertF("☼☼☼☼☼\n" +
                "☼  W☼\n" +
                "☼W ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        // when
        dice(1, 3, // backway
            2, 3); // backway
        tick();

        // then
        assertEquals(5, field().getBackWaysTimer());

        assertF("☼☼☼☼☼\n" +
                "☼WW ☼\n" +
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
                "☼  ) ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ) ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
        
        // when
        robber().left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ )  ◄ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        dice(1, 2); // hero
        field().clearScore();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼► )   ☼\n" +
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

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ◄ « ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero(0).crack(RIGHT);
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►F  ☼\n" +
                "☼###*##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►   ☼\n" +
                "☼###K##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

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
                "☼  ►   ☼\n" +
                "☼###K##☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();


        // then
        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");

        assertEquals(20, hero(0).scores());
        assertEquals(0, hero(1).scores());
        assertEquals(true, hero(0).isAlive());
        assertEquals(false, hero(1).isAlive());

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►   ☼\n" +
                "☼###C##☼\n" +
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
                "☼►    »☼\n" +
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

        // when
        dice(-1); // ничего нового не генерим
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►$$&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        verifyAllEvents("[GET_CLUE_KNIFE(1)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ►$&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        verifyAllEvents("[GET_CLUE_KNIFE(2)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  ►&@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        verifyAllEvents("[GET_CLUE_GLOVE(1)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼   ►@@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        verifyAllEvents("[GET_CLUE_RING(1)]");

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼    ►@☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
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

        // then
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
        dice(2, 3, // knife
            3, 3,  // knife
            4, 3,  // glove
            5, 3,  // ring
            6, 3,  // ring
            6, 4,  // ring
            1, 6); // hero
        field().clearScore();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼U     ☼\n" +
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

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼ U    ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ $ &@ ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldEndlesslyWalkThroughTheBackWays_untilExit() {
        // given
        settings().integer(BACKWAYS_COUNT, 3);

        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼►W    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼►W    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   U  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ W    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼   U  ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ W    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ ►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // что интересно, если ты не выйдешь из черный хода то в следующий тик отправишься дальше
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   U  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ W    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼   U  ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ W    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ ►    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ W►   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");
    }

    @Test
    public void shouldResetBackWays_whenClearBoard() {
        // given
        shouldEndlesslyWalkThroughTheBackWays_untilExit();

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ W►   ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // добавим еще один черный ход
        settings().integer(BACKWAYS_COUNT, settings().integer(BACKWAYS_COUNT) + 1);
        dice(2, 4, // backway
            1, 2); // hero
        field().clearScore();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼ W W  ☼\n" +
                "☼      ☼\n" +
                "☼►W    ☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // оставим два черный хода
        settings().integer(BACKWAYS_COUNT, 2);
        dice(1, 2); // hero
        field().clearScore();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼   W  ☼\n" +
                "☼      ☼\n" +
                "☼   W  ☼\n" +
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
                "☼►m m m☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►m m m☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals(false, hero().under(MASK_POTION));

        // when
        dice(-1); // ничего нового не генерим

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

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼     w☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        assertEquals(true, hero().under(MASK_POTION));

        // when
        // почистим все
        dice(1, 2);  // hero
        field().clearScore();

        // then
        assertEquals(false, hero().under(MASK_POTION));

        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►m m m☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // добавим еще
        settings().integer(MASK_POTIONS_COUNT, settings().integer(MASK_POTIONS_COUNT) + 2);
        dice(3, 3, // potion
            5, 3,  // potion
            1, 6); // hero
        field().clearScore();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼U     ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼  m m ☼\n" +
                "☼ m m m☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // when
        // оставим только 1
        settings().integer(MASK_POTIONS_COUNT, 1);
        dice(1, 2);  // hero
        field().clearScore();

        // then
        assertF("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼►m    ☼\n" +
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

        // when
        tick();

        // then
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
        dice(1, 2); // hero
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
        dice(2, 2); // hero
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
    public void shouldResetBackWaysTimeout_whenClearBoard() {
        // given
        backWaysAreRecreatedEveryFewTicks();

        assertF("☼☼☼☼☼\n" +
                "☼WW ☼\n" +
                "☼  ◄☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertEquals(5, field().getBackWaysTimer());

        // when
        tick();
        tick();
        tick();

        // then
        assertEquals(2, field().getBackWaysTimer());

        // when
        dice(3, 3, // backway
            3, 2,  // backway
            1, 2); // hero
        field().clearScore();

        // then
        assertEquals(5, field().getBackWaysTimer());
    }

    @Ignore // TODO please fix me
    @Test
    public void accessGivenBullets() {
        // given
        givenFl("☼☼☼☼☼☼☼☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼      ☼\n" +
                "☼ ► •••☼\n" +
                "☼######☼\n" +
                "☼☼☼☼☼☼☼☼\n");

        // then
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
        // second level selected
        dice(1); 
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
        // when
        hero().right();
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

        assertEquals(2, hero(0).scores());

        // when
        hero().crack(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼$  ☼\n" +
                "☼ ◄ ☼\n" +
                "☼*##☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼$  ☼\n" +
                "☼  ►☼\n" +
                "☼ ##☼\n" +
                "☼☼☼☼☼\n");

        // when
        dice(1, 2); // hero
        field().clearScore();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼►$ ☼\n" +
                "☼###☼\n" +
                "☼☼☼☼☼\n");

        assertEquals(0, hero(0).scores());
    }

    // я не провалюсь через пол, если там дырка (ошибка сенсея)
    @Test
    public void shouldStopHero_whenBoardIsNotSquare() {
        // given when
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n"); // ошибка, лишняя линия

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ U ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n");
    }

    // я не провалюсь через пол, если там дырка (ошибка сенсея)
    @Test
    public void shouldStopHero_whenBoardHasNoBorder_caseDown() {
        // given when
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ◄ ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ U ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ U ☼\n" +
                "☼   ☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ U ☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ U ☼\n");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼ U ☼\n");
    }

    // я не могу двигаться вправо за пределы поля,
    // если карта нарисована без границы (ошибка сенсея)
    @Test
    public void shouldStopHero_whenBoardHasNoBorder_caseRight() {
        // given when
        givenFl("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼  ◄ \n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼  ◄ \n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼   ►\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼   ►\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼   ►\n" +
                "☼☼☼☼☼\n");
    }

    // я не могу двигаться влево за пределы поля,
    // если карта нарисована без границы (ошибка сенсея)
    @Test
    public void shouldStopHero_whenBoardHasNoBorder_caseLeft() {
        // given when
        givenFl("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                " ◄  ☼\n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                " ◄  ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                "◄   ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                "◄   ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                "◄   ☼\n" +
                "☼☼☼☼☼\n");
    }

    // я не могу двигаться вверх за пределы поля,
    // если карта нарисована без границы (ошибка сенсея)
    @Test
    public void shouldStopHero_whenBoardHasNoBorder_caseUp() {
        // given when
        givenFl("☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼◄H ☼\n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼◄H ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().right();
        tick();

        hero().up();
        tick();

        hero().up();
        tick();

        hero().up();
        tick();

        // then
        assertF("☼ A ☼\n" +
                "☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼ A ☼\n" +
                "☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼ A ☼\n" +
                "☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼ H ☼\n" +
                "☼☼☼☼☼\n");
    }

    // снаряд не может двигаться вправо за пределы поля,
    // если карта нарисована без границы (ошибка сенсея)
    @Test
    public void shouldStopBullet_whenBoardHasNoBorder_caseRight() {
        // given when
        givenFl("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼◄   \n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼◄   \n" +
                "☼☼☼☼☼\n");

        // when
        hero().shoot(RIGHT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼►•  \n" +
                "☼☼☼☼☼\n");

        assertBullets("[[2,1,RIGHT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼►  •\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[4,1,RIGHT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼►   \n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼    \n" +
                "☼    \n" +
                "☼►   \n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");
    }

    // снаряд не может двигаться влево за пределы поля,
    // если карта нарисована без границы (ошибка сенсея)
    @Test
    public void shouldStopBullet_whenBoardHasNoBorder_caseLeft() {
        // given when
        givenFl("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                "  ◄ ☼\n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                "  ◄ ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().shoot(LEFT);
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                " •◄ ☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[[1,1,LEFT]]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                "  ◄ ☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");

        // when
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "    ☼\n" +
                "    ☼\n" +
                "  ◄ ☼\n" +
                "☼☼☼☼☼\n");

        assertBullets("[]");
    }

    // TODO прострелить находясь на трубе нельзя, в оригинале только находясь на краю трубы
    // TODO карта намного больше, чем квардартик вьюшка, и я подходя к границе просто передвигаю вьюшку
    // TODO повляется многопользовательский режим игры в формате "стенка на стенку"
}