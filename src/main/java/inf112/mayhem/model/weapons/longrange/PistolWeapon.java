package inf112.mayhem.model.weapons.longrange;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class PistolWeapon extends AbstractWeapon {
    private static final WeaponType WEAPON_TYPE = WeaponType.PISTOL;
    private static final float WIDTH = 10f;
    private static final float HEIGHT = 10f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = -1;
    private static final float firingDelay = 1f;
    private static final float knockBackMultiplier = 300f;
    private static final float bulletSpeed = 500f;

    public PistolWeapon(float startX, float startY, AbstractCharacter character){
        super(startX, startY, character, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE, AMMO, firingDelay, knockBackMultiplier, bulletSpeed);
    }
}
