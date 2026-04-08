package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.EntityState;
import inf112.mayhem.model.PlayerModel;
import inf112.mayhem.model.weapons.WeaponType;

public class SemiActionShotgun extends ShortRangeWeapon {

    private static final WeaponType WEAPON_TYPE = WeaponType.SHOTGUN;
    private static final float WIDTH = 20f;
    private static final float HEIGHT = 12f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = 8;
    private static final float firingDelay = 4f;

    public SemiActionShotgun(float startX, float startY, PlayerModel player, float width, float height,
            WeaponType weaponType, EntityState startingState, int startingAmmo, float firingDelay) {
        super(startX, startY, player, width, height, weaponType, startingState, startingAmmo, firingDelay);
    }

    @Override
    public Rectangle getAttackBounds() {
        return new Rectangle(player.x(), player.y(), WIDTH, HEIGHT);
    }

    @Override
    public float getKnockBack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKnockBack'");
    }

}
