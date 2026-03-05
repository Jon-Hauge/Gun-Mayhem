package inf112.mayhem.model;

import com.badlogic.gdx.math.Rectangle;

public interface EntityModel {
	
	float x();
	float y();
	float width();
	float height();
	EntityType type();
	EntityState state();
	
	void updateState();
	void update(AppWorld world, float delta);

	Rectangle bounds();
	boolean isActive();
	void setActive(boolean active);
	void hit(EntityModel obj);


}
