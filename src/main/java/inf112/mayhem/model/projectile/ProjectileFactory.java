package inf112.mayhem.model.projectile;

import inf112.mayhem.model.weapons.WeaponType;

public class ProjectileFactory {

    /// Creates a projectile of a given weapon-type.
    ///
    /// @param spawnX Starting horizontal coordinate of projectile
    /// @param spawnY Starting vertical coordinate of projectile
    /// @param direction Direction of projectile: -1 for left, 1 for right
    /// @param weaponType Type of weapon which fired projectile
    ///
    public static AbstractProjectile create(float spawnX, float spawnY, int direction, WeaponType weaponType) {
        return switch (weaponType) {
            case PISTOL -> new PistolProjectile(spawnX, spawnY, direction);
            case SNIPER -> new SniperProjectile(spawnX, spawnY, direction);
            case PUMPACTION -> new PumpActionShotgunProjectile(spawnX, spawnY, direction);
            case SEMIAUTOSHOTGUN -> new SemiAutoShotgunProjectile(spawnX, spawnY, direction);
            case SWORD -> null; // sword does not use projectiles
            default -> throw new IllegalArgumentException("Unknown weapon-type: " + weaponType);
        };
    }
}