package inf112.mayhem.model.weapons;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.collision.MapCollisionHandler;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.view.screens.activeGameView.WeaponView;

public abstract class AbstractWeapon implements EntityModel {

    private float x;
    private float y;
    private float width;
    private float height;
    private EntityState state;
    private WeaponType weaponType;
    private int maxAmmo; // set ammo to -1 for inf
    private int currentAmmo;
    private float firingDelay;
    protected AbstractCharacter character;
    private WeaponView weaponView;
    protected float knockBackMultiplier;
    protected float bulletSpeed;

    private Rectangle bounds = new Rectangle();

    protected float velocityY = 0;
    protected float gravity = -500f;
    protected boolean grounded = false;

    public AbstractWeapon(float startX, float startY, AbstractCharacter character, float width, float height,
                          WeaponType weaponType, EntityState startingState, int startingAmmo, float firingDelay,
                          float knockBackMultiplier, float bulletSpeed) {
        this.x = startX;
        this.y = startY;
        this.character = character;
        this.height = height;
        this.width = width;
        this.weaponType = weaponType;
        this.state = startingState;
        this.maxAmmo = startingAmmo;
        this.currentAmmo = startingAmmo;
        this.firingDelay = firingDelay;
        this.bulletSpeed = bulletSpeed;
        this.knockBackMultiplier = knockBackMultiplier;
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public float height() {
        return height;
    }

    @Override
    public EntityType type() {
        return EntityType.WEAPON;
    }

    @Override
    public EntityState state() {
        return state;
    }

    @Override
    public void updateState() {
        int i = 1; // MIDDLERTIDIG
    }

    @Override
    public void update(AppWorld world, float delta) {
        if (character != null) {
            this.x = character.x();
            this.y = character.y();
        }
        updateBounds();
        updateState();
        /// Update once mapcollisionhandler is given to world, and world is given to abstractweapon
        ///if (state == EntityState.DROPPED && !grounded) {
        ///    velocityY += gravity * delta;
        ///    float previousY = y;
        ///    y += velocityY * delta;

        ///    Rectangle bounds = bounds();
        ///    bounds.setPosition(x, y);

        ///    if (world.getMapCollisionHandler.isGrounded(bounds, previousY, false)) {
        ///        grounded = true;
        ///        velocityY = 0;
        ///        y = world.getMapCollisionHandler.getGroundY(bounds, false);
        ///    }
        ///}
    }

    @Override
    public Rectangle bounds() {
        return bounds;
    }

    @Override
    public boolean isActive() {
        return true; // MIDDLERTIDLIG
    }

    @Override
    public void updateBounds() {
        bounds.set(x, y, width, height);
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
        return weaponType;
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

    public WeaponView getWeaponView() {
        return weaponView;
    }

    public void setWeaponView(WeaponView weaponView) {
        this.weaponView = weaponView;
    }

    public float getKnockBackMultiplier() {
        return this.knockBackMultiplier;
    }

    public float getBulletSpeed() {
        return this.bulletSpeed;
    }
}
