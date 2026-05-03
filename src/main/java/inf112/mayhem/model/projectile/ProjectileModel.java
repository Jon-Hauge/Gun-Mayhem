package inf112.mayhem.model.projectile;

import inf112.mayhem.model.entities.EntityModel;

public interface ProjectileModel extends EntityModel {

	///
	/// @return Direction of projectile: -1 for left, 1 for right
	///
	int getDirection();

	///
	/// @return Amount of knockback in projectile
	///
	float getKnockBack();
	
	///
	/// @return Amount of damage in projectile
	///
	float getDamage();

	///
	/// @return Speed of projectile
	///
	float getBulletSpeed();

	/// Initiates the hit action for the projectile when it hits a target
	///
	/// @param obj Object that the projectile hits
	///
	void hit();
}
