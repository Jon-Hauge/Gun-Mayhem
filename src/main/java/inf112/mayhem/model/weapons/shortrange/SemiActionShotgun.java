package inf112.mayhem.model.weapons.shortrange;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.WeaponType;

public class SemiActionShotgun extends ShortRangeWeapon {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 15;
    private static final int AMMO = 8;
    private static final float FIRING_DELAY = 4f;
    private static final float KNOCKBACK = 40f;

    public SemiActionShotgun(float startX, float startY, AbstractCharacter character) {
        super(startX, startY, WIDTH, HEIGHT, AMMO, FIRING_DELAY, KNOCKBACK, character);
        if (character != null) {
            setAttackBounds(new Rectangle(character.x(), character.y(), WIDTH, HEIGHT));
        }
        
        this.type = EntityType.WEAPON;
        this.state = EntityState.DROPPED;
        this.weapon = WeaponType.SEMIACTIONSHOTGUN;
        
        
    }
}