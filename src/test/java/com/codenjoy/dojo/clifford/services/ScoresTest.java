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


import com.codenjoy.dojo.services.PlayerScores;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.Events.Event.*;
import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static org.junit.Assert.assertEquals;

public class ScoresTest {

    private PlayerScores scores;
    private GameSettings settings;

    public void heroDie() {
        scores.event(new Events(HERO_DIE));
    }

    public void killHero() {
        scores.event(new Events(KILL_HERO));
    }

    public void killEnemy() {
        scores.event(new Events(KILL_ENEMY));
    }

    public void suicide() {
        scores.event(new Events(SUICIDE));
    }

    public void clueKnife(int amount) {
        scores.event(new Events(GET_CLUE_KNIFE).with(amount));
    }

    public void clueRing(int amount) {
        scores.event(new Events(GET_CLUE_RING).with(amount));
    }

    public void clueGlove(int amount) {
        scores.event(new Events(GET_CLUE_GLOVE).with(amount));
    }

    @Before
    public void setup() {
        settings = new GameSettings()
                .integer(SUICIDE_PENALTY, 13)
                .integer(HERO_DIE_PENALTY, 30)

                .integer(KILL_HERO_SCORE, 20)
                .integer(KILL_ENEMY_SCORE, 50)

                .integer(CLUE_SCORE_GLOVE, 2)
                .integer(CLUE_SCORE_GLOVE_INCREMENT, 1)

                .integer(CLUE_SCORE_KNIFE, 20)
                .integer(CLUE_SCORE_KNIFE_INCREMENT, 10)

                .integer(CLUE_SCORE_RING, 200)
                .integer(CLUE_SCORE_RING_INCREMENT, 100);

        scores = new Scores(0, settings);
    }

    @Test
    public void shouldCollectScores() {
        // given
        scores = new Scores(140, settings);

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
        assertEquals(140
                        + 2 * settings.integer(KILL_HERO_SCORE)
                        + settings.integer(KILL_ENEMY_SCORE)

                        + 5 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1 + 2 + 3 + 4) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT)

                        + 3 * settings.integer(CLUE_SCORE_RING)
                        + (1 + 2) * settings.integer(CLUE_SCORE_RING_INCREMENT)

                        + 2 * settings.integer(CLUE_SCORE_GLOVE)
                        + (1) * settings.integer(CLUE_SCORE_GLOVE_INCREMENT)

                        - settings.integer(HERO_DIE_PENALTY),
                scores.getScore());
    }

    @Test
    public void shouldStillZeroAfterDead() {
        heroDie();

        assertEquals(0, scores.getScore());
    }

    @Test
    public void shouldClearScore() {
        // given
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
    public void shouldIncreaseForNextClue_stepByStep() {
        // given
        scores = new Scores(0, settings);

        // when
        clueKnife(0);

        // then
        assertEquals(1 * settings.integer(CLUE_SCORE_KNIFE)
                        + (0) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());

        // when
        clueKnife(1);

        // then
        assertEquals(2 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());

        // when
        clueKnife(2);

        // then
        assertEquals(3 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1 + 2) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());

        // when
        clueKnife(3);

        // then
        assertEquals(4 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1 + 2 + 3) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());

        // when
        clueKnife(4);

        // then
        assertEquals(5 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1 + 2 + 3 + 4) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfGameOver() {
        // given
        scores = new Scores(0, settings);

        clueKnife(0);
        clueKnife(1);
        clueKnife(2);

        // when
        heroDie();

        // then
        Integer score = (Integer) scores.getScore();
        assertEquals(3 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1 + 2) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT)
                        - settings.integer(HERO_DIE_PENALTY),
                (int) score);

        // when
        clueKnife(0);
        clueKnife(1);

        // then
        assertEquals(score
                        + 2 * settings.integer(CLUE_SCORE_KNIFE)
                        + 1 * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfClean() {
        // given
        scores = new Scores(0, settings);

        clueKnife(0);
        clueKnife(1);
        clueKnife(2);

        // when
        scores.clear();

        assertEquals(0, scores.getScore());

        // then
        clueKnife(0);
        clueKnife(1);

        assertEquals(2 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfSuicide() {
        // given
        scores = new Scores(0, settings);

        clueKnife(0);
        clueKnife(1);
        clueKnife(2);

        // when
        suicide();

        int saved = -settings.integer(SUICIDE_PENALTY)
                + 3 * settings.integer(CLUE_SCORE_KNIFE)
                + (1 + 2) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);

        assertEquals(saved,
                scores.getScore());

        // then
        clueKnife(0);
        clueKnife(1);

        assertEquals(saved
                        + 2 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfHeroDie() {
        // given
        scores = new Scores(0, settings);

        clueKnife(0);
        clueKnife(1);
        clueKnife(2);

        // when
        heroDie();

        int saved = -settings.integer(HERO_DIE_PENALTY)
                + 3 * settings.integer(CLUE_SCORE_KNIFE)
                + (1 + 2) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);

        assertEquals(saved,
                scores.getScore());

        // then
        clueKnife(0);
        clueKnife(1);

        assertEquals(saved
                        + 2 * settings.integer(CLUE_SCORE_KNIFE)
                        + (1) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }


}
