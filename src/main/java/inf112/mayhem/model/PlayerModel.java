package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import inf112.mayhem.model.collision.MapCollisionHandler;
import inf112.mayhem.model.collision.ProjectileCollisionHandler;

public class PlayerModel implements CharacterModel {
	private MapCollisionHandler collision;
	private ProjectileCollisionHandler pCollision;
	private final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    private final Vector2 size = new Vector2(32, 32);
    private final Rectangle bounds = new Rectangle();
	private int direction = 1;
	private int lives = 3;

	
    private final float GRAVITY = 400f;
    private final float JUMP_FORCE = 250f;
    private final float DOUBLE_JUMP_FORCE = 200f;
    private final float DROP_DURATION = 2f;
	
    private float dropDuration = 0;
    private int jump_counter = 0;
    
    private final EntityType type = EntityType.PLAYER;
    private EntityState state = EntityState.IDLE;
    private boolean isGrounded = false;
    private boolean active = true;
    private boolean ignorePlatforms = false;
    
	public PlayerModel(float startX, float startY, TiledMap map) {
		this.collision = new MapCollisionHandler(map);
		position.set(startX, startY);
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
	public float x() {
		return position.x;
	}

	@Override
	public float y() {
		return position.y;
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
		return type;
	}
	
	@Override
	public EntityState state() {
		return state;
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
	    
	    //CHECK FOR COLLITIONS WITH GROUND OR PLATFORM
		if (velocity.y <= 0 && collision.isGrounded(bounds, previousY, ignorePlatforms)) {
		    isGrounded = true;
		    ignorePlatforms = false;		    velocity.y = 0;
		    position.y = collision.getGroundY(bounds, ignorePlatforms);
		    jump_counter = 0;
		    updateBounds();
		} else {
		    isGrounded = false;
		}

	    //DROP TIMER
	    if (ignorePlatforms) {
	        dropDuration -= delta;
	        if (dropDuration <= 0) ignorePlatforms = false;
	    }

	    //WORLD BASED ON PLAYER BOUNDS
	    position.x = Math.max(0, Math.min(position.x, (float) world.width() - size.x));
	    position.y = Math.max(0, Math.min(position.y, (float) world.height() - size.y));
	    updateBounds();
	}
	
	@Override
	public void setDirection(int n) {
		this.direction = n;
	}

	@Override
	public int lives() {
		return this.lives;
	}

	@Override
	public int getDirection() {
		return this.direction;
	}

	@Override
	public void hit(EntityModel obj) {
		ProjectileClass projectile = (ProjectileClass) obj;

		float knockBack = projectile.getKnockBackMultiplier();

		velocity.x += knockBack * projectile.getDirection();
	}

	@Override
	public Rectangle bounds() {
		return this.bounds;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public void setActive(boolean active) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setActive'");
	}

}

	
