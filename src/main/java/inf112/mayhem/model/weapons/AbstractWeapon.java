package inf112.mayhem.model.weapons;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.AbstractEntity;

public abstract class AbstractWeapon extends AbstractEntity implements WeaponModel {

    private int maxAmmo; // set ammo to -1 for inf
    private int currentAmmo;
    private float firingDelay;
    protected WeaponType weapon;
    protected float velocityY = 0;
    protected float gravity = -10f;
    protected boolean grounded = false;
    private boolean active = true;
    private float timer = 0f;
    private final float lifespan = 20f;

    public AbstractWeapon(float x, float y, int width, int height, int startingAmmo, float firingDelay) {
    	super(x, y, width, height);
        this.maxAmmo = startingAmmo;
        this.currentAmmo = startingAmmo;
        this.firingDelay = firingDelay;
        this.type = EntityType.WEAPON;
        this.state = EntityState.DROPPED;
    }

    @Override
    public void update(AppWorld world, float delta) {

        if (state == EntityState.DROPPED) {
            timer += delta;
        }

        updateBounds();
        updateState();

        if (state == EntityState.DROPPED && !grounded) {
            velocityY += gravity * delta;
            float previousY = position.y;
            position.y += velocityY * delta;

            Rectangle bounds = bounds();
            bounds.setPosition(position.x, position.y);

            if (world.getMapCollisionHandler().isGrounded(bounds, previousY, false)) {
                grounded = true;
                velocityY = 0;
                position.y = world.getMapCollisionHandler().getGroundY(bounds, false);
            }
        }
    }

    @Override
    public boolean isActive() {
        return active;
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
    
    @Override
    public void updateState() {
        if (position.y < -100f) {
            active = false;
        }
        if (timer > lifespan) {
            active = false;
        }
    }
    
    public void setState(EntityState state) {
    	this.state = state;
    }
}
