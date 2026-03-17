package inf112.mayhem.model;

public interface CharacterModel extends EntityModel {
	void jump();
	void drop();
	void hit(EntityModel obj);
	void setDirection(int n);
	int getDirection();
	int lives();
}
