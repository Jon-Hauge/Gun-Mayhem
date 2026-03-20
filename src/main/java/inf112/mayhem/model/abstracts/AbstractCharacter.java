package inf112.mayhem.model.abstracts;

public class AbstractCharacter {
	
import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.CharacterModel;
import inf112.mayhem.model.EntityModel;

public abstract class AbstractCharacter extends AbstractEntity implements CharacterModel {
	
	int lives;
	int direction;
	boolean isGrounded;
	float startX, startY;

	public AbstractCharacter(float x, float y, int width, int height, int lives, TiledMap map) {
		super(x, y, width, height, map);
		this.lives = lives;
		this.direction = 1;
	}

	@Override
	public abstract void jump();

	@Override
	public abstract void drop();

	@Override
	public abstract void hit(EntityModel obj);

	@Override
	public void die() {
		this.isActive = false;
	}
	
	@Override
	public void respawn() {
		position.set(this.startX, this.startY);
		velocity.x = 0;
	}
	
	@Override
	public void setLives(int lives) {
		this.lives = lives;	
	}
	
	@Override
	public void setDirection(int n) {
		this.direction = n;
	}

	@Override
	public int getDirection() {
		return this.direction;
	}

	@Override
	public int lives() {
		return this.lives;
	}
	
	@Override
	public boolean outOfBounds(AppWorld world) {
		return bounds().x < 0 							      ||
			   bounds().x + bounds().width > world.width()    ||
			   bounds().y < 0 							      || 
			   bounds().y + bounds().height > world.height();
	}
>>>>>>> fa821c6 (Create abstract character class)
}
