package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;


public abstract class ShortRangeWeapon extends AbstractWeapon {

    protected Rectangle attackBounds = new Rectangle();
    protected float knockBack;

    public ShortRangeWeapon(float startX, float startY, PlayerClass player, float width, float height,
            WeaponType weaponType, EntityState startingState, int startingAmmo, float firingDelay) {
        super(startX, startY, player, width, height, weaponType, startingState, startingAmmo, firingDelay, firingDelay, firingDelay);
    }

    public Rectangle getAttackBounds() {
        return this.attackBounds;
    };

    protected float getKnockBack() {
        return this.knockBack;
    }

    protected void setKnockback(float knockBack) {
        this.knockBack = knockBack;
    }


    protected void setAttackBounds(Rectangle attackBounds) {
        this.attackBounds = attackBounds;
    }

    protected void updateWeaponBounds() {
        attackBounds.set(player.x(), player.y(), this.width(), this.height());
    }

    public void update(AppWorld world, float delta) {
        updateWeaponBounds();
    }

}
