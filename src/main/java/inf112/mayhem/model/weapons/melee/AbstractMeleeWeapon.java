package inf112.mayhem.model.weapons.melee;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public abstract class AbstractMeleeWeapon extends AbstractWeapon {

	protected Rectangle attackBounds = new Rectangle();
	protected float KNOCKBACK;
	
    public AbstractMeleeWeapon(float x, float y, float width, float height, int startingAmmo, float firingDelay) {
		super(x, y, width, height, startingAmmo, firingDelay);
	}
    
    public float getKnockback() {
    	return KNOCKBACK;
    }
    
    public Rectangle getAttackBounds() {
    	return attackBounds;
    }
    
    protected void updateWeaponBounds(float holderX, float holderY) {
    	attackBounds.set(holderX, holderY, width(), height());
    }

}