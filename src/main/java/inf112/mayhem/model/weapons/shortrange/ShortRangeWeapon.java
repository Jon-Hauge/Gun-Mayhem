package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.EntityModel;
import inf112.mayhem.model.EntityState;
import inf112.mayhem.model.PlayerModel;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;


public abstract class ShortRangeWeapon extends AbstractWeapon {

    public ShortRangeWeapon(float startX, float startY, PlayerModel player, float width, float height,
            WeaponType weaponType, EntityState startingState, int startingAmmo, float firingDelay) {
        super(startX, startY, player, width, height, weaponType, startingState, startingAmmo, firingDelay);
    }

    public abstract Rectangle getAttackBounds();

    public abstract float getKnockBack();

    public boolean isHitting(EntityModel target) {

        return getAttackBounds().overlaps(target.bounds());
    }

}
