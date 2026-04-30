package inf112.mayhem.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;

class AbstractEntityTest {

    class TestEntity extends AbstractEntity {
        TestEntity(float x, float y) {
            super(x, y, 10, 20);
            this.type = EntityType.PROJECTILE;
            this.state = EntityState.ACTIVE;
        }

        @Override
        public void updateState() {
            state = EntityState.IDLE;
        }

        @Override
        public void update(AppWorld world, float delta) {
            position.x += velocity.x * delta;
            position.y += velocity.y * delta;
            updateBounds();
        }
    }

    @Test
    void constructorStoresValues() {
        TestEntity entity = new TestEntity(5, 7);

        assertEquals(5, entity.x());
        assertEquals(7, entity.y());
        assertEquals(10, entity.width());
        assertEquals(20, entity.height());
        assertEquals(EntityType.PROJECTILE, entity.type());
        assertEquals(EntityState.ACTIVE, entity.state());
        assertTrue(entity.isActive());
    }

    @Test
    void boundsAreInitializedCorrectly() {
        TestEntity entity = new TestEntity(5, 7);

        assertEquals(5, entity.bounds().x);
        assertEquals(7, entity.bounds().y);
        assertEquals(10, entity.bounds().width);
        assertEquals(20, entity.bounds().height);
    }

    @Test
    void updateBoundsUpdatesRectangle() {
        TestEntity entity = new TestEntity(0, 0);
        entity.velocity.x = 10;
        entity.velocity.y = 20;

        entity.update(mock(AppWorld.class), 2f);

        assertEquals(20, entity.bounds().x);
        assertEquals(40, entity.bounds().y);
    }

    @Test
    void updateStateUpdatesState() {
        TestEntity entity = new TestEntity(0, 0);

        entity.updateState();

        assertEquals(EntityState.IDLE, entity.state());
    }
}