package inf112.mayhem.model.entities.weapons;

import com.badlogic.gdx.math.Rectangle;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.AbstractEntity;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;

public abstract class AbstractWeapon extends AbstractEntity implements WeaponModel {

    private final int maxAmmo; // set ammo to -1 for inf
    private int currentAmmo;
    private final float firingDelay;
    protected WeaponType weapon;
    protected float velocityY = 0;
    protected boolean grounded = false;
    private boolean active = true;
    private float timer = 0f;
    private boolean hasShot = false;
    private final float LIFESPAN = 20f;
    protected float GRAVITY = -10f;

    /// Initiates an abstract weapon.
    /// Inherited classes determine weapon type.
    ///
    /// @param x Horizontal coordinate of weapon
    /// @param y Vertical coordinate of weapon
    /// @param width Width of weapon
    /// @param height Height of weapon
    /// @param startingAmmo Amount of ammo weapon starts with
    /// @param firingDelay Delay between firing projectiles
    ///
    public AbstractWeapon(float x, float y, float width, float height, int startingAmmo, float firingDelay) {
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
            velocityY += GRAVITY * delta;
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

    @Override
    public void dropWeapon() {
        state = EntityState.DROPPED;
    }

    @Override
    public void pickUpWeapon() {
        state = EntityState.IN_USE;
    }

    @Override
    public WeaponType getWeaponType() {
    	return this.weapon;
    }

    @Override
    public boolean shoot() {
        if (currentAmmo == 0) {
            return false;
        }
        if (currentAmmo < 0) {
            hasShot = true;
            return true;
        } else {
            currentAmmo -= 1;
            hasShot = true;
            return true;
        }
    }

    @Override
    public void pickUpAmmo() {
        currentAmmo = maxAmmo;
    }

    @Override
    public float getFiringDelay() {
        return firingDelay;
    }

    @Override
    public int getCurrentAmmo() {
        return currentAmmo;
    }

    @Override
    public int getMaxAmmo() {
        return maxAmmo;
    }

    @Override
    public boolean outOfAmmo() {
    	return currentAmmo == 0;
    }
    
    @Override
    public void updateState() {
        if (position.y < -100f) {
            active = false;
        }
        if (timer > LIFESPAN) {
            active = false;
        }
    }

    @Override
    public void setState(EntityState state) {
    	this.state = state;
    }

    @Override
    public boolean hasShot(){
        return hasShot;
    }

    @Override
    public void soundMade(){
        hasShot = false;
    }
}
