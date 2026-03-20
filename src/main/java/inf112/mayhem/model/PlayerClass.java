package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.abstracts.AbstractCharacter;
import inf112.mayhem.model.collision.MapCollisionHandler;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.enums.EntityType;
import inf112.mayhem.model.interfaces.EntityModel;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class PlayerClass extends AbstractCharacter {
	
	private final static int WIDTH = 32;
    private final static int HEIGHT = 32;
    
    private float dropDuration = 0;
    
	private MapCollisionHandler collision;
    
	private AbstractWeapon equippedWeapon;
    
	public PlayerClass(float startX, float startY, TiledMap map) {
		super(startX, startY, WIDTH, HEIGHT, 3, map);
		this.type = EntityType.PLAYER;
		this.state = EntityState.IDLE;
		this.isGrounded = false;
		this.ignorePlatforms = false;
		this.jump_counter = 0;
		
		this.collision = new MapCollisionHandler(map);
		
		respawn();
        updateBounds();
	}
	
	@Override
	public void updateBounds() {
        bounds.set(position.x, position.y, size.x, size.y);
	}
	
	@Override
	public void jump() {
		if (isGrounded) {
			velocity.y = JUMP_FORCE;
			isGrounded = false;
			jump_counter++;
		} else if (!isGrounded && jump_counter < 2) {
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
	public float x() { return position.x; }

	@Override
	public float y() { return position.y; }

	@Override
	public float width() { return size.x; }

	@Override
	public float height() { return size.y; }
	
	@Override
	public EntityType type() { return type; }
	
	@Override
	public EntityState state() { return state; }
	
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
			this.lives -= 1;
			if (this.lives <= 0) {
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

	    position.x = Math.max(0 - WORLD_PADDING, Math.min(position.x, (float) world.width() + WORLD_PADDING));
	    position.y = Math.max(0 - WORLD_PADDING, Math.min(position.y, (float) world.height() + WORLD_PADDING));
	    updateBounds();
	}
	
	@Override
	public void setDirection(int n) { this.direction = n; }

	@Override
	public int lives() { return this.lives; }

	@Override
	public void setLives(int lives) { this.lives = lives; }

	@Override
	public int getDirection() { return this.direction; }

	@Override
	public void hit(EntityModel obj) {
		ProjectileClass projectile = (ProjectileClass) obj;
		velocity.x += projectile.getKnockBackMultiplier() * projectile.getDirection();
	}

	@Override
	public Rectangle bounds() { return this.bounds; }

	@Override
	public boolean isActive() { return this.active; }
	
	@Override
	public boolean outOfBounds(AppWorld world) {
		return bounds().x < 0 ||
		    bounds().x + bounds().width > world.width() ||
		    bounds().y < 0 ||
		    bounds().y + bounds().height > world.height();
	}
	
	@Override
	public void die() { this.active = false; }

	@Override
	public void respawn() {
		position.set(this.startX, this.startY);
		velocity.x = 0;
	}

	@Override
	public void equipWeapon(AbstractWeapon weapon) { this.equippedWeapon = weapon; }

	@Override
	public AbstractWeapon getEquippedWeapon() { return equippedWeapon; }
}