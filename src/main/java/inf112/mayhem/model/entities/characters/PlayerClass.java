package inf112.mayhem.model.entities.characters;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.projectile.AbstractProjectile;

public class PlayerClass extends AbstractCharacter {

    private static final float WIDTH = 32f;
    private static final float HEIGHT = 32f;
    private static final int LIVES = 3;
    private boolean hasJumped = false;

    /// Initializes a new player at the specified position.
    ///
    /// @param startX Horizontal starting coordinate of player
    /// @param startY Vertical starting coordinate of player
    /// @param map Map player resides in
    ///
    public PlayerClass(float startX, float startY, TiledMap map) {
        super(startX, startY, WIDTH, HEIGHT, LIVES, map);
        this.type = EntityType.PLAYER;
        this.state = EntityState.IDLE;
    }

    @Override
    public void jump() {
        if (isGrounded) {
            velocity.y = JUMP_FORCE * jumpBoostMultiplier;
            isGrounded = false;
            jump_counter++;
            hasJumped = true;
        } else if (jump_counter < 2) {
            velocity.y = DOUBLE_JUMP_FORCE * jumpBoostMultiplier;
            jump_counter++;
            hasJumped = true;
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
    public void update(AppWorld world, float delta) {
    	super.update(world, delta);
        if (waitingForRespawn) {
            return;
        }
    	
        float previousY = position.y;

        velocity.y -= GRAVITY * delta * gravityMultiplier;
        position.x += velocity.x * delta * speedMultiplier;
        position.x += knockbackVelocity * delta;
        knockbackVelocity *= Math.max(0, 1 - KNOCKBACK_DECAY * delta);
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
                waitingForRespawn = true;
                respawnTimer = 0f;
                velocity.x = 0;
                velocity.y = 0;
            }
        } else {
            isGrounded = false;
        }

        if (ignorePlatforms) {
            dropDuration -= delta;
            if (dropDuration <= 0)
                ignorePlatforms = false;
        }

        position.x = Math.max(-WORLD_PADDING, Math.min(position.x, (float) world.width() + WORLD_PADDING));
        position.y = Math.max(-WORLD_PADDING, Math.min(position.y, (float) world.height() + WORLD_PADDING));
        updateBounds();
        updateState();
    }

    @Override
    public void hit(AbstractProjectile obj) {
    	super.hit(obj);
        applyKnockback(obj.getKnockBack(), obj.getDirection());
    }

	@Override
	public float getDamagePercentage() {
		return damagePercent;
	}

    @Override
    public boolean hasJumped() {
        return hasJumped;
    }

    @Override
    public void jumpSoundMade() {
        hasJumped = false;
    }
}