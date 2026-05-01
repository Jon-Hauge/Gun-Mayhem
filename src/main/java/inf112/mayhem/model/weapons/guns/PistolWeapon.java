package inf112.mayhem.model.weapons.guns;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class PistolWeapon extends AbstractWeapon {
    private static final float WIDTH = 10f;
    private static final float HEIGHT = 10f;
    private static final int AMMO = -1;
    private static final float FIRING_DELAY = 1f;

    public PistolWeapon(float x, float y){
        super(x, y, WIDTH, HEIGHT, AMMO, FIRING_DELAY);
        
        this.type = EntityType.WEAPON;
        this.weapon = WeaponType.PISTOL;
        this.state = EntityState.IN_USE;
    }

	@Override
	public float getFiringDelay() {
		return FIRING_DELAY;
	}

}
