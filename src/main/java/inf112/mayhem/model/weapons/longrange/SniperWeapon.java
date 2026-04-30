package inf112.mayhem.model.weapons.longrange;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class SniperWeapon extends AbstractWeapon {
    private static final int WIDTH = 25;
    private static final int HEIGHT = 15;
    private static final int AMMO = 10;
    private static final float FIRING_DELAY = 3f;

    public SniperWeapon(float startX, float startY, AbstractCharacter character) {
        super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY, character);
        
        this.type = EntityType.WEAPON;
        this.weapon = WeaponType.SNIPER;
        this.state = EntityState.DROPPED;
        
    }

	@Override
	public float getFiringDelay() {
		return FIRING_DELAY;
	}
}