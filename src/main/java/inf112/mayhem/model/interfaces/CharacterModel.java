package inf112.mayhem.model.interfaces;

import inf112.mayhem.model.AppWorld;

import inf112.mayhem.model.weapons.AbstractWeapon;

public interface CharacterModel extends EntityModel {
	void jump();
	void drop();
	void hit(EntityModel obj);
	void setDirection(int n);
	void die();
	boolean outOfBounds(AppWorld world);
	void respawn();
	int getDirection();
	int lives();
	void setLives(int lives);
	void equipWeapon(AbstractWeapon weapon);
	AbstractWeapon getEquippedWeapon();
}
