package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.EntityState;
import inf112.mayhem.model.PlayerModel;
import inf112.mayhem.model.weapons.WeaponType;

public class PumpAction extends ShortRangeWeapon{


    public PumpAction(float startX, float startY, PlayerModel player, float width, float height, WeaponType weaponType,
            EntityState startingState, int startingAmmo, float firingDelay) {
        super(startX, startY, player, width, height, weaponType, startingState, startingAmmo, firingDelay);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Rectangle getAttackBounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAttackBounds'");
    }

    @Override
    public float getKnockBack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKnockBack'");
    }

}
