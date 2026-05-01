package inf112.mayhem.model.weapons;

import inf112.mayhem.model.entities.EntityState;

public interface WeaponModel {

	///
	/// @return Whether to shoot the weapon
	///
	boolean shoot();

	///
	/// @return Firing delay of weapon
	///
	float getFiringDelay();

	///
	/// @return Weapon's current ammo count
	///
	int getCurrentAmmo();

	///
	/// @return Weapon's max ammo count
	///
	int getMaxAmmo();

	/// Drops the weapon from the sky or player's controller
	///
	void dropWeapon();

	/// Picks up weapon from the gameworld
	///
	void pickUpWeapon();

	///
	/// @return Weapon-type of weapon
	///
	WeaponType getWeaponType();

	/// Picks up ammo from the gameworld
	///
	void pickUpAmmo();

	/// Sets state of weapon
	///
	/// @param state State to move weapon to
	///
	void setState(EntityState state);
}
