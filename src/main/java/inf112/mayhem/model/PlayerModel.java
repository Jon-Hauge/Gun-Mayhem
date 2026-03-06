package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerModel implements EntityModel {
	private MapCollisionHandler collision;
	private final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    private final Vector2 size = new Vector2(32, 32);
    private final Rectangle bounds = new Rectangle();
	private int direction = 1; // 1 right -1 left

    
    private final EntityType type = EntityType.PLAYER;
    
    private final float GRAVITY = 400f;
    private final float JUMP_FORCE = 250f;
	private boolean active = true;

    
    private EntityState state = EntityState.IDLE;
    private boolean isGrounded = false;

	public PlayerModel(float startX, float startY, TiledMap map) {
		this.collision = new MapCollisionHandler(map);
		position.set(startX, startY);
        updateBounds();
	}
	
	private void updateBounds() {
	        bounds.set(position.x, position.y, size.x, size.y);
	}
	 
	public void jump() {
		
		//NEEDS TO BE CHANGED ONCE WE HAVE DIFFERENT LAYERS OF "GROUND"
		if (isGrounded) {
			velocity.y = JUMP_FORCE;
		}
	 }
	
	public void drop() {
		if (isGrounded) {
			velocity.y -= GRAVITY * 4.8;
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
		if (!isGrounded) {
	        state = EntityState.JUMPING;
	    } 
	    else if (velocity.x < 0) {
	        state = EntityState.WALKING_LEFT;
	    } 
	    else if (velocity.x > 0) {
	        state = EntityState.WALKING_RIGHT;
	    } 
	    else {
	        state = EntityState.IDLE;
	    }
	}

	@Override
	public void update(AppWorld world, float delta) {
		
		//Players ability to move
		velocity.y -= GRAVITY * delta;
		position.mulAdd(velocity, delta);
		
		//Resets downward velocity when grounded   
		if (collision.isGrounded(bounds, velocity.y)) {
		    velocity.y = 0;
		    isGrounded = true;
		    position.y = collision.getGroundY(bounds);
		    updateBounds();
		} else {
			
		    isGrounded = false;
		}

        // clamp i world
        position.x = Math.max(0, Math.min(position.x, (float) world.width()  - size.x));
        position.y = Math.max(0, Math.min(position.y, (float) world.height() - size.y));
        
       
        updateBounds();
    }

	


		public void setDirection(int direction) {
		if (direction == -1 || direction == 1) {
			this.direction = direction;
		} else {
			throw new IllegalArgumentException("Direction must be -1 or 1");
		}
	}


	public int getDirection() {
		return this.direction;
	}

	@Override
	public void hit(EntityModel obj) {
		ProjectileModel projectile = (ProjectileModel) obj;

		float knockBack = projectile.getKnockBackMultiplier();

		velocity.x += knockBack * projectile.getDirection();

		//Todo Update Posistion
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

	
