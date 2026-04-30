package inf112.mayhem.model.weapons;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.AbstractEntity;

public abstract class AbstractWeapon extends AbstractEntity {

    private int maxAmmo; // set ammo to -1 for inf
    private int currentAmmo;
    private float firingDelay;
    protected AbstractCharacter character;
    protected float knockBackMultiplier;
    protected float bulletSpeed;
    protected WeaponType weapon;

    protected float velocityY = 0;
    protected float gravity = -10f;
    protected boolean grounded = false;

    public AbstractWeapon(float x, float y, int width, int height, int startingAmmo, float firingDelay,

                          float knockBackMultiplier, float bulletSpeed, AbstractCharacter character) {
    	super(x, y, width, height);
        this.maxAmmo = startingAmmo;
        this.currentAmmo = startingAmmo;
        this.character = character;
        this.firingDelay = firingDelay;
        this.bulletSpeed = bulletSpeed;
        this.knockBackMultiplier = knockBackMultiplier;
        
        this.type = EntityType.WEAPON;
        this.state = EntityState.DROPPED;
    }

    @Override
    public void update(AppWorld world, float delta) {
        if (character != null) {
            this.position.x = character.x();
            this.position.y = character.y();
            return;
        }
        updateBounds();
        updateState();
        /// Update once mapcollisionhandler is given to world, and world is given to abstractweapon
        if (state == EntityState.DROPPED && !grounded) {
            velocityY += gravity * delta;
            position.y += velocityY * delta;

            Rectangle bounds = bounds();
            bounds.setPosition(position.x, position.y);

        ///    if (world.getMapCollisionHandler.isGrounded(bounds, previousY, false)) {
        ///        grounded = true;
        ///        velocityY = 0;
        ///        y = world.getMapCollisionHandler.getGroundY(bounds, false);
        ///    }
        }
    }

    @Override
    public boolean isActive() {
        return true; // MIDDLERTIDLIG
    }

    public void dropWeapon() {
        state = EntityState.DROPPED;
    }

    public void openCrate() {
        state = EntityState.OPENED_WEAPON_CRATE;
    }

    public void pickUpWeapon() {
        state = EntityState.OPENED_WEAPON_CRATE;
    }

    public WeaponType getWeaponType() {
    	return this.weapon;
    }

    public boolean shoot() {
        if (currentAmmo == 0) {
            return false;
        }
        if (currentAmmo < 0) {
            return true;
        } else {
            currentAmmo -= 1;
            return true;
        }
    }

    public void pickUpAmmo() {
        currentAmmo = maxAmmo;
    }

    public float getFiringDelay() {
        return firingDelay;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public AbstractCharacter getCharacter() {
        return character;
    }

    public void setCharacter(AbstractCharacter character) {
        this.character = character;
    }

    public float getKnockBackMultiplier() {
        return this.knockBackMultiplier;
    }

    public float getBulletSpeed() {
        return this.bulletSpeed;
    }
    
    @Override
    public void updateState() {
    }
    
    public void setState(EntityState state) {
    	this.state = state;
    }
}
