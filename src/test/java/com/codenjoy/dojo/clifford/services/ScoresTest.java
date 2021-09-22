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

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static org.junit.Assert.assertEquals;

public class ScoresTest {

    private PlayerScores scores;
    private GameSettings settings;

    public void heroDie() {
        scores.event(Events.HERO_DIE);
    }

    public void killHero() {
        scores.event(Events.KILL_HERO);
    }

    public void killEnemy() {
        scores.event(Events.KILL_ENEMY);
    }

    public void suicide() {
        scores.event(Events.SUICIDE);
    }

    public void clueKnife() {
        scores.event(Events.GET_CLUE_KNIFE);
    }

    public void clueRing() {
        scores.event(Events.GET_CLUE_RING);
    }

    public void clueGlove() {
        scores.event(Events.GET_CLUE_GLOVE);
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
        scores = new Scores(140, settings);

        killHero();
        killHero();
        killEnemy();

        clueKnife();
        clueRing();
        clueRing();
        clueRing();
        clueGlove();
        clueGlove();
        clueKnife();
        clueKnife();
        clueKnife();
        clueKnife();

        heroDie();

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
        clueKnife();
        clueRing();
        clueRing();
        clueGlove();
        clueGlove();
        clueGlove();

        assertEquals(529, scores.getScore());
        assertEquals("Scores{score=529, ring=200, glove=3, knife=10}",
                scores.toString());

        // when
        scores.clear();

        // then
        assertEquals(0, scores.getScore());
        assertEquals("Scores{score=0, ring=0, glove=0, knife=0}",
                scores.toString());
    }

    @Test
    public void shouldIncreaseForNextClue() {
        scores = new Scores(0, settings);

        clueKnife();
        clueKnife();
        clueKnife();
        clueKnife();

        assertEquals(4 * settings.integer(CLUE_SCORE_KNIFE)
                + (1 + 2 + 3) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfGameOver() {
        // given
        scores = new Scores(0, settings);

        clueKnife();
        clueKnife();
        clueKnife();

        // when
        heroDie();

        // then
        Integer score = (Integer) scores.getScore();
        assertEquals(3 * settings.integer(CLUE_SCORE_KNIFE)
                    + (1 + 2) * settings.integer(CLUE_SCORE_KNIFE_INCREMENT)
                    - settings.integer(HERO_DIE_PENALTY),
                (int)score);

        // when
        clueKnife();
        clueKnife();

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

        clueKnife();
        clueKnife();
        clueKnife();

        // when
        scores.clear();

        assertEquals(0, scores.getScore());

        // then
        clueKnife();
        clueKnife();

        assertEquals(2 * settings.integer(CLUE_SCORE_KNIFE)
                        + 1 * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfSuicide() {
        // given
        scores = new Scores(0, settings);

        clueKnife();
        clueKnife();
        clueKnife();

        // when
        suicide();

        int saved = - settings.integer(SUICIDE_PENALTY)
                + 3 * settings.integer(CLUE_SCORE_KNIFE)
                + 3 * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        assertEquals(saved,
                scores.getScore());

        // then
        clueKnife();
        clueKnife();

        assertEquals(saved
                        + 2 * settings.integer(CLUE_SCORE_KNIFE)
                        + 1 * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }

    @Test
    public void shouldCleanIncreasedIfHeroDie() {
        // given
        scores = new Scores(0, settings);

        clueKnife();
        clueKnife();
        clueKnife();

        // when
        heroDie();

        int saved = - settings.integer(HERO_DIE_PENALTY)
                + 3 * settings.integer(CLUE_SCORE_KNIFE)
                + 3 * settings.integer(CLUE_SCORE_KNIFE_INCREMENT);
        assertEquals(saved,
                scores.getScore());

        // then
        clueKnife();
        clueKnife();

        assertEquals(saved
                        + 2 * settings.integer(CLUE_SCORE_KNIFE)
                        + 1 * settings.integer(CLUE_SCORE_KNIFE_INCREMENT),
                scores.getScore());
    }


}
