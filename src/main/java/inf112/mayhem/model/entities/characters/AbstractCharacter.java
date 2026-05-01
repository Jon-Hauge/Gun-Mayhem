package inf112.mayhem.model.entities.characters;

	
import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.collision.MapCollisionHandler;
import inf112.mayhem.model.entities.AbstractEntity;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.weapons.AbstractWeapon;


public abstract class AbstractCharacter extends AbstractEntity implements CharacterModel {
	
	protected MapCollisionHandler collision;
	protected AbstractWeapon equippedWeapon;

    protected final float GRAVITY = 400f;
    protected final float JUMP_FORCE = 250f;
    protected final float DOUBLE_JUMP_FORCE = 200f;
    protected final float DROP_DURATION = 2f;
    protected final int WORLD_PADDING = 200;
	 
	protected int direction;
	protected int jump_counter;
	protected int lives;
	
	protected float dropDuration = 0;
	protected float startX, startY;
	
	protected boolean isGrounded;
	protected boolean ignorePlatforms;
	

	public AbstractCharacter(float x, float y, float width, float height, int lives, TiledMap map) {
		super(x, y, width, height);
		this.startX = x;
		this.startY = y;
		this.lives = lives;
		this.direction = 1;
		
		this.collision = new MapCollisionHandler(map);
	}

	@Override
	public abstract void jump();

	@Override
	public abstract void drop();

	@Override
	public abstract void hit(EntityModel obj);

	@Override
	public void die() {
		this.active = false;
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
	public void equipWeapon(AbstractWeapon weapon) {
		this.equippedWeapon = weapon;
	}

	@Override
	public AbstractWeapon getEquippedWeapon() {
		return this.equippedWeapon;
	}

	@Override
	public boolean outOfBounds(AppWorld world) {
		return bounds().x < 0 							      ||
			   bounds().x + bounds().width > world.width()    ||
			   bounds().y < 0 							      || 
			   bounds().y + bounds().height > world.height();
	}
}
