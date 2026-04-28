package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.weapons.WeaponType;

public class Sword extends ShortRangeWeapon {
    private static final WeaponType WEAPON_TYPE = WeaponType.SWORD;
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = 0;
    private static final float HIT_DELAY = 4f;
    private static final float knockBackMultiplier = 200f;

    public Sword(float startX, float startY, AbstractCharacter character) {
        super(startX, startY, character, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE, AMMO, HIT_DELAY);
        setKnockback(knockBackMultiplier);
        if (character != null) {
            setAttackBounds(new Rectangle(character.x(), character.y(), 8, 12));
        }
    }
}
