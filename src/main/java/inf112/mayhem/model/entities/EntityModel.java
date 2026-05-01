package inf112.mayhem.model.entities;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;

public interface EntityModel {

	///
	/// @return Horizontal coordinate of entity
	///
	float x();

	///
	/// @return Vertical coordinate of entity
	///
	float y();

	///
	/// @return Width of entity
	///
	float width();

	///
	/// @return Height of entity
	///
	float height();

	///
	/// @return entity's type
	///
	EntityType type();

	///
	/// @return entity's state
	///
	EntityState state();

	/// Updates the entity's state
	///
	void updateState();

	/// Updates the entity's bounds
	///
	void updateBounds();

	/// Updates the gameworld each gametic, i.e. since the last update
	///
	/// @param world Gameworld to be updated
	/// @param delta One gametic
	///
	void update(AppWorld world, float delta);

	///
	/// @return Bounds of entity
	///
	Rectangle bounds();

	/// Checks if entity is active. Unactive entities may be removed to conserve memory.
	///
	/// @return Whether entity is active in gameworld
	///
	boolean isActive();
}
