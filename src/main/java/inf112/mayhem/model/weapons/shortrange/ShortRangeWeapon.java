package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public abstract class ShortRangeWeapon extends AbstractWeapon {

    protected Rectangle attackBounds = new Rectangle();
    protected float knockBack;

    // Short range has no bulletspeed or knockBackMultiplier —
    // Therefore 0f are sent in as a parameter
    public ShortRangeWeapon(float startX, float startY, int width, int height, int startingAmmo, float firingDelay, float knockback) {
        super(startX, startY, width, height, startingAmmo, firingDelay);
        
    }

    public Rectangle getAttackBounds() {
        return this.attackBounds;
    }

    protected float getKnockBack() {
        return this.knockBack;
    }

    protected void setKnockback(float knockBack) {
        this.knockBack = knockBack;
    }

    protected void setAttackBounds(Rectangle attackBounds) {
        this.attackBounds = attackBounds;
    }

}