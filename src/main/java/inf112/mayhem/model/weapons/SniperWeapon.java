package inf112.mayhem.model.weapons;

import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.PlayerClass;

public class SniperWeapon extends AbstractWeapon {
    private static final WeaponType WEAPON_TYPE = WeaponType.SNIPER;
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = 10;
    private static final float firingDelay = 3f;

    public SniperWeapon(float startX, float startY, PlayerClass playerClass) {
        super(startX, startY, playerClass, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE, AMMO, firingDelay);
    }
}
