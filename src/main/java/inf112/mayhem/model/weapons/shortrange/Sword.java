package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.weapons.WeaponType;

public class Sword extends ShortRangeWeapon {

    public Sword(float startX, float startY, PlayerClass player, float width, float height, WeaponType weaponType,
            EntityState startingState, int startingAmmo, float firingDelay) {
        super(startX, startY, player, width, height, weaponType, startingState, startingAmmo, firingDelay);
        setKnockback(200f);
        setAttackBounds(new Rectangle(player.x(), player.y(), 8, 12));
    }

    

}
