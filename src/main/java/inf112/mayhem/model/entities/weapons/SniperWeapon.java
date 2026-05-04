package inf112.mayhem.model.entities.weapons;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;

public class SniperWeapon extends AbstractWeapon {
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final int AMMO = 6;
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