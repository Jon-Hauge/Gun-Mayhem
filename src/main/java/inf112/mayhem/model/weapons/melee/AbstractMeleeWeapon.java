package inf112.mayhem.model.weapons.melee;

import com.badlogic.gdx.math.Rectangle;
import inf112.mayhem.model.weapons.AbstractWeapon;

public abstract class AbstractMeleeWeapon extends AbstractWeapon {

	protected Rectangle attackBounds = new Rectangle();
	protected float KNOCKBACK;

    /// Initiates a melee weapon.
    ///
    /// @param x Starting horizontal coordinate of weapon
    /// @param y Starting vertical coordinate of weapon
    /// @param width Width of weapon
    /// @param height Height of weapon
    /// @param startingAmmo Amount of times weapon can strike
    /// @param firingDelay Delay between strikes
    ///
    public AbstractMeleeWeapon(float x, float y, float width, float height, int startingAmmo, float firingDelay) {
		super(x, y, width, height, startingAmmo, firingDelay);
	}

    ///
    /// @return knockback of melee weapon
    ///
    public float getKnockback() {
    	return KNOCKBACK;
    }

    ///
    /// @return attack bounds of weapon
    ///
    public Rectangle getAttackBounds() {
    	return attackBounds;
    }

    /// Updates bounds of weapon.
    ///
    /// @param holderX Horizontal coordinate to place attack-bounds in
    /// @param holderY Vertical coordinate to place attack-bounds in
    ///
    protected void updateWeaponBounds(float holderX, float holderY) {
    	attackBounds.set(holderX, holderY, width(), height());
    }

}