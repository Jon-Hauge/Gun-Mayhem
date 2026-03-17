package inf112.mayhem.model;

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
}
