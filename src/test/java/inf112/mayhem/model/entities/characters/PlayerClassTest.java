package inf112.mayhem.model.entities.characters;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.projectile.AbstractProjectile;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;

class PlayerClassTest {

    private PlayerClass player;
    private TiledMap map;

    @BeforeEach
    void setUp() {
    map = new TiledMap();
    player = new PlayerClass(10, 20, map);
    }

    @Test
    void constructorSetsCorrectValues() {
        assertEquals(10, player.x());
        assertEquals(20, player.y());
        assertEquals(32, player.width());
        assertEquals(32, player.height());
        assertEquals(EntityType.PLAYER, player.type());
        assertEquals(EntityState.IDLE, player.state());
        assertEquals(3, player.lives());
        assertTrue(player.isActive());
    }

    @Test
    void updateBoundsMatchesPosition() {
        player.velocity.x = 50;
        player.velocity.y = 25;

        player.respawn();
        player.updateBounds();

        assertEquals(player.x(), player.bounds().x);
        assertEquals(player.y(), player.bounds().y);
        assertEquals(player.width(), player.bounds().width);
        assertEquals(player.height(), player.bounds().height);
    }

    @Test
    void dieMakesPlayerInactive() {
        player.die();

        assertFalse(player.isActive());
    }

    @Test
    void setLivesChangesLives() {
        player.setLives(7);

        assertEquals(7, player.lives());
    }

    @Test
    void directionCanBeChanged() {
        player.setDirection(-1);

        assertEquals(-1, player.getDirection());
    }

    @Test
    void equipWeaponStoresWeapon() {
        AbstractWeapon weapon = mock(AbstractWeapon.class);

        player.equipWeapon(weapon);

        assertSame(weapon, player.getEquippedWeapon());
    }

    @Test
    void jumpWhenGroundedSetsUpwardsVelocity() throws Exception {
        setBoolean(player, "isGrounded", true);

        player.jump();

        assertTrue(player.velocity.y > 0);
        assertEquals(EntityState.IDLE, player.state());

        player.updateState();

        assertEquals(EntityState.JUMPING, player.state());
    }

    @Test
    void doubleJumpWorksWhenNotGrounded() throws Exception {
        setBoolean(player, "isGrounded", false);
        setInt(player, "jump_counter", 1);

        player.jump();

        assertTrue(player.velocity.y > 0);
        assertEquals(2, getInt(player, "jump_counter"));
    }

    @Test
    void jumpDoesNothingWhenAlreadyUsedDoubleJump() throws Exception {
        setBoolean(player, "isGrounded", false);
        setInt(player, "jump_counter", 2);
        player.velocity.y = 0;

        player.jump();

        assertEquals(0, player.velocity.y);
        assertEquals(2, getInt(player, "jump_counter"));
    }

    @Test
    void dropWhenGroundedSetsFallingStateData() throws Exception {
        setBoolean(player, "isGrounded", true);

        player.drop();

        assertTrue(player.velocity.y < 0);
        assertFalse(getBoolean(player, "isGrounded"));
        assertTrue(getBoolean(player, "ignorePlatforms"));
    }

    @Test
    void dropWhenNotGroundedDoesNothing() throws Exception {
        setBoolean(player, "isGrounded", false);
        player.velocity.y = 0;

        player.drop();

        assertEquals(0, player.velocity.y);
        assertFalse(getBoolean(player, "ignorePlatforms"));
    }

    @Test
    void updateStateJumpingWhenVelocityYPositiveAndNotGrounded() throws Exception {
        setBoolean(player, "isGrounded", false);
        player.velocity.y = 100;

        player.updateState();

        assertEquals(EntityState.JUMPING, player.state());
    }

    @Test
    void updateStateFallingWhenVelocityYNegativeAndNotGrounded() throws Exception {
        setBoolean(player, "isGrounded", false);
        player.velocity.y = -100;

        player.updateState();

        assertEquals(EntityState.FALLING, player.state());
    }

    @Test
    void updateStateWalkingWhenGroundedAndMovingHorizontally() throws Exception {
        setBoolean(player, "isGrounded", true);
        player.velocity.x = 10;

        player.updateState();

        assertEquals(EntityState.WALKING, player.state());
    }

    @Test
    void updateStateIdleWhenGroundedAndNotMoving() throws Exception {
        setBoolean(player, "isGrounded", true);
        player.velocity.x = 0;
        player.velocity.y = 0;

        player.updateState();

        assertEquals(EntityState.IDLE, player.state());
    }

    @Test
    void outOfBoundsReturnsTrueOutsideWorld() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 100);
        when(world.height()).thenReturn((double)100);

        PlayerClass outside = new PlayerClass(-50, 20, map);

        assertTrue(outside.outOfBounds(world));
    }

    @Test
    void outOfBoundsReturnsFalseInsideWorld() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 100);
        when(world.height()).thenReturn((double) 100);

        assertFalse(player.outOfBounds(world));
    }

    @Test
    void hitAppliesKnockbackFromProjectile() {
        AbstractProjectile projectile = mock(AbstractProjectile.class);
        when(projectile.getKnockBack()).thenReturn(100f);
        when(projectile.getDirection()).thenReturn(1);

        player.hit(projectile);

        verify(projectile).getKnockBack();
        verify(projectile).getDirection();
    }

    @Test
    void hitAffectsHorizontalMovementOnUpdate() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 1000);
        when(world.height()).thenReturn((double) 1000);

        AbstractProjectile projectile = mock(AbstractProjectile.class);
        when(projectile.getKnockBack()).thenReturn(100f);
        when(projectile.getDirection()).thenReturn(1);

        float xBefore = player.x();
        player.hit(projectile);
        player.update(world, 0.1f);

        assertTrue(player.x() > xBefore);
    }

    @Test
    void updateOutOfBoundsDecrementsLivesAndRespawnsWhenLivesRemain() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 100);
        when(world.height()).thenReturn((double) 100);

        PlayerClass out = new PlayerClass(-10, 20, map);
        out.velocity.y = 500;

        out.update(world, 0.1f);

        assertEquals(2, out.lives());
        assertEquals(0, out.velocity.x);
        assertEquals(0, out.velocity.y);
        assertTrue(out.isActive());
    }

    @Test
    void updateOutOfBoundsKillsPlayerWhenNoLivesRemain() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 100);
        when(world.height()).thenReturn((double) 100);

        PlayerClass out = new PlayerClass(-10, 20, map);
        out.setLives(1);
        out.velocity.y = 500;

        out.update(world, 0.1f);

        assertEquals(0, out.lives());
        assertFalse(out.isActive());
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

    private static int getInt(Object obj, String field) throws Exception {
        Field f = AbstractCharacter.class.getDeclaredField(field);
        f.setAccessible(true);
        return f.getInt(obj);
    }
}