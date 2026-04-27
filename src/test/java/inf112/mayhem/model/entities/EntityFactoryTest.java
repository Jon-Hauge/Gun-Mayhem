package inf112.mayhem.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.EnemyClass;
import inf112.mayhem.model.entities.characters.PlayerClass;

class EntityFactoryTest {

    private AppWorld world;
    private TiledMap map;
    private EntityFactory factory;

    @BeforeEach
    void setUp() {
        world = mock(AppWorld.class);
        map = new TiledMap();
        factory = new EntityFactory(world, map);
    }

    @Test
    void spawnPlayerCreatesPlayerAndAddsToWorld() {
        PlayerClass player = factory.spawnPlayer(10, 20);

        assertEquals(EntityType.PLAYER, player.type());
        assertEquals(10, player.x());
        assertEquals(20, player.y());
        verify(world).add(player);
    }

    @Test
    void spawnEnemyCreatesEnemyAndAddsToWorld() {
        EnemyClass enemy = factory.spawnEnemy(30, 40);

        assertEquals(EntityType.ENEMY, enemy.type());
        assertEquals(30, enemy.x());
        assertEquals(40, enemy.y());
        assertEquals(3, enemy.getAggression());
        verify(world).add(enemy);
    }
}