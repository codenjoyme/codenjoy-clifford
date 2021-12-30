package com.codenjoy.dojo.clifford.services;

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


import com.codenjoy.dojo.clifford.TestGameSettings;
import com.codenjoy.dojo.services.PlayerScores;
import com.codenjoy.dojo.services.event.Calculator;
import com.codenjoy.dojo.services.event.ScoresImpl;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.Event.Type.*;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.event.Mode.CUMULATIVELY;
import static com.codenjoy.dojo.services.event.Mode.SERIES_MAX_VALUE;
import static org.junit.Assert.assertEquals;

public class ScoresTest {

    private PlayerScores scores;
    private GameSettings settings;

    public void heroDie() {
        scores.event(new Event(HERO_DIED));
    }

    public void killHero() {
        scores.event(new Event(KILL_OTHER_HERO));
    }

    public void killEnemy() {
        scores.event(new Event(KILL_ENEMY_HERO));
    }

    public void suicide() {
        scores.event(new Event(SUICIDE));
    }

    public void clueKnife(int amount) {
        scores.event(new Event(GET_CLUE_KNIFE).with(amount));
    }

    public void clueRing(int amount) {
        scores.event(new Event(GET_CLUE_RING).with(amount));
    }

    public void clueGlove(int amount) {
        scores.event(new Event(GET_CLUE_GLOVE).with(amount));
    }

    @Before
    public void setup() {
        settings = new TestGameSettings()
                .initScore(CUMULATIVELY)

                .integer(SUICIDE_PENALTY, -13)
                .integer(HERO_DIED_PENALTY, -30)

                .integer(KILL_OTHER_HERO_SCORE, 20)
                .integer(KILL_ENEMY_HERO_SCORE, 50)

                .integer(CLUE_SCORE_GLOVE, 2)
                .integer(CLUE_SCORE_GLOVE_INCREMENT, 1)

                .integer(CLUE_SCORE_KNIFE, 20)
                .integer(CLUE_SCORE_KNIFE_INCREMENT, 10)

                .integer(CLUE_SCORE_RING, 200)
                .integer(CLUE_SCORE_RING_INCREMENT, 100);
    }

    private void givenScores(int score) {
        scores = new ScoresImpl<>(score, new Calculator<>(new Scores(settings)));
    }

    @Test
    public void shouldCollectScores() {
        // given
        givenScores(140);

        // when
        killHero();
        killHero();
        killEnemy();

        clueKnife(0);
        clueRing(0);
        clueRing(1);
        clueRing(2);
        clueGlove(0);
        clueGlove(1);
        clueKnife(1);
        clueKnife(2);
        clueKnife(3);
        clueKnife(4);

        heroDie();

        // then
        int expected = 140;
        expected += 2 * settings.integer(KILL_OTHER_HERO_SCORE);
        expected += settings.integer(KILL_ENEMY_HERO_SCORE);

        expected += 5 * settings.integer(CLUE_SCORE_KNIFE);
        expected += (1 + 2 + 3 + 4) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);

        expected += 3 * settings.integer(CLUE_SCORE_RING);
        expected += (1 + 2) * settings.integer(CLUE_SCORE_RING_INCREMENT);

        expected += 2 * settings.integer(CLUE_SCORE_GLOVE);
        expected += settings.integer(CLUE_SCORE_GLOVE_INCREMENT);

        expected += settings.integer(HERO_DIED_PENALTY);
        assertEquals(expected, scores.getScore());
    }

    @Test
    public void shouldIncreaseForNextClue_stepByStep_knife() {
        // given
        givenScores(0);

        // when then
        clueKnife(0);
        int expected = settings.integer(CLUE_SCORE_KNIFE);
        assertEquals(expected, scores.getScore());

        // when then
        clueKnife(1);
        expected += settings.integer(CLUE_SCORE_KNIFE);
        expected += settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        assertEquals(expected, scores.getScore());

        // when then
        clueKnife(2);
        expected += settings.integer(CLUE_SCORE_KNIFE);
        expected += 2 * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        assertEquals(expected, scores.getScore());

        // when then
        clueKnife(3);
        expected += settings.integer(CLUE_SCORE_KNIFE);
        expected += 3 * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        assertEquals(expected, scores.getScore());
    }

    @Test
    public void shouldIncreaseForNextClue_stepByStep_glove() {
        // given
        givenScores(0);

        // when then
        clueGlove(0);
        int expected = settings.integer(CLUE_SCORE_GLOVE);
        assertEquals(expected, scores.getScore());

        // when then
        clueGlove(1);
        expected += settings.integer(CLUE_SCORE_GLOVE);
        expected += settings.integer(CLUE_SCORE_GLOVE_INCREMENT);
        assertEquals(expected, scores.getScore());

        // when then
        clueGlove(2);
        expected += settings.integer(CLUE_SCORE_GLOVE);
        expected += 2 * settings.integer(CLUE_SCORE_GLOVE_INCREMENT);
        assertEquals(expected, scores.getScore());

        // when then
        clueGlove(3);
        expected += settings.integer(CLUE_SCORE_GLOVE);
        expected += 3 * settings.integer(CLUE_SCORE_GLOVE_INCREMENT);
        assertEquals(expected, scores.getScore());
    }

    @Test
    public void shouldIncreaseForNextClue_stepByStep_ring() {
        // given
        givenScores(0);

        // when then
        clueRing(0);
        int expected = settings.integer(CLUE_SCORE_RING);
        assertEquals(expected, scores.getScore());

        // when then
        clueRing(1);
        expected += settings.integer(CLUE_SCORE_RING);
        expected += settings.integer(CLUE_SCORE_RING_INCREMENT);
        assertEquals(expected, scores.getScore());

        // when then
        clueRing(2);
        expected += settings.integer(CLUE_SCORE_RING);
        expected += 2 * settings.integer(CLUE_SCORE_RING_INCREMENT);
        assertEquals(expected, scores.getScore());

        // when then
        clueRing(3);
        expected += settings.integer(CLUE_SCORE_RING);
        expected += 3 * settings.integer(CLUE_SCORE_RING_INCREMENT);
        assertEquals(expected, scores.getScore());
    }

    @Test
    public void shouldStillZeroAfterDead() {
        // given
        givenScores(0);

        // when
        heroDie();

        // then
        assertEquals(0, scores.getScore());
    }

    @Test
    public void shouldClearScore() {
        // given
        givenScores(0);

        clueKnife(0);
        clueRing(0);
        clueRing(1);
        clueGlove(0);
        clueGlove(1);
        clueGlove(2);

        assertEquals(529, scores.getScore());

        // when
        scores.clear();

        // then
        assertEquals(0, scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfGameOver() {
        // given
        givenScores(0);

        clueKnife(0);
        clueKnife(1);
        clueKnife(2);

        // when
        heroDie();

        // then
        int expected = 3 * settings.integer(CLUE_SCORE_KNIFE);
        expected += (1 + 2) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        expected += settings.integer(HERO_DIED_PENALTY);
        assertEquals(expected, scores.getScore());

        // when
        clueKnife(0);
        clueKnife(1);

        // then
        expected += 2 * settings.integer(CLUE_SCORE_KNIFE);
        expected += settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        assertEquals(expected, scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfClean() {
        // given
        givenScores(0);

        clueKnife(0);
        clueKnife(1);
        clueKnife(2);

        // when
        scores.clear();

        assertEquals(0, scores.getScore());

        // then
        clueKnife(0);
        clueKnife(1);

        int expected = 2 * settings.integer(CLUE_SCORE_KNIFE);
        expected += settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        assertEquals(expected, scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfSuicide() {
        // given
        givenScores(0);

        clueKnife(0);
        clueKnife(1);
        clueKnife(2);

        // when
        suicide();

        int expected = 3 * settings.integer(CLUE_SCORE_KNIFE);
        expected += (1 + 2) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        expected += settings.integer(SUICIDE_PENALTY);
        assertEquals(expected, scores.getScore());

        // then
        clueKnife(0);
        clueKnife(1);

        expected += 2 * settings.integer(CLUE_SCORE_KNIFE);
        expected += settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        assertEquals(expected, scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfHeroDie() {
        // given
        givenScores(0);

        clueKnife(0);
        clueKnife(1);
        clueKnife(2);

        // when
        heroDie();

        int expected = 3 * settings.integer(CLUE_SCORE_KNIFE);
        expected += (1 + 2) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        expected += settings.integer(HERO_DIED_PENALTY);
        assertEquals(expected, scores.getScore());

        // then
        clueKnife(0);

        expected += settings.integer(CLUE_SCORE_KNIFE);
        assertEquals(expected, scores.getScore());
    }

    @Test
    public void shouldCollectScores_seriesMaxScoresMode() {
        // given
        settings.initScore(SERIES_MAX_VALUE);
        givenScores(140);

        // then
        assertEquals(140, scores.getScore());
        assertEquals((Integer)0, ((ScoresImpl)scores).getSeries());

        // when
        clueKnife(0);
        clueRing(0);
        clueRing(1);
        clueRing(2);
        clueGlove(0);
        clueGlove(1);
        clueKnife(1);
        clueKnife(2);
        clueKnife(3);
        clueKnife(4);

        heroDie();

        // then
        assertEquals(1105, scores.getScore());
        assertEquals((Integer)0, ((ScoresImpl)scores).getSeries());

        // when
        clueKnife(0);
        clueRing(0);
        clueRing(1);
        clueRing(2);
        clueGlove(0);
        clueGlove(1);
        clueKnife(1);
        clueKnife(2);
        clueKnife(3);
        clueKnife(4);

        // then
        assertEquals(1105, scores.getScore());
        assertEquals((Integer)1105, ((ScoresImpl)scores).getSeries());

        // when
        clueKnife(5);
        clueKnife(6);

        // then
        assertEquals(1255, scores.getScore());
        assertEquals((Integer)1255, ((ScoresImpl)scores).getSeries());

        // when
        scores.clear();

        // then
        assertEquals(0, scores.getScore());
        assertEquals((Integer)0, ((ScoresImpl)scores).getSeries());
    }
}