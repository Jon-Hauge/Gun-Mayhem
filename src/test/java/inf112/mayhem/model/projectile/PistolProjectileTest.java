package inf112.mayhem.model.projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PistolProjectileTest {

    @Test
    void constructorUsesPistolStats() {
        PistolProjectile projectile = new PistolProjectile(15f, 25f, -1);

        assertEquals(15f, projectile.x());
        assertEquals(25f, projectile.y());
        assertEquals(10f, projectile.width());
        assertEquals(7f, projectile.height());
        assertEquals(-1, projectile.getDirection());
        assertEquals(500f, projectile.getBulletSpeed());
        assertEquals(1000f, projectile.getKnockBack());
    }
}
