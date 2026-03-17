package inf112.mayhem.model.weapons;

import inf112.mayhem.model.EntityState;

public class PistolWeapon extends AbstractWeapon {
    private static final WeaponType WEAPON_TYPE = WeaponType.PISTOL;
    private static final float WIDTH = 10f;
    private static final float HEIGHT = 10f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;

    public PistolWeapon(float startX, float startY){
        super(startX, startY, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE);
    }
}

