package inf112.mayhem.model.weapons.longrange;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class PistolWeapon extends AbstractWeapon {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int AMMO = -1;
    private static final float firingDelay = 1f;
    private static final float knockBackMultiplier = 1200f;
    private static final float bulletSpeed = 500f;

    public PistolWeapon(float x, float y, AbstractCharacter character){
        super(x, y, WIDTH, HEIGHT, AMMO, firingDelay, knockBackMultiplier, bulletSpeed, character);
        
        this.type = EntityType.WEAPON;
        this.weapon = WeaponType.PISTOL;
        this.state = EntityState.IN_USE;
    }

}
