package inf112.mayhem.model.projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SemiAutoShotgunProjectileTest {

    @Test
    void constructorUsesSemiAutoShotgunStats() {
        SemiAutoShotgunProjectile projectile = new SemiAutoShotgunProjectile(7f, 8f, -1);

        assertEquals(7f, projectile.x());
        assertEquals(8f, projectile.y());
        assertEquals(10f, projectile.width());
        assertEquals(30f, projectile.height());
        assertEquals(-1, projectile.getDirection());
        assertEquals(500f, projectile.getBulletSpeed());
        assertEquals(2500f, projectile.getKnockBack());
    }
}
