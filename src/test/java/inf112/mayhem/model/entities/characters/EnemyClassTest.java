package inf112.mayhem.model.entities.characters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.projectile.AbstractProjectile;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;
import inf112.mayhem.model.entities.weapons.WeaponType;

class EnemyClassTest {

    private EnemyClass enemy;
    private TiledMap map;

    @BeforeEach
    void setUp() {
        map = mock(TiledMap.class);

        MapLayers layers = new MapLayers();
        TiledMapTileLayer groundLayer = mock(TiledMapTileLayer.class);
        TiledMapTileLayer platformLayer = mock(TiledMapTileLayer.class);

        when(groundLayer.getName()).thenReturn("ground");
        when(platformLayer.getName()).thenReturn("platform1");

        layers.add(groundLayer);
        layers.add(platformLayer);

        when(map.getLayers()).thenReturn(layers);

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
    void jumpWhenGroundedSetsUpwardsVelocityAndJumpCounter() throws Exception {
        setBoolean(enemy, "isGrounded", true);

        enemy.jump();

        assertTrue(enemy.velocity.y > 0);
        assertEquals(1, getInt(enemy, "jump_counter"));
        assertTrue(enemy.hasJumped());
    }

    @Test
    void enemyCanJumpUntilMaxJumps() throws Exception {
        setBoolean(enemy, "isGrounded", false);
        setInt(enemy, "jump_counter", 1);

        enemy.jump();

        assertTrue(enemy.velocity.y > 0);
        assertEquals(2, getInt(enemy, "jump_counter"));
    }

    @Test
    void enemyCannotJumpPastMaxJumps() throws Exception {
        setBoolean(enemy, "isGrounded", false);
        setInt(enemy, "jump_counter", 4);
        enemy.velocity.y = 0;

        enemy.jump();

        assertEquals(4, getInt(enemy, "jump_counter"));
        assertEquals(0, enemy.velocity.y);
    }

    @Test
    void jumpSoundMadeResetsHasJumped() throws Exception {
        setBoolean(enemy, "isGrounded", true);

        enemy.jump();
        assertTrue(enemy.hasJumped());

        enemy.jumpSoundMade();
        assertFalse(enemy.hasJumped());
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
    void hitAppliesKnockback() throws Exception {
        AbstractProjectile projectile = mock(AbstractProjectile.class);
        when(projectile.getKnockBack()).thenReturn(100f);
        when(projectile.getDirection()).thenReturn(1);

        enemy.hit(projectile);

        assertTrue(getFloat(enemy, "knockbackVelocity") != 0f);
    }

    @Test
    void spawnDelayRestrictsMovementInitially() throws Exception {
        AppWorld world = emptyWorld();

        enemy.update(world, 0.5f);

        assertEquals(0, enemy.velocity.x);
        assertTrue(getFloat(enemy, "spawnDelayTimer") > 0);
    }

    @Test
    void enemyCanPatrolAfterSpawnDelay() throws Exception {
        AppWorld world = emptyWorld();

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "isGrounded", false);

        enemy.update(world, 0.1f);

        assertTrue(enemy.velocity.x != 0);
    }
    
    @Test
    void enemyStopsJumpingAtMaxJumps() throws Exception {
        setBoolean(enemy, "isGrounded", false);
        setBoolean(enemy, "isCrossingGap", true);
        setFloat(enemy, "airJumpCheckTimer", 0f);
        setFloat(enemy, "jumpDecisionCooldown", 0f);
        setInt(enemy, "jump_counter", 4);

        enemy.update(emptyWorld(), 0.1f);

        assertEquals(4, getInt(enemy, "jump_counter"));
    }
    
    @Test
    void enemyDoesNotSpamJumpDueToCooldown() throws Exception {
        setBoolean(enemy, "isGrounded", false);
        setBoolean(enemy, "isCrossingGap", true);
        setFloat(enemy, "airJumpCheckTimer", 0f);
        setFloat(enemy, "jumpDecisionCooldown", 1f);
        setInt(enemy, "jump_counter", 1);

        enemy.update(emptyWorld(), 0.1f);

        assertEquals(1, getInt(enemy, "jump_counter"));
    }

    @Test
    void enemyChasesPlayerAfterSpawnDelay() throws Exception {
        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(300, 40, 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "isGrounded", false);

        enemy.update(world, 0.1f);

        assertTrue(enemy.velocity.x > 0);
    }

    @Test
    void enemyFacesLeftWhenPlayerIsLeft() throws Exception {
        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(-100, 40, 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "isGrounded", false);

        enemy.update(world, 0.1f);

        assertEquals(-1, enemy.getDirection());
        assertTrue(enemy.velocity.x < 0);
    }

    @Test
    void enemyShootsWhenPlayerIsInFrontInRangeAndSameHeight() throws Exception {
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        when(weapon.getFiringDelay()).thenReturn(0.3f);
        when(weapon.shoot()).thenReturn(true);
        when(weapon.getWeaponType()).thenReturn(WeaponType.PISTOL);

        enemy.equipWeapon(weapon);

        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x() + 20, enemy.y(), 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "isGrounded", false);

        enemy.update(world, 0.1f);

        verify(weapon).shoot();
        verify(world).add(any(AbstractProjectile.class));
    }

    @Test
    void enemyDoesNotShootWhenPlayerIsTooHigh() throws Exception {
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        enemy.equipWeapon(weapon);

        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x() + 20, enemy.y() + 200, 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "isGrounded", false);

        enemy.update(world, 0.1f);

        verify(weapon, never()).shoot();
    }

    @Test
    void enemyDoesNotShootWhenPlayerBehindEnemy() throws Exception {
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        enemy.equipWeapon(weapon);

        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x() - 20, enemy.y(), 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setInt(enemy, "direction", 1);
        setBoolean(enemy, "isGrounded", false);

        enemy.update(world, 0.1f);

        when(player.bounds()).thenReturn(new Rectangle(enemy.x() + 20, enemy.y() + 200, 32, 32));
    }

    @Test
    void isAtEdgeReturnsTrueWhenNoGroundInFront() throws Exception {
        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        when(ground.getCell(anyInt(), anyInt())).thenReturn(null);

        setBoolean(enemy, "isGrounded", true);
        setInt(enemy, "direction", 1);

        Method method = EnemyClass.class.getDeclaredMethod("isAtEdge");
        method.setAccessible(true);

        boolean atEdge = (boolean) method.invoke(enemy);

        assertTrue(atEdge);
    }

    @Test
    void edgeJumpStartsGapCrossing() throws Exception {
        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x() + 300, enemy.y(), 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "isGrounded", true);
        setBoolean(enemy, "hasLandedAfterSpawn", true);

        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        when(ground.getCell(anyInt(), anyInt())).thenReturn(null);

        enemy.update(world, 0.1f);

        assertTrue(getBoolean(enemy, "hasEdgeJumped"));
        assertTrue(getBoolean(enemy, "isCrossingGap"));
        assertEquals(1, getInt(enemy, "jump_counter"));
        assertTrue(enemy.velocity.y > 0);
    }

    @Test
    void enemyEmergencyJumpsWhenFallingOverEmptyYAxis() throws Exception {
        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x() + 300, enemy.y(), 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "hasLandedAfterSpawn", true);
        setBoolean(enemy, "isGrounded", false);
        setBoolean(enemy, "isCrossingGap", true);
        setFloat(enemy, "airJumpCheckTimer", 0f);
        setFloat(enemy, "jumpDecisionCooldown", 0f);
        setInt(enemy, "jump_counter", 1);
        enemy.velocity.y = -20f;

        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        when(ground.getCell(anyInt(), anyInt())).thenReturn(null);

        enemy.update(world, 0.1f);

        assertEquals(2, getInt(enemy, "jump_counter"));
        assertTrue(enemy.velocity.y > 0);
    }

    @Test
    void enemyDoesNotEmergencyJumpWhenCooldownActive() throws Exception {
        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x() + 300, enemy.y(), 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "hasLandedAfterSpawn", true);
        setBoolean(enemy, "isGrounded", false);
        setBoolean(enemy, "isCrossingGap", true);
        setFloat(enemy, "airJumpCheckTimer", 0f);
        setFloat(enemy, "jumpDecisionCooldown", 1f);
        setInt(enemy, "jump_counter", 1);
        enemy.velocity.y = -20f;

        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        when(ground.getCell(anyInt(), anyInt())).thenReturn(null);

        enemy.update(world, 0.1f);

        assertEquals(1, getInt(enemy, "jump_counter"));
    }

    @Test
    void enemyDoesNotEmergencyJumpPastMaxJumps() throws Exception {
        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x() + 300, enemy.y(), 32, 32));

        AppWorld world = worldWith(player);

        setFloat(enemy, "spawnDelayTimer", 0f);
        setBoolean(enemy, "hasLandedAfterSpawn", true);
        setBoolean(enemy, "isGrounded", false);
        setBoolean(enemy, "isCrossingGap", true);
        setFloat(enemy, "airJumpCheckTimer", 0f);
        setFloat(enemy, "jumpDecisionCooldown", 0f);
        setInt(enemy, "jump_counter", 4);
        enemy.velocity.y = -20f;

        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        when(ground.getCell(anyInt(), anyInt())).thenReturn(null);

        enemy.update(world, 0.1f);

        assertEquals(4, getInt(enemy, "jump_counter"));
    }

    @Test
    void respawnResetsGapJumpState() throws Exception {
        setBoolean(enemy, "hasEdgeJumped", true);
        setBoolean(enemy, "isCrossingGap", true);
        setFloat(enemy, "airJumpCheckTimer", 1f);
        setFloat(enemy, "jumpDecisionCooldown", 1f);
        setInt(enemy, "jump_counter", 2);

        enemy.respawn();

        assertFalse(getBoolean(enemy, "hasEdgeJumped"));
        assertFalse(getBoolean(enemy, "isCrossingGap"));
        assertEquals(0, getInt(enemy, "jump_counter"));
        assertEquals(0f, getFloat(enemy, "airJumpCheckTimer"));
        assertEquals(0f, getFloat(enemy, "jumpDecisionCooldown"));
    }

    @Test
    void respawnResetsEnemyAiState() throws Exception {
        setBoolean(enemy, "hasEdgeJumped", true);
        setBoolean(enemy, "hasLandedAfterSpawn", true);
        setBoolean(enemy, "isCrossingGap", true);
        setFloat(enemy, "airJumpCheckTimer", 1f);
        setFloat(enemy, "jumpDecisionCooldown", 1f);
        setInt(enemy, "jump_counter", 3);

        enemy.respawn();

        assertFalse(getBoolean(enemy, "hasEdgeJumped"));
        assertFalse(getBoolean(enemy, "hasLandedAfterSpawn"));
        assertFalse(getBoolean(enemy, "isCrossingGap"));
        assertEquals(0f, getFloat(enemy, "airJumpCheckTimer"));
        assertEquals(0f, getFloat(enemy, "jumpDecisionCooldown"));
        assertEquals(0, getInt(enemy, "jump_counter"));
        assertEquals(1, enemy.getDirection());
    }

    private AppWorld emptyWorld() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn(1280.0);
        when(world.height()).thenReturn(768.0);
        when(world.iterator()).thenReturn(new ArrayList<EntityModel>().iterator());
        return world;
    }

    private AppWorld worldWith(EntityModel entity) {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn(1280.0);
        when(world.height()).thenReturn(768.0);

        List<EntityModel> objects = new ArrayList<>();
        objects.add(entity);

        when(world.iterator()).thenReturn(objects.iterator());

        return world;
    }

    private static void setFloat(Object obj, String field, float value) throws Exception {
        Field f = getField(obj, field);
        f.setAccessible(true);
        f.setFloat(obj, value);
    }

    private static float getFloat(Object obj, String field) throws Exception {
        Field f = getField(obj, field);
        f.setAccessible(true);
        return f.getFloat(obj);
    }

    private static void setInt(Object obj, String field, int value) throws Exception {
        Field f = getField(obj, field);
        f.setAccessible(true);
        f.setInt(obj, value);
    }

    private static int getInt(Object obj, String field) throws Exception {
        Field f = getField(obj, field);
        f.setAccessible(true);
        return f.getInt(obj);
    }

    private static void setBoolean(Object obj, String field, boolean value) throws Exception {
        Field f = getField(obj, field);
        f.setAccessible(true);
        f.setBoolean(obj, value);
    }

    private static boolean getBoolean(Object obj, String field) throws Exception {
        Field f = getField(obj, field);
        f.setAccessible(true);
        return f.getBoolean(obj);
    }

    private static Field getField(Object obj, String field) throws Exception {
        Class<?> current = obj.getClass();

        while (current != null) {
            try {
                return current.getDeclaredField(field);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }

        throw new NoSuchFieldException(field);
    }
}