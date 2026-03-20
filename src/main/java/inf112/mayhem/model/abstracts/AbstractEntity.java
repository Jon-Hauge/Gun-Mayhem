package inf112.mayhem.model.abstracts;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.enums.EntityType;
import inf112.mayhem.model.interfaces.EntityModel;

public abstract class AbstractEntity implements EntityModel {
	Vector2 position;
	Vector2 velocity;
	Vector2 size;
	Rectangle bounds;
	
	EntityType type;
	EntityState state;
	
	boolean isActive;
	
	public AbstractEntity(float x, float y, int width, int height, TiledMap map) {
		position = new Vector2(x, y);
		velocity = new Vector2(0,0);
		size = new Vector2(width, height);
		bounds = new Rectangle(x, y, width, height);
		isActive = true;
		
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
		return isActive;
	}
	
	@Override
	public EntityType type() {
		return this.type;
	}
	
	@Override
	public abstract EntityState state();
	
	@Override
	public abstract void updateState();
	
	@Override
	public void updateBounds() {
        bounds.set(position.x, position.y, size.x, size.y);
	}
	
	@Override
	public abstract void update(AppWorld world, float delta);

	

}
