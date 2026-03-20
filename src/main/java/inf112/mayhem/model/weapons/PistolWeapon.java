package inf112.mayhem.model.weapons;

import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.enums.EntityState;

public class PistolWeapon extends AbstractWeapon {
    private static final WeaponType WEAPON_TYPE = WeaponType.PISTOL;
    private static final float WIDTH = 10f;
    private static final float HEIGHT = 10f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = -1;
    private static final float firingDelay = 1f;

    public PistolWeapon(float startX, float startY, PlayerClass player){
        super(startX, startY, player, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE, AMMO, firingDelay);
    }
}
