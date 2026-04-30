package inf112.mayhem.model.entities.characters;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

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
    void hitAppliesProjectileKnockback() throws Exception {
        AbstractProjectile projectile = mock(AbstractProjectile.class);
        when(projectile.getKnockBackMultiplier()).thenReturn(100f);
        when(projectile.getDirection()).thenReturn(1);

        enemy.hit(projectile);

        assertTrue(getFloat(enemy, "knockBack") > 0);
    }

    @Test
    void enemyDoesNotShootImmediatelyIfReactionTimeNotMet() {
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        enemy.equipWeapon(weapon);
        enemy.setAggression(1); // Long reaction time (1.5s)

        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(500, 40, 32, 32));

        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn(1280.0);
        when(world.height()).thenReturn(768.0);
        List<EntityModel> objects = new ArrayList<>();
        objects.add(player);
        when(world.iterator()).thenReturn(objects.iterator());

        enemy.update(world, 0.1f);

        // Should not shoot yet because playerDetected is false (needs reactionTime)
        verify(weapon, never()).shoot();
    }

    @Test
    void enemyShootsWhenPlayerDetectedAndInRange() throws Exception {
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        when(weapon.getFiringDelay()).thenReturn(0.3f);
        when(weapon.shoot()).thenReturn(true);
        when(weapon.getWeaponType()).thenReturn(WeaponType.PISTOL);
        enemy.equipWeapon(weapon);
        
        setBoolean(enemy, "playerDetected", true);
        setFloat(enemy, "spawnDelayTimer", 0f);

        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x() + 20, enemy.y(), 32, 32));

        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn(1280.0);
        when(world.height()).thenReturn(768.0);
        
        List<EntityModel> objects = new ArrayList<>();
        objects.add(player);
        when(world.iterator()).thenReturn(objects.iterator());

        enemy.update(world, 0.1f);

        verify(weapon).shoot();
        verify(world).add(any(AbstractProjectile.class)); // ← verifiser prosjektil lagt til
    }

    @Test
    void isAtEdgeReturnsTrueWhenNoGroundBelow() throws Exception {
        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        when(ground.getCell(anyInt(), anyInt())).thenReturn(null);
        
        // Mock grounded state
        setBoolean(enemy, "isGrounded", true);
        
        // Use reflection to call private isAtEdge
        java.lang.reflect.Method method = EnemyClass.class.getDeclaredMethod("isAtEdge");
        method.setAccessible(true);
        boolean atEdge = (boolean) method.invoke(enemy);
        
        assertTrue(atEdge);
    }

    @Test
    void spawnDelayRestrictsMovementInitially() throws Exception {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn(1280.0);
        when(world.height()).thenReturn(768.0);
        when(world.iterator()).thenReturn(new ArrayList<EntityModel>().iterator());

        // Initially spawnDelayTimer is 1.0f
        enemy.update(world, 0.5f);

        assertEquals(0, enemy.velocity.x, "Velocity X should be 0 during spawn delay");
        assertTrue(getFloat(enemy, "spawnDelayTimer") > 0);
    }

    @Test
    void enemyCanMoveAfterSpawnDelay() throws Exception {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn(1280.0);
        when(world.height()).thenReturn(768.0);
        when(world.iterator()).thenReturn(new ArrayList<EntityModel>().iterator());

        // Update past 1.0s
        enemy.update(world, 1.1f);
        // After spawn delay, the first update might just clear the timer and skip logic if using multi-edit's logic
        // Let's call update one more time to be sure logic runs
        enemy.update(world, 0.1f);
        
        // Mock grounded so patrol doesn't think it's at edge (isAtEdge returns false if not grounded)
        setBoolean(enemy, "isGrounded", true);
        enemy.update(world, 0.1f);

        // After spawn delay, patrol should set velocity
        assertTrue(enemy.velocity.x != 0, "Velocity X should be non-zero after spawn delay (patrolling)");
        assertTrue(getFloat(enemy, "spawnDelayTimer") <= 0);
    }

    @Test
    void enemyUsesDoubleJumpInChase() throws Exception {
        enemy.setAggression(5);
        setBoolean(enemy, "isGrounded", false);
        setInt(enemy, "jump_counter", 1);
        enemy.velocity.y = 10f;
        setFloat(enemy, "spawnDelayTimer", 0f);

        PlayerClass player = mock(PlayerClass.class);
        when(player.isActive()).thenReturn(true);
        when(player.bounds()).thenReturn(new Rectangle(enemy.x(), enemy.y() + 100, 32, 32));

        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn(1280.0);
        when(world.height()).thenReturn(768.0);
        List<EntityModel> objects = new ArrayList<>();
        objects.add(player);
        when(world.iterator()).thenReturn(objects.iterator());

        setBoolean(enemy, "playerDetected", true);

        enemy.update(world, 0.1f);

        assertEquals(2, getInt(enemy, "jump_counter"));
        assertTrue(enemy.velocity.y > 10f);
    }

    private static void setFloat(Object obj, String field, float value) throws Exception {
        Field f;
        try {
            f = obj.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            f = AbstractCharacter.class.getDeclaredField(field);
        }
        f.setAccessible(true);
        f.setFloat(obj, value);
    }

    private static int getInt(Object obj, String field) throws Exception {
        Field f;
        try {
            f = obj.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            f = AbstractCharacter.class.getDeclaredField(field);
        }
        f.setAccessible(true);
        return f.getInt(obj);
    }

    private static void setBoolean(Object obj, String field, boolean value) throws Exception {
        Field f;
        try {
            f = obj.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            try {
                f = AbstractCharacter.class.getDeclaredField(field);
            } catch (NoSuchFieldException e2) {
                f = AbstractCharacter.class.getSuperclass().getDeclaredField(field);
            }
        }
        f.setAccessible(true);
        f.setBoolean(obj, value);
    }

    private static boolean getBoolean(Object obj, String field) throws Exception {
        Field f;
        try {
            f = obj.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            f = AbstractCharacter.class.getDeclaredField(field);
        }
        f.setAccessible(true);
        return f.getBoolean(obj);
    }

    private static float getFloat(Object obj, String field) throws Exception {
        Field f;
        try {
            f = obj.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            f = AbstractCharacter.class.getDeclaredField(field);
        }
        f.setAccessible(true);
        return f.getFloat(obj);
    }

    private static void setInt(Object obj, String field, int value) throws Exception {
        Field f;
        try {
            f = obj.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            f = AbstractCharacter.class.getDeclaredField(field);
        }
        f.setAccessible(true);
        f.setInt(obj, value);
    }
}