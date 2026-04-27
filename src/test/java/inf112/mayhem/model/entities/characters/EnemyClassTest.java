package inf112.mayhem.model.entities.characters;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.projectile.AbstractProjectile;

class EnemyClassTest {

    private EnemyClass enemy;
    private TiledMap map;

    @BeforeEach
    void setUp() {
        map = new TiledMap();
        enemy = new EnemyClass(30, 40, map, 3);
    }

    @Test
    void constructorSetsCorrectValues() {
        assertEquals(30, enemy.x());
        assertEquals(40, enemy.y());
        assertEquals(32, enemy.width());
        assertEquals(32, enemy.height());
        assertEquals(EntityType.ENEMY, enemy.type());
        assertEquals(EntityState.IDLE, enemy.state());
        assertEquals(3, enemy.lives());
        assertEquals(3, enemy.getAggression());
        assertTrue(enemy.isActive());
    }

    @Test
    void aggressionIsClampedToMinimumOne() {
        enemy.setAggression(-100);

        assertEquals(1, enemy.getAggression());
    }

    @Test
    void aggressionIsClampedToMaximumFive() {
        enemy.setAggression(100);

        assertEquals(5, enemy.getAggression());
    }

    @Test
    void jumpWhenGroundedSetsUpwardsVelocity() throws Exception {
        setBoolean(enemy, "isGrounded", true);

        enemy.jump();

        assertTrue(enemy.velocity.y > 0);
    }

    @Test
    void enemyWithAggressionThreeCanDoubleJump() throws Exception {
        setBoolean(enemy, "isGrounded", false);
        setInt(enemy, "jump_counter", 1);

        enemy.jump();

        assertTrue(enemy.velocity.y > 0);
    }

    @Test
    void enemyWithLowAggressionCannotDoubleJump() throws Exception {
        EnemyClass passiveEnemy = new EnemyClass(0, 0, map, 1);
        setBoolean(passiveEnemy, "isGrounded", false);
        setInt(passiveEnemy, "jump_counter", 1);

        passiveEnemy.jump();

        assertEquals(0, passiveEnemy.velocity.y);
    }

    @Test
    void dropWhenGroundedSetsIgnorePlatforms() throws Exception {
        setBoolean(enemy, "isGrounded", true);

        enemy.drop();

        assertTrue(enemy.velocity.y < 0);
        assertTrue(getBoolean(enemy, "ignorePlatforms"));
    }

    @Test
    void updateStateJumping() throws Exception {
        setBoolean(enemy, "isGrounded", false);
        enemy.velocity.y = 10;

        enemy.updateState();

        assertEquals(EntityState.JUMPING, enemy.state());
    }

    @Test
    void updateStateFalling() throws Exception {
        setBoolean(enemy, "isGrounded", false);
        enemy.velocity.y = -10;

        enemy.updateState();

        assertEquals(EntityState.FALLING, enemy.state());
    }

    @Test
    void updateStateWalking() throws Exception {
        setBoolean(enemy, "isGrounded", true);
        enemy.velocity.x = 10;

        enemy.updateState();

        assertEquals(EntityState.WALKING, enemy.state());
    }

    @Test
    void updateStateIdle() throws Exception {
        setBoolean(enemy, "isGrounded", true);
        enemy.velocity.x = 0;
        enemy.velocity.y = 0;

        enemy.updateState();

        assertEquals(EntityState.IDLE, enemy.state());
    }

    @Test
    void hitAppliesProjectileKnockback() {
        AbstractProjectile projectile = mock(AbstractProjectile.class);
        when(projectile.getKnockBackMultiplier()).thenReturn(100f);
        when(projectile.getDirection()).thenReturn(1);

        enemy.hit(projectile);

        assertTrue(enemy.velocity.x > 0);
    }

    private static void setBoolean(Object obj, String field, boolean value) throws Exception {
        Field f = AbstractCharacter.class.getDeclaredField(field);
        f.setAccessible(true);
        f.setBoolean(obj, value);
    }

    private static boolean getBoolean(Object obj, String field) throws Exception {
        Field f = AbstractCharacter.class.getDeclaredField(field);
        f.setAccessible(true);
        return f.getBoolean(obj);
    }

    private static void setInt(Object obj, String field, int value) throws Exception {
        Field f = AbstractCharacter.class.getDeclaredField(field);
        f.setAccessible(true);
        f.setInt(obj, value);
    }
}