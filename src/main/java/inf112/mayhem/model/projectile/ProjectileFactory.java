package inf112.mayhem.model.projectile;

import inf112.mayhem.model.weapons.WeaponType;

public class ProjectileFactory {

    public static AbstractProjectile create(float spawnX, float spawnY, int direction, WeaponType weaponType) {
        return switch (weaponType) {
            case PISTOL -> new PistolProjectile(spawnX, spawnY, direction);
            case SNIPER -> new SniperProjectile(spawnX, spawnY, direction);
            case SWORD -> null; // sword bruker ikke prosjektil
            default -> throw new IllegalArgumentException("Ukjent våpentype: " + weaponType);
        };
    }
}