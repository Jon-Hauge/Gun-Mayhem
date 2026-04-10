package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.weapons.WeaponType;

public class PumpAction extends ShortRangeWeapon {

    private static final WeaponType WEAPON_TYPE = WeaponType.SHOTGUN;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = 5;
    private static final float firingDelay = 8f;

    public PumpAction(float startX, float startY, PlayerClass player, float width, float height, WeaponType weaponType,
            EntityState startingState, int startingAmmo, float firingDelay) {
        super(startX, startY, player, width, height, weaponType, startingState, startingAmmo, firingDelay);
        setKnockback(100f);
        setAttackBounds(new Rectangle(player.x(), player.y(), 10, 15));
    }
}
