package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.WeaponType;

public class PumpAction extends ShortRangeWeapon {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 10;
    private static final int AMMO = 5;
    private static final float FIRING_DELAY = 8f;
    private static final float KNOCKBACK = 100f;

    public PumpAction(float startX, float startY, AbstractCharacter character) {
        super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY, KNOCKBACK, character);
        
        this.type = EntityType.WEAPON;
        this.weapon = WeaponType.PUMPACTION;
        this.state = EntityState.DROPPED;
        if (character != null) {
            setAttackBounds(new Rectangle(character.x(), character.y(), WIDTH, HEIGHT));
        }
    }

	@Override
	public float getFiringDealy() {
		return FIRING_DELAY;
	}
}