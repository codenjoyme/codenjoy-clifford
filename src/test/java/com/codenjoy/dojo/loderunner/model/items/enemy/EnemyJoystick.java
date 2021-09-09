package com.codenjoy.dojo.loderunner.model.items.enemy;

import com.codenjoy.dojo.loderunner.model.Field;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Joystick;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.joystick.DirectionActJoystick;
import org.mockito.Mockito;

import static com.codenjoy.dojo.services.Direction.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnemyJoystick implements Joystick, DirectionActJoystick {

    private final Enemy enemy;

    public EnemyJoystick(Enemy enemy) {
        this.enemy = enemy;
        enemy.setAi(mock(EnemyAI.class));
    }

    private void overwriteDirection(Direction direction) {
        Mockito.reset(enemy.getAi());
        when(enemy.getAi().getDirection(any(Field.class), any(Point.class), anyList()))
                .thenReturn(direction, null);
    }

    @Override
    public void down() {
        overwriteDirection(DOWN);
    }

    @Override
    public void up() {
        overwriteDirection(UP);
    }

    @Override
    public void left() {
        overwriteDirection(LEFT);
    }

    @Override
    public void right() {
        overwriteDirection(RIGHT);
    }

    @Override
    public void act(int... p) {
        overwriteDirection(ACT);
    }
}