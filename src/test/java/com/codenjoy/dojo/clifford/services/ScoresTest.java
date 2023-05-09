package com.codenjoy.dojo.clifford.services;

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
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.clifford.TestGameSettings;
import com.codenjoy.dojo.services.event.EventObject;
import com.codenjoy.dojo.services.event.ScoresMap;
import com.codenjoy.dojo.utils.scorestest.AbstractScoresTest;
import org.junit.Test;

import static com.codenjoy.dojo.clifford.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.event.Mode.CUMULATIVELY;
import static com.codenjoy.dojo.services.event.Mode.SERIES_MAX_VALUE;
import static org.junit.Assert.assertEquals;

public class ScoresTest extends AbstractScoresTest {

    @Override
    public GameSettings settings() {
        return new TestGameSettings()
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

    @Override
    protected Class<? extends ScoresMap> scores() {
        return Scores.class;
    }

    @Override
    protected Class<? extends EventObject> events() {
        return Event.class;
    }

    @Override
    protected Class<? extends Enum> eventTypes() {
        return Event.Type.class;
    }

    @Test
    public void shouldCollectScores() {
        assertEvents("140:\n" +
                "KILL_OTHER_HERO > +20 = 160\n" +
                "KILL_OTHER_HERO > +20 = 180\n" +
                "KILL_ENEMY_HERO > +50 = 230\n" +
                "GET_CLUE_KNIFE,0 > +20 = 250\n" +
                "GET_CLUE_RING,0 > +200 = 450\n" +
                "GET_CLUE_RING,1 > +300 = 750\n" +
                "GET_CLUE_RING,2 > +400 = 1150\n" +
                "GET_CLUE_GLOVE,0 > +2 = 1152\n" +
                "GET_CLUE_GLOVE,1 > +3 = 1155\n" +
                "GET_CLUE_KNIFE,1 > +30 = 1185\n" +
                "GET_CLUE_KNIFE,2 > +40 = 1225\n" +
                "GET_CLUE_KNIFE,3 > +50 = 1275\n" +
                "GET_CLUE_KNIFE,4 > +60 = 1335\n" +
                "HERO_DIED > -30 = 1305");
    }

    @Test
    public void shouldIncreaseForNextClue_stepByStep_knife() {
        // given
        settings.integer(CLUE_SCORE_KNIFE, 20)
                .integer(CLUE_SCORE_KNIFE_INCREMENT, 10);

        // when then
        assertEvents("0:\n" +
                "GET_CLUE_KNIFE,0 > +20 = 20\n" +
                "GET_CLUE_KNIFE,1 > +30 = 50\n" +
                "GET_CLUE_KNIFE,2 > +40 = 90\n" +
                "GET_CLUE_KNIFE,3 > +50 = 140");
    }

    @Test
    public void shouldIncreaseForNextClue_stepByStep_glove() {
        // given
        settings.integer(CLUE_SCORE_GLOVE, 2)
                .integer(CLUE_SCORE_GLOVE_INCREMENT, 1);

        // when then
        assertEvents("0:\n" +
                "GET_CLUE_GLOVE,0 > +2 = 2\n" +
                "GET_CLUE_GLOVE,1 > +3 = 5\n" +
                "GET_CLUE_GLOVE,2 > +4 = 9\n" +
                "GET_CLUE_GLOVE,3 > +5 = 14");
    }

    @Test
    public void shouldIncreaseForNextClue_stepByStep_ring() {
        // given
        settings.integer(CLUE_SCORE_RING, 200)
                .integer(CLUE_SCORE_RING_INCREMENT, 100);

        // when then
        assertEvents("0:\n" +
                "GET_CLUE_RING,0 > +200 = 200\n" +
                "GET_CLUE_RING,1 > +300 = 500\n" +
                "GET_CLUE_RING,2 > +400 = 900\n" +
                "GET_CLUE_RING,3 > +500 = 1400");
    }

    @Test
    public void shouldNotBeLessThanZero() {
        // given
        settings.integer(HERO_DIED_PENALTY, -30);

        // when then
        assertEvents("0:\n" +
                "HERO_DIED > +0 = 0");
    }

    @Test
    public void shouldClearScore() {
        // given
        shouldCollectScores();

        // when
        scores.clear();

        // then
        assertEquals(0, scores.getScore().intValue());
    }
    @Test
    public void shouldCleanIncreased_whenHeroDied() {
        // given
        settings.integer(HERO_DIED_PENALTY, -30);

        // when then
        assertEvents("0:\n" +
                "GET_CLUE_KNIFE,0 > +20 = 20\n" +
                "GET_CLUE_KNIFE,1 > +30 = 50\n" +
                "GET_CLUE_KNIFE,2 > +40 = 90\n" +
                "HERO_DIED > -30 = 60\n" +
                "GET_CLUE_KNIFE,0 > +20 = 80\n" +
                "GET_CLUE_KNIFE,1 > +30 = 110");
    }

    @Test
    public void shouldCleanIncreased_whenClean() {
        assertEvents("0:\n" +
                "GET_CLUE_KNIFE,0 > +20 = 20\n" +
                "GET_CLUE_KNIFE,1 > +30 = 50\n" +
                "GET_CLUE_KNIFE,2 > +40 = 90\n" +
                "(CLEAN) > -90 = 0\n" +
                "GET_CLUE_KNIFE,0 > +20 = 20\n" +
                "GET_CLUE_KNIFE,1 > +30 = 50");
    }

    @Test
    public void shouldCleanIncreased_whenSuicide() {
        // given
        settings.integer(SUICIDE_PENALTY, -13);

        // when then
        assertEvents("0:\n" +
                "GET_CLUE_KNIFE,0 > +20 = 20\n" +
                "GET_CLUE_KNIFE,1 > +30 = 50\n" +
                "GET_CLUE_KNIFE,2 > +40 = 90\n" +
                "SUICIDE > -13 = 77\n" +
                "GET_CLUE_KNIFE,0 > +20 = 97\n" +
                "GET_CLUE_KNIFE,1 > +30 = 127");
    }

    @Test
    public void shouldCollectScores_seriesMaxScoresMode() {
        // given
        settings.initScore(SERIES_MAX_VALUE);

        // then
        assertEvents("140:");
        assertEquals((Integer)0, scores.getSeries());

        // when then
        assertEvents(
                "GET_CLUE_KNIFE,0 > +0 = 140\n" +
                "GET_CLUE_RING,0 > +80 = 220\n" +
                "GET_CLUE_RING,1 > +300 = 520\n" +
                "GET_CLUE_RING,2 > +400 = 920\n" +
                "GET_CLUE_GLOVE,0 > +2 = 922\n" +
                "GET_CLUE_GLOVE,1 > +3 = 925\n" +
                "GET_CLUE_KNIFE,1 > +30 = 955\n" +
                "GET_CLUE_KNIFE,2 > +40 = 995\n" +
                "GET_CLUE_KNIFE,3 > +50 = 1045\n" +
                "GET_CLUE_KNIFE,4 > +60 = 1105\n" +
                "HERO_DIED > +0 = 1105");

        assertEquals((Integer)0, scores.getSeries());

        // when then
        assertEvents("GET_CLUE_KNIFE,0 > +0 = 1105\n" +
                "GET_CLUE_RING,0 > +0 = 1105\n" +
                "GET_CLUE_RING,1 > +0 = 1105\n" +
                "GET_CLUE_RING,2 > +0 = 1105\n" +
                "GET_CLUE_GLOVE,0 > +0 = 1105\n" +
                "GET_CLUE_GLOVE,1 > +0 = 1105\n" +
                "GET_CLUE_KNIFE,1 > +0 = 1105\n" +
                "GET_CLUE_KNIFE,2 > +0 = 1105\n" +
                "GET_CLUE_KNIFE,3 > +0 = 1105\n" +
                "GET_CLUE_KNIFE,4 > +0 = 1105");

        assertEquals((Integer)1105, scores.getSeries());

        // when then
        assertEvents("GET_CLUE_KNIFE,5 > +70 = 1175\n" +
                "GET_CLUE_KNIFE,6 > +80 = 1255");

        assertEquals((Integer)1255, scores.getSeries());

        // when
        scores.clear();

        // then
        assertEquals((Integer)0, scores.getScore());
        assertEquals((Integer)0, scores.getSeries());
    }
}