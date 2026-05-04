package inf112.mayhem.model.entities.weapons;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;

public class PistolWeapon extends AbstractWeapon {
    private static final float WIDTH = 10f;
    private static final float HEIGHT = 10f;
    private static final int AMMO = -1;
    private static final float FIRING_DELAY = 0.5f;

    /// Initiates a pistol weapon.
    ///
    /// @param startX Horizontal coordinate of weapon
    /// @param startY Horizontal coordinate of weapon
    ///
    public PistolWeapon(float startX, float startY){
        super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY);
        
        this.type = EntityType.WEAPON;
        this.weapon = WeaponType.PISTOL;
        this.state = EntityState.IN_USE;
    }

	@Override
	public float getFiringDelay() {
		return FIRING_DELAY;
	}
}
