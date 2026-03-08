package inf112.mayhem.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ProjectileModel implements EntityModel{

    private final Vector2 size = new Vector2(10, 7);
    private Vector2 position = new Vector2();
    private final Rectangle bounds = new Rectangle();
    
    private final EntityType type = EntityType.PROJECTILE;
    private EntityState state = EntityState.ACTIVE;
    
    private boolean active = true;
    private final int direction; // 1 = høyre, -1 = venstre
    private final float BULLET_SPEED = 150f;
    private float knockBackMultiplier = 100;


    public ProjectileModel(PlayerModel player) {
		this.direction = player.getDirection();
        this.position.x = player.x()  + 20 - player.width() ;
        this.position.y = player.y() + (player.width()/2);
    }

    private void updateBounds() {
	        bounds.set(position.x, position.y, size.x, size.y);
	}

    @Override
    public float x() {
        return this.position.x;
    }

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
    public void update(AppWorld world, float delta) {
        position.x += BULLET_SPEED * direction * delta;
        this.updateBounds();

        //CHECK IF OUT OF BOUNDS
        if (position.x + size.x < 0     || 
        	position.x > world.width()  ||
        	position.y + size.y < 0     || 
        	position.y > world.height())
        	{
                active = false;
            }
        
        updateState();
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
        this.active = active;
    }

    @Override
    public void hit(EntityModel obj) {
        active = false;
    }

    public float getKnockBackMultiplier() {
        return this.knockBackMultiplier;
    }

    public int getDirection() {
		return this.direction;
	}

}
