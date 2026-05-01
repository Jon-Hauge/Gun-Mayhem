package inf112.mayhem.model.weapons.melee;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.WeaponType;

public class Sword extends AbstractMeleeWeapon {

    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final int AMMO = -1;
    private static final float FIRING_DELAY = 0.5f;

    /// Initiates a sword weapon.
    ///
    /// @param startX Starting horizontal coordinate
    /// @param startY Starting vertical coordinate
    ///
    public Sword(float startX, float startY) {
    	super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY);
    	
    	this.KNOCKBACK = 200f;
    	this.weapon = WeaponType.SWORD;
        this.type = EntityType.WEAPON;
        this.state = EntityState.IN_USE;
    }

	@Override
	public float getFiringDelay() {
		return FIRING_DELAY;
	}
}