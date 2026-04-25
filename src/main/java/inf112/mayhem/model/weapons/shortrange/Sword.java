package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.weapons.WeaponType;

public class Sword extends ShortRangeWeapon {
    private static final float knockBackMultiplier = 200f;

    public Sword(float startX, float startY, AbstractCharacter character, float width, float height, WeaponType weaponType,
                 EntityState startingState, int startingAmmo, float firingDelay) {
        super(startX, startY, character, width, height, weaponType, startingState, startingAmmo, firingDelay);
        setKnockback(knockBackMultiplier);
        setAttackBounds(new Rectangle(character.x(), character.y(), 8, 12));
    }

    

}
