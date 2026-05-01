package inf112.mayhem.model.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import inf112.mayhem.model.AppWorld;

public abstract class AbstractEntity implements EntityModel {
	protected Vector2 position;
	public Vector2 velocity;
	protected Vector2 size;
	protected Rectangle bounds;
	protected EntityType type;
	protected EntityState state;
	protected boolean active;

	/// Initiates an abstract entity capable of interacting with the gameworld.
	///
	/// @param x Horizontal coordinate of entity
	/// @param y Vertical coordinate of entity
	/// @param width Width of entity
	/// @param height Height of entity
	///
	public AbstractEntity(float x, float y, float width, float height) {
		position = new Vector2(x, y);
		velocity = new Vector2(0,0);
		size = new Vector2(width, height);
		bounds = new Rectangle(x, y, width, height);
		active = true;
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
	public Rectangle bounds() {
		return bounds;
	}
	
	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public EntityType type() {
		return this.type;
	}
	
	@Override
	public EntityState state() {
		return this.state;
	}
	
	@Override
	public abstract void updateState();
	
	@Override
	public void updateBounds() {
        bounds.set(position.x, position.y, size.x, size.y);
	}
	
	@Override
	public abstract void update(AppWorld world, float delta);
}