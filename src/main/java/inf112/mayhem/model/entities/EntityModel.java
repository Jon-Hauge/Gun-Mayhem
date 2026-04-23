package inf112.mayhem.model.entities;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;

public interface EntityModel {
	
	float x();
	float y();
	float width();
	float height();
	EntityType type();
	EntityState state();
	
	void updateState();
	void updateBounds();
	void update(AppWorld world, float delta);

	Rectangle bounds();
	boolean isActive();
}
