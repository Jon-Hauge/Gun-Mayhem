package inf112.mayhem.model;

import inf112.mayhem.model.collision.EntityCollisionHandler;
import inf112.mayhem.model.entities.EntityFactory;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.characters.EnemyClass;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.view.maps.LevelLoader;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppWorldTest {

    @Test
    void levelShouldNotUnlockWhenGameIsActive() {
        AppWorld world = new AppWorld(1280, 768);
        int currentLevel = 1;
        int unlockedLevels = 1;

        int newUnlockedLevels = world.updateLevelCount(currentLevel, unlockedLevels);

        assertEquals(1, newUnlockedLevels, "Level 2 should not be unlocked while game is still active");
    }

    @Test
    void levelShouldUnlockWhenGameIsWon() {
        AppWorld world = new AppWorld(1280, 768);
        int currentLevel = 1;
        int unlockedLevels = 1;

        world.setState(GameState.VICTORY);
        int newUnlockedLevels = world.updateLevelCount(currentLevel, unlockedLevels);
        
        assertEquals(2, newUnlockedLevels, "Level 2 should be unlocked when game is won");
    }

    @Test
    void levelShouldNotUnlockWhenGameIsLost() {
        AppWorld world = new AppWorld(1280, 768);
        int currentLevel = 1;
        int unlockedLevels = 1;

        world.setState(GameState.GAME_OVER);
        int newUnlockedLevels = world.updateLevelCount(currentLevel, unlockedLevels);

        assertEquals(1, newUnlockedLevels, "Level 2 should not be unlocked when game is lost");
    }

    @Test
    void testAddAndIterator() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        EntityCollisionHandler pCollision = mock(EntityCollisionHandler.class);
        setPrivateField(world, "pCollision", pCollision);
        
        EntityModel entity = mock(EntityModel.class);
        when(entity.isActive()).thenReturn(true);
        
        world.add(entity);
        
        assertFalse(world.iterator().hasNext());
        
        world.update(0.1f);
        
        Iterator<EntityModel> it = world.iterator();
        assertTrue(it.hasNext());
        assertEquals(entity, it.next());
        verify(pCollision).checkCollisions();
    }

    @Test
    void testRemoveInactive() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        EntityCollisionHandler pCollision = mock(EntityCollisionHandler.class);
        setPrivateField(world, "pCollision", pCollision);
        
        EntityModel activeEntity = mock(EntityModel.class);
        EntityModel inactiveEntity = mock(EntityModel.class);
        
        when(activeEntity.isActive()).thenReturn(true);
        when(inactiveEntity.isActive()).thenReturn(false);
        
        world.add(activeEntity);
        world.add(inactiveEntity);
        world.update(0);
        
        world.removeInactive();
        
        int count = 0;
        for (EntityModel e : world) {
            count++;
            assertEquals(activeEntity, e);
        }
        assertEquals(1, count);
    }

    @Test
    void testEvaluateStateVictory() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        PlayerClass player = mock(PlayerClass.class);
        EnemyClass enemy = mock(EnemyClass.class);
        
        setPrivateField(world, "playerClass", player);
        setPrivateField(world, "enemyClass", enemy);
        
        when(player.lives()).thenReturn(3);
        when(enemy.lives()).thenReturn(0);
        
        world.evaluateState();
        
        assertEquals(GameState.VICTORY, world.state());
    }

    @Test
    void testEvaluateStateGameOver() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        PlayerClass player = mock(PlayerClass.class);
        EnemyClass enemy = mock(EnemyClass.class);
        
        setPrivateField(world, "playerClass", player);
        setPrivateField(world, "enemyClass", enemy);
        
        when(player.lives()).thenReturn(0);
        when(enemy.lives()).thenReturn(3);
        
        world.evaluateState();
        
        assertEquals(GameState.GAME_OVER, world.state());
    }

    @Test
    void testEvaluateStateNoTransitionWhenBothAlive() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        PlayerClass player = mock(PlayerClass.class);
        EnemyClass enemy = mock(EnemyClass.class);

        setPrivateField(world, "playerClass", player);
        setPrivateField(world, "enemyClass", enemy);
        world.setState(GameState.ACTIVE);

        when(player.lives()).thenReturn(2);
        when(enemy.lives()).thenReturn(1);

        world.evaluateState();

        assertEquals(GameState.ACTIVE, world.state());
    }

    @Test
    void testReset() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        PlayerClass player = mock(PlayerClass.class);
        EnemyClass enemy = mock(EnemyClass.class);
        
        setPrivateField(world, "playerClass", player);
        setPrivateField(world, "enemyClass", enemy);
        world.setState(GameState.GAME_OVER);
        
        world.reset();
        
        assertEquals(GameState.ACTIVE, world.state());
        verify(player).setLives(3);
        verify(enemy).setLives(3);
    }

    @Test
    void testUpdateSpawning() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        EntityFactory factory = mock(EntityFactory.class);
        EntityCollisionHandler pCollision = mock(EntityCollisionHandler.class);
        setPrivateField(world, "fac", factory);
        setPrivateField(world, "pCollision", pCollision);

        world.update(5f);
        verify(factory, never()).spawnWeapon();
        verify(factory, never()).spawnCrate();

        world.update(5f);
        verify(factory).spawnWeapon();
        verify(factory, never()).spawnCrate();

        world.update(5f);
        verify(factory).spawnCrate();
    }

    @Test
    void testGetters() {
        AppWorld world = new AppWorld(1280, 768);
        assertEquals(1280, world.width());
        assertEquals(768, world.height());
        assertEquals(0, world.currentTime());
        assertEquals(GameState.MAIN_MENU, world.state());
        assertNull(world.getPlayerClass());
        assertNull(world.getMap());
        assertNull(world.getEntityFactory());
    }

    @Test
    void testGetEnemyClass() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        EnemyClass enemy = mock(EnemyClass.class);
        setPrivateField(world, "enemyClass", enemy);

        assertEquals(enemy, world.getEnemyClass());
    }

    @Test
    void testAddNull() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        EntityCollisionHandler pCollision = mock(EntityCollisionHandler.class);
        setPrivateField(world, "pCollision", pCollision);
        
        world.add(null);
        world.update(0.1f);
        assertFalse(world.iterator().hasNext());
    }

    @Test
    void testUpdateLevelCountMaxLevels() {
        AppWorld world = new AppWorld(1280, 768);
        world.setState(GameState.VICTORY);
        
        assertEquals(3, world.updateLevelCount(3, 3));
        
        assertEquals(2, world.updateLevelCount(1, 2));
    }

    @Test
    void testUpdateCallsEntityUpdate() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        EntityCollisionHandler pCollision = mock(EntityCollisionHandler.class);
        setPrivateField(world, "pCollision", pCollision);
        
        EntityModel entity = mock(EntityModel.class);
        when(entity.isActive()).thenReturn(true);
        
        world.add(entity);
        world.update(0.5f);
        
        verify(entity).update(world, 0.5f);
    }

    @Test
    void testRemoveObject() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        EntityCollisionHandler pCollision = mock(EntityCollisionHandler.class);
        setPrivateField(world, "pCollision", pCollision);

        EntityModel entity = mock(EntityModel.class);
        when(entity.isActive()).thenReturn(true);
        world.add(entity);
        world.update(0.1f);

        world.remove(entity);

        assertFalse(world.iterator().hasNext());
    }

    @Test
    void testRemoveNonExistingObject() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        EntityCollisionHandler pCollision = mock(EntityCollisionHandler.class);
        setPrivateField(world, "pCollision", pCollision);

        EntityModel existing = mock(EntityModel.class);
        EntityModel nonExisting = mock(EntityModel.class);
        when(existing.isActive()).thenReturn(true);
        world.add(existing);
        world.update(0.1f);

        world.remove(nonExisting);

        Iterator<EntityModel> iterator = world.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(existing, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testLoadLevel() throws Exception {
        AppWorld world = new AppWorld(1280, 768);
        LevelLoader loader = mock(LevelLoader.class);
        TiledMap map = mock(TiledMap.class);
        MapLayers layers = mock(MapLayers.class);
        TiledMapTileLayer groundLayer = mock(TiledMapTileLayer.class);
        
        when(map.getLayers()).thenReturn(layers);
        when(layers.get("ground")).thenReturn(groundLayer);
        when(layers.iterator()).thenReturn(new java.util.ArrayList<com.badlogic.gdx.maps.MapLayer>().iterator());
        
        setPrivateField(world, "loader", loader);
        when(loader.loadLevel(anyInt())).thenReturn(map);

        world.loadLevel(1);

        verify(loader).loadLevel(1);
        assertNotNull(world.getMap());
        assertEquals(map, world.getMap());
        assertNotNull(world.getEntityFactory());
        assertNotNull(world.getPlayerClass());
        assertNotNull(getPrivateField(world, "enemyClass"));
        
        assertNotNull(world.getPlayerClass().getEquippedWeapon());
        assertNotNull(world.getMapCollisionHandler());
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
