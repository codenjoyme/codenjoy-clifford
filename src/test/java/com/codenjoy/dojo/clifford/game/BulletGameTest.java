package com.codenjoy.dojo.clifford.game;

import com.codenjoy.dojo.clifford.model.items.Potion;
import org.junit.Test;

import static com.codenjoy.dojo.services.Direction.LEFT;
import static com.codenjoy.dojo.services.Direction.RIGHT;
import static com.codenjoy.dojo.services.PointImpl.pt;
import static org.junit.Assert.assertEquals;

public class BulletGameTest extends AbstractGameTest {

    private void assertBulletAt(int x, int y) {
        assertEquals(true, field.bullets().contains(pt(x, y)));
    }

    private void assertBulletCount(int count) {
        assertEquals(count, field.bullets().size());
    }

    @Test
    public void heroCanShoot_rightDirection() {
        givenFl("☼☼☼☼☼" +
                "☼   ☼" +
                "☼►  ☼" +
                "☼###☼" +
                "☼☼☼☼☼");

        assertBulletCount(0);

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "☼►  ☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertBulletAt(1, 2);

        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "☼► •☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertBulletCount(1);
    }

    @Test
    public void heroCanShoot_leftDirection() {
        givenFl("☼☼☼☼☼" +
                "☼   ☼" +
                "☼  ◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");

        assertBulletCount(0);

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "☼  ◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertBulletAt(3, 2);

        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "☼• ◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertBulletCount(1);
    }

    @Test
    public void bulletIsRemovedOutOfBoard() {
        givenFl("☼☼☼☼☼" +
                "☼   ☼" +
                "   ◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");

        assertBulletCount(0);

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "   ◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertBulletAt(3, 2);

        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                " • ◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertBulletCount(1);

        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "   ◄☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertBulletCount(0);
    }

    @Test
    public void bulletInteractWithBrick() {
        givenFl("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼ #  ◄☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼ #  ◄☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");
        assertBulletAt(5, 2);

        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼ #• ◄☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");

        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼ *  ◄☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");
        assertBulletCount(0);

        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼    ◄☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");

        tick();
        tick();
        tick();
        tick();
        tick();
        tick();
        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼ 4  ◄☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");
    }

    @Test
    public void bulletInteractWithBorder() {
        givenFl("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼   ◄ ☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼   ◄ ☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");
        assertBulletAt(4, 2);

        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼ • ◄ ☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");
        assertEquals(true, field.bullets().getAt(pt(2, 2)).stream()
                .allMatch(bullet -> bullet.getDirection() == LEFT));

        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼   ◄ ☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");
        assertBulletAt(0, 2);

        tick();

        assertE("☼☼☼☼☼☼☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼     ☼" +
                "☼ • ◄ ☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼");
        assertEquals(true, field.bullets().getAt(pt(2, 2)).stream()
                .allMatch(bullet -> bullet.getDirection() == RIGHT));
    }

    @Test
    public void heroNotMoveWhenShoot() {
        givenFl("☼☼☼☼☼" +
                "☼   ☼" +
                "☼►  ☼" +
                "☼###☼" +
                "☼☼☼☼☼");

        hero().right();
        hero().act(1);
        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "☼►  ☼" +
                "☼###☼" +
                "☼☼☼☼☼");
        assertBulletAt(1, 2);

        hero().right();
        tick();

        assertE("☼☼☼☼☼" +
                "☼   ☼" +
                "☼ ►•☼" +
                "☼###☼" +
                "☼☼☼☼☼");
    }

    @Test
    public void heroKillOtherHero() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  ►   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  (   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  (   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
        assertBulletAt(1, 3);

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼► •(   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼►  Z   ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertBulletCount(0);
        events.verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");
    }

    @Test
    public void heroWithMaskIsImmortal_bulletGoThrough() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼► ► ►  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
        hero(1).pick(Potion.PotionType.MASK_POTION);

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼► ⋉ (  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero(0).act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼► ⋉ (  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
        assertBulletAt(1, 3);

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼► ⋉ (  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
        assertBulletAt(3, 3);

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼► ⋉ Z  ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertBulletCount(0);
        events.verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => []\n" +
                "listener(2) => [HERO_DIE]\n");
    }

    @Test
    public void heroFallOnBullet() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►   ☼" +
                "☼       ☼" +
                "☼     ◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ⊏   ☼" +
                "☼       ☼" +
                "☼     ◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ⊏   ☼" +
                "☼     ◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
        assertBulletAt(6, 3);

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   Z ◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertBulletCount(0);
        events.verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");
    }

    @Test
    public void heroFallOnBullet_notTakeClue() {
        givenFl("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ►   ☼" +
                "☼       ☼" +
                "☼   $ ◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ⊏   ☼" +
                "☼       ☼" +
                "☼   $ ◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        hero().act(1);
        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   ⊏   ☼" +
                "☼   $ ◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");
        assertBulletAt(6, 3);

        tick();

        assertE("☼☼☼☼☼☼☼☼☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼       ☼" +
                "☼   Z ◄ ☼" +
                "☼#######☼" +
                "☼       ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertBulletCount(0);
        assertEquals(1, field.clueKnife().size());
        events.verifyAllEvents(
                "listener(0) => [KILL_HERO]\n" +
                "listener(1) => [HERO_DIE]\n");
    }
}
