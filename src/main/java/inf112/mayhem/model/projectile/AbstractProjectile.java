package inf112.mayhem.model.projectile;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.character.PlayerClass;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.weapons.AbstractWeapon;

public abstract class AbstractProjectile implements ProjectileModel, EntityModel {
    private final Vector2 size = new Vector2(10, 7);
    protected Vector2 position = new Vector2();
    protected final Rectangle bounds = new Rectangle();
    private final AbstractWeapon weapon;
    private final EntityType type = EntityType.PROJECTILE;
    private EntityState state = EntityState.ACTIVE;
    protected boolean active = true;
    private final int direction;
    private final float BULLET_SPEED = 350f;
    private float knockBackMultiplier = 1500f;


    public AbstractProjectile(AbstractWeapon weapon) {
        this.weapon = weapon;
        PlayerClass player = weapon.getPlayer();
        this.direction = player.getDirection();

        if (direction == -1) {
            this.position.x = player.x() - size.x;
        } else {
            this.position.x = player.x() + player.width();
        }
        this.position.y = player.y() + (player.height() / 2);
    }

    @Override
    public abstract void updateBounds();

    public AbstractWeapon getWeapon() {
        return weapon;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public float x() { return this.position.x; }

    @Override
    public float y() {
        return this.position.y;
    }

    @Override
    public float width() {
        return size.x;
    }

    @Override
    public float height() {
        return size.y;
    }

    @Override
    public EntityType type() {
        return EntityType.PROJECTILE;
    }

    @Override
    public EntityState state() {
        return state;
    }

    @Override
    public void updateState() {
        if (active) {
            this.state = EntityState.ACTIVE;
        } else {
            this.state = EntityState.HIT;
        }

    }

    @Override
    public abstract void update(AppWorld world, float delta);
    // CHECK IF OUT OF BOUNDS

    @Override
    public Rectangle bounds() {
        return this.bounds;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void hit(EntityModel obj) {
        active = false;
    }

    public float getKnockBackMultiplier() {
        return this.knockBackMultiplier;
    }

    public float getBulletSpeed() {
        return this.BULLET_SPEED;
    }
}
