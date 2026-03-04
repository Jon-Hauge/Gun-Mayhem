package inf112.mayhem.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerModel implements EntityModel {
	private final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    private final Vector2 size = new Vector2(32, 32);
    private final Rectangle bounds = new Rectangle();
    
    private final EntityType type = EntityType.PLAYER;
    
    private final float GRAVITY = 400f;
    
    public EntityState state = EntityState.IDLE;

	public PlayerModel(float startX, float startY) {
		position.set(startX, startY);
        updateBounds();
	}
	
	private void updateBounds() {
	        bounds.set(position.x, position.y, size.x, size.y);
	}
	 
	public boolean isGrounded() {
		return position.y <= 0;
	}
	 
	 public void jump() {
		 if (isGrounded()) {
			 velocity.y = 100f;
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
	public void update(AppWorld world, float delta) {
		//Players ability to move
		velocity.y -= 250f * delta;
        position.mulAdd(velocity, delta);

        // clamp i world
        position.x = Math.max(0, Math.min(position.x, (float) world.width()  - size.x));
        position.y = Math.max(0, Math.min(position.y, (float) world.height() - size.y));
        
        //Resets downward velocity when grounded
        if (isGrounded()) {
        	velocity.y = 0;
        }

        updateBounds();
    }
}

	
