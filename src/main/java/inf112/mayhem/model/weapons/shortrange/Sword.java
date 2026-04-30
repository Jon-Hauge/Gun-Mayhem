package inf112.mayhem.model.weapons.shortrange;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.WeaponType;

public class Sword extends ShortRangeWeapon {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 15;
    private static final int AMMO = -1;
    private static final float FIRING_DELAY = 0.5f;
    private static final float KNOCKBACK = 200f;

    public Sword(float startX, float startY) {
    	super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY, KNOCKBACK);
    	
    	this.weapon = WeaponType.SWORD;
        this.type = EntityType.WEAPON;
        this.state = EntityState.IN_USE;
    }

	@Override
	public float getFiringDelay() {
		return FIRING_DELAY;
	}
}