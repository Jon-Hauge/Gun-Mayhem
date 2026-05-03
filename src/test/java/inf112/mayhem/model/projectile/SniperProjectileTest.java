package inf112.mayhem.model.projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SniperProjectileTest {

    @Test
    void constructorUsesSniperStats() {
        SniperProjectile projectile = new SniperProjectile(2f, 3f, 1);

        assertEquals(2f, projectile.x());
        assertEquals(3f, projectile.y());
        assertEquals(15f, projectile.width());
        assertEquals(5f, projectile.height());
        assertEquals(1, projectile.getDirection());
        assertEquals(900f, projectile.getBulletSpeed());
        assertEquals(3000f, projectile.getKnockBack());
    }
}
