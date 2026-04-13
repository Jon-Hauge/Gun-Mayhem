package inf112.mayhem.model.weapons;

import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.enums.EntityState;

public class SniperWeapon extends AbstractWeapon {
    private static final WeaponType WEAPON_TYPE = WeaponType.SNIPER;
    private static final float WIDTH = 25f;
    private static final float HEIGHT = 15f;
    private static final EntityState STARTING_STATE = EntityState.IN_USE;
    private static final int AMMO = 10;
    private static final float FIRING_DELAY = 3f;
    private static final float KNOCKBACK_MULTIPLIER = 200f;
    private static final float BULLET_SPEED = 800f;

    public SniperWeapon(float startX, float startY, PlayerClass player) {
        super(startX, startY, player, WIDTH, HEIGHT, WEAPON_TYPE, STARTING_STATE, AMMO, FIRING_DELAY, KNOCKBACK_MULTIPLIER, BULLET_SPEED);
    }
}