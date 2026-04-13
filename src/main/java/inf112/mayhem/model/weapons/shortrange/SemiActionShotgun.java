package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.weapons.WeaponType;

public class SemiActionShotgun extends ShortRangeWeapon {

    private static final WeaponType WEAPON_TYPE = WeaponType.SHOTGUN;
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = 8;
    private static final float FIRING_DELAY = 4f;

    public SemiActionShotgun(float startX, float startY, PlayerClass player) {
        super(startX, startY, player, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE, AMMO, FIRING_DELAY);
        setKnockback(40f);
        setAttackBounds(new Rectangle(player.x(), player.y(), WIDTH, HEIGHT));
    }
}