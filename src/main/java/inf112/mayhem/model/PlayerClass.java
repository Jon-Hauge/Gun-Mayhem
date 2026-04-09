package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.abstracts.AbstractCharacter;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.enums.EntityType;
import inf112.mayhem.model.interfaces.EntityModel;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class PlayerClass extends AbstractCharacter {

    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private AbstractWeapon equippedWeapon;

    public PlayerClass(float startX, float startY, TiledMap map) {
        super(startX, startY, WIDTH, HEIGHT, 3, map);
        this.type = EntityType.PLAYER;
        this.state = EntityState.IDLE;
    }

    @Override
    public void jump() {
        if (isGrounded) {
            velocity.y = JUMP_FORCE;
            isGrounded = false;
            jump_counter++;
        } else if (jump_counter < 2) {
            velocity.y = DOUBLE_JUMP_FORCE;
            jump_counter++;
        }
    }

    @Override
    public void drop() {
        if (isGrounded) {
            velocity.y -= GRAVITY - 230f;
            isGrounded = false;
            ignorePlatforms = true;
            dropDuration = DROP_DURATION;
        }
    }

    @Override
    public void updateState() {
        if (!isGrounded && velocity.y > 0) {
            state = EntityState.JUMPING;
        } else if (!isGrounded && velocity.y < 0) {
            state = EntityState.FALLING;
        } else if (velocity.x != 0) {
            state = EntityState.WALKING;
        } else {
            state = EntityState.IDLE;
        }
    }

    @Override
    public void update(AppWorld world, float delta) {
        float previousY = position.y;

        velocity.y -= GRAVITY * delta;
        position.x += velocity.x * delta;
        updateBounds();
        position.y += velocity.y * delta;
        updateBounds();

        if (velocity.y <= 0 && collision.isGrounded(bounds, previousY, ignorePlatforms)) {
            isGrounded = true;
            ignorePlatforms = false;
            velocity.y = 0;
            position.y = collision.getGroundY(bounds, ignorePlatforms);
            jump_counter = 0;
            updateBounds();
        } else if (outOfBounds(world)) {
            lives--;
            if (lives <= 0) {
                die();
            } else {
                respawn();
                updateBounds();
            }
        } else {
            isGrounded = false;
        }

        if (ignorePlatforms) {
            dropDuration -= delta;
            if (dropDuration <= 0) ignorePlatforms = false;
        }

        position.x = Math.max(-WORLD_PADDING, Math.min(position.x, (float) world.width() + WORLD_PADDING));
        position.y = Math.max(-WORLD_PADDING, Math.min(position.y, (float) world.height() + WORLD_PADDING));
        updateBounds();
        updateState();
    }

    @Override
    public void hit(EntityModel obj) {
        AbstractProjectile projectile = (AbstractProjectile) obj;
        velocity.x += projectile.getKnockBackMultiplier() * projectile.getDirection();
    }

    @Override
    public void equipWeapon(AbstractWeapon weapon) { this.equippedWeapon = weapon; }

    @Override
    public AbstractWeapon getEquippedWeapon() { return equippedWeapon; }
}