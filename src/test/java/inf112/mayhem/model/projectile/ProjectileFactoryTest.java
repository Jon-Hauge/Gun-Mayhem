package inf112.mayhem.model.projectile;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import inf112.mayhem.model.weapons.WeaponType;

class ProjectileFactoryTest {

    @Test
    void createReturnsPistolProjectileForPistol() {
        assertInstanceOf(PistolProjectile.class, ProjectileFactory.create(0f, 0f, 1, WeaponType.PISTOL));
    }

    @Test
    void createReturnsSniperProjectileForSniper() {
        assertInstanceOf(SniperProjectile.class, ProjectileFactory.create(0f, 0f, 1, WeaponType.SNIPER));
    }

    @Test
    void createReturnsPumpActionProjectileForPumpAction() {
        assertInstanceOf(PumpActionShotgunProjectile.class, ProjectileFactory.create(0f, 0f, 1, WeaponType.PUMPACTION));
    }

    @Test
    void createReturnsSemiAutoProjectileForSemiAutoShotgun() {
        assertInstanceOf(SemiAutoShotgunProjectile.class, ProjectileFactory.create(0f, 0f, 1, WeaponType.SEMIAUTOSHOTGUN));
    }
}
