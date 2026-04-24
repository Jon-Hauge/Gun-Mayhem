package inf112.mayhem.model.weapons.longrange;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class SniperWeapon extends AbstractWeapon {
    private static final WeaponType WEAPON_TYPE = WeaponType.SNIPER;
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = 10;
    private static final float FIRING_DELAY = 3f;
    private static final float KNOCKBACK_MULTIPLIER = 200f;
    private static final float BULLET_SPEED = 800f;

    public SniperWeapon(float startX, float startY, AbstractCharacter character) {
        super(startX, startY, character, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE, AMMO, FIRING_DELAY, KNOCKBACK_MULTIPLIER, BULLET_SPEED);
    }
}