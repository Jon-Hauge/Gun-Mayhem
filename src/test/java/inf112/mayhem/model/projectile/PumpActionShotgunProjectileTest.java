package inf112.mayhem.model.projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PumpActionShotgunProjectileTest {

    @Test
    void constructorUsesPumpActionStats() {
        PumpActionShotgunProjectile projectile = new PumpActionShotgunProjectile(11f, 12f, 1);

        assertEquals(11f, projectile.x());
        assertEquals(12f, projectile.y());
        assertEquals(10f, projectile.width());
        assertEquals(20f, projectile.height());
        assertEquals(1, projectile.getDirection());
        assertEquals(500f, projectile.getBulletSpeed());
        assertEquals(3000f, projectile.getKnockBack());
    }
}
