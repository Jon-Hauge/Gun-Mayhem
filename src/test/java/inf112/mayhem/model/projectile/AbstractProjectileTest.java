package inf112.mayhem.model.projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;

class AbstractProjectileTest {

    private PistolProjectile projectile;

    @BeforeEach
    void setUp() {
        projectile = new PistolProjectile(10, 20, 1);
    }

    @Test
    void constructorSetsInitialValues() {
        assertEquals(10, projectile.x());
        assertEquals(20, projectile.y());
        assertEquals(10, projectile.width());
        assertEquals(7, projectile.height());
        assertEquals(EntityType.PROJECTILE, projectile.type());
        assertEquals(EntityState.ACTIVE, projectile.state());
        assertTrue(projectile.isActive());
    }

    @Test
    void updateMovesProjectileInDirection() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 1000);
        when(world.height()).thenReturn((double) 1000);

        projectile.update(world, 0.5f);

        assertEquals(260, projectile.x());
        assertEquals(EntityState.ACTIVE, projectile.state());
        assertTrue(projectile.isActive());
    }

    @Test
    void updateDeactivatesWhenOutOfBounds() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 100);
        when(world.height()).thenReturn((double) 1000);

        projectile.update(world, 1f);

        assertFalse(projectile.isActive());
        assertEquals(EntityState.HIT, projectile.state());
    }

    @Test
    void hitDeactivatesProjectile() {
        projectile.hit();
        projectile.updateState();

        assertFalse(projectile.isActive());
        assertEquals(EntityState.HIT, projectile.state());
    }

    @Test
    void gettersReturnConfiguredValues() {
        assertEquals(1, projectile.getDirection());
        assertEquals(500f, projectile.getBulletSpeed());
        assertEquals(1000f, projectile.getKnockBack());
    }
}
