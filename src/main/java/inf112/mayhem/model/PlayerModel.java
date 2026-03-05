package inf112.mayhem.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerModel implements EntityModel {
	private final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    private final Vector2 size = new Vector2(100, 100);
    private final Rectangle bounds = new Rectangle();
    
    private final EntityType type = EntityType.PLAYER;
    
    private final float GRAVITY = 400f;
    private final float JUMP_FORCE = 100f;
    
    private EntityState state = EntityState.IDLE;
    private boolean isGrounded = false;

	public PlayerModel(float startX, float startY) {
		position.set(startX, startY);
        updateBounds();
	}
	
	private void updateBounds() {
	        bounds.set(position.x, position.y, size.x, size.y);
	}
	 
	public void jump() {
		 
		 //NEEDS TO BE CHANGED ONCE WE HAVE DIFFERENT LAYERS OF "GROUND"
		 if (position.y <= 0) {
			 velocity.y = JUMP_FORCE;
			 isGrounded = false;
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

        // clamp i world
        position.x = Math.max(0, Math.min(position.x, (float) world.width()  - size.x));
        position.y = Math.max(0, Math.min(position.y, (float) world.height() - size.y));
        
        //Resets downward velocity when grounded
        if (position.y <= 0) {
        	velocity.y = 0;
        	isGrounded = true;
        }

        updateBounds();
    }

	@Override
	public boolean isInBounds(AppWorld world) {
		float x = position.x;
		float y = position.y;
        return !(x <= 0) && !(y <= 0) && !(x >= world.width()) && !(y >= world.height());
    }
}

	
