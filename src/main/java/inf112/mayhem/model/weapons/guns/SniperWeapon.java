package inf112.mayhem.model.weapons.guns;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class SniperWeapon extends AbstractWeapon {
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final int AMMO = 10;
    private static final float FIRING_DELAY = 3f;

    /// Initiates a sniper weapon.
    ///
    /// @param startX Horizontal coordinate of weapon
    /// @param startY Horizontal coordinate of weapon
    ///
    public SniperWeapon(float startX, float startY) {
        super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY);
        
        this.type = EntityType.WEAPON;
        this.weapon = WeaponType.SNIPER;
        this.state = EntityState.DROPPED;
        
    }

	@Override
	public float getFiringDelay() {
		return FIRING_DELAY;
	}
}