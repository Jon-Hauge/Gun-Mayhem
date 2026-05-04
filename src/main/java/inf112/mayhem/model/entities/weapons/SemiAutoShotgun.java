package inf112.mayhem.model.entities.weapons;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;

public class SemiAutoShotgun extends AbstractWeapon {
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final int AMMO = 8;
    private static final float FIRING_DELAY = 1f;

    /// Initiates a semi auto shotgun weapon.
    ///
    /// @param startX Horizontal coordinate of weapon
    /// @param startY Horizontal coordinate of weapon
    ///
    public SemiAutoShotgun(float startX, float startY) {
        super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY);
        
        this.type = EntityType.WEAPON;
        this.state = EntityState.DROPPED;
        this.weapon = WeaponType.SEMIAUTOSHOTGUN;
    }

	@Override
	public float getFiringDelay() {
		return FIRING_DELAY;
	}
}