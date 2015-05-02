package com.codenjoy.dojo.loderunner.client;

import com.codenjoy.dojo.client.Direction;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SolverTest {

    private Dice dice;
    private Solver ai;

    @Before
    public void setup() {
        dice = mock(Dice.class);
        ai = new YourSolver(dice);
    }

    private Board board(String board) {
        return (Board) new Board().forString(board);
    }

    @Test
    public void should() {
        asertAI("☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼►   H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼", Direction.RIGHT);

        asertAI("☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼ ►  H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼", Direction.RIGHT);

        asertAI("☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼  ► H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼", Direction.RIGHT);

        asertAI("☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼   ►H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼", Direction.RIGHT);
    }


    private void asertAI(String board, Direction expected) {
        String actual = ai.get(board(board));
        assertEquals(expected.toString(), actual);
    }

    private void dice(Direction direction) {
        when(dice.next(anyInt())).thenReturn(direction.value());
    }
}
