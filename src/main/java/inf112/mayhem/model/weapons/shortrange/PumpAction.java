package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.weapons.WeaponType;

public class PumpAction extends ShortRangeWeapon {

    private static final WeaponType WEAPON_TYPE = WeaponType.PUMPACTION;
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 10f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = 5;
    private static final float FIRING_DELAY = 8f;
    private static final float knockBackMultiplier = 100f;

    public PumpAction(float startX, float startY, AbstractCharacter character) {
        super(startX, startY, character, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE, AMMO, FIRING_DELAY);
        setKnockback(knockBackMultiplier);
        if (character != null) {
            setAttackBounds(new Rectangle(character.x(), character.y(), WIDTH, HEIGHT));
        }
    }
}