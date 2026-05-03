package inf112.mayhem.model.weapons.guns;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class PumpActionShotgun extends AbstractWeapon {
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 10f;
    private static final int AMMO = 5;
    private static final float FIRING_DELAY = 2f;

    /// Initiates a pump action shotgun weapon.
    ///
    /// @param startX Horizontal coordinate of weapon
    /// @param startY Horizontal coordinate of weapon
    ///
    public PumpActionShotgun(float startX, float startY) {
        super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY);
        
        this.type = EntityType.WEAPON;
        this.weapon = WeaponType.PUMPACTION;
        this.state = EntityState.DROPPED;
 
    }

}