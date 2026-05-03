package inf112.mayhem.model.entities.characters;

	
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.collision.MapCollisionHandler;
import inf112.mayhem.model.entities.AbstractEntity;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.powerUp.PowerUp;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.guns.PistolWeapon;

public abstract class AbstractCharacter extends AbstractEntity implements CharacterModel {
	
	protected MapCollisionHandler collision;
	protected AbstractWeapon equippedWeapon;
	protected AbstractWeapon baseWeapon;

    protected final float GRAVITY = 400f;
    protected final float JUMP_FORCE = 250f;
    protected final float DOUBLE_JUMP_FORCE = 200f;
    protected final float DROP_DURATION = 2f;
    protected final int WORLD_PADDING = 200;
    protected final float KNOCKBACK_DECAY = 18f;

	protected float speedMultiplier = 1f;
	protected float gravityMultiplier = 1f;
	protected float jumpBoostMultiplier = 1f;
	protected float damagePercent = 0f;
	protected float knockbackVelocity = 0f;
	 
	protected int direction;
	protected int jump_counter;
	protected int lives;
	
	protected float dropDuration = 0;
	protected float startX, startY;
	
	protected boolean isGrounded;
	protected boolean ignorePlatforms;

	private ArrayList<ActivePowerUp> activePowerUps = new ArrayList<>();
	
	/// Initiates a character entity able to interact with other entities and the map in the gameworld.
	///
	/// @param x Horizontal coordinate of the character
	/// @param y Vertical coordinate of the character
	/// @param width Width of the character
	/// @param height Height of the character
	/// @param lives Amount of lives the character starts with
	/// @param map Map character resides in
	///
	public AbstractCharacter(float x, float y, float width, float height, int lives, TiledMap map) {
		super(x, y, width, height);
		this.startX = x;
		this.startY = y;
		this.lives = lives;
		this.direction = 1;
		this.baseWeapon = new PistolWeapon(x, y);
		this.equippedWeapon = baseWeapon;
		
		this.collision = new MapCollisionHandler(map);
	}

	@Override
	public abstract void jump();

	@Override
	public abstract void drop();

	@Override
	public void hit(AbstractProjectile obj) {
		damagePercent += obj.getDamage();
	}

	@Override
	public void applyKnockback(float baseKnockback, int direction) {
		float scaledKnockback = calculateKnockback(baseKnockback);
		knockbackVelocity += scaledKnockback * direction;
	}
	
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
	public void update(AppWorld world, float delta) {
		if (this.equippedWeapon != null && this.equippedWeapon != baseWeapon && this.equippedWeapon.outOfAmmo()) {
		    this.equippedWeapon.dropWeapon();
		    this.equippedWeapon = baseWeapon;
		}

		Iterator<ActivePowerUp> iter = activePowerUps.iterator();

		while (iter.hasNext()) {
			ActivePowerUp p = iter.next();
			p.timeLeft -= delta;

			if (p.timeLeft <= 0) {
				p.powerUp.removePowerUp(this);
				iter.remove();
			}
		}
	}

	@Override
	public void updateState() {
		if (!isGrounded && velocity.y > 0) {
			state = EntityState.JUMPING;
		} else if (!isGrounded && velocity.y < 0) {
			state = EntityState.FALLING;
		} else if (velocity.x != 0) {
			state = EntityState.WALKING;
		} else {
			state = EntityState.IDLE;
		}
	}

	@Override
	public boolean outOfBounds(AppWorld world) {
		return bounds().x < 0 							      ||
			   bounds().x + bounds().width > world.width()    ||
			   bounds().y < 0 							      || 
			   bounds().y + bounds().height > world.height() + 1000;
	}

	@Override
	public void setSpeedMultiplier(float speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	@Override
	public void setGravityMultiplier(float gravityMultiplier) {
		this.gravityMultiplier = gravityMultiplier;
	}

	@Override
	public void setJumpBoostMultiplier(float jumpBoostMultiplier) {
		this.jumpBoostMultiplier = jumpBoostMultiplier;
	}

	/// Adds a powerUp to the list of active powerUps.
	///
	/// @param powerUp PowerUp to be added to list
	/// @param duration Duration of added powerUp
	///
	public void addPowerUp(PowerUp powerUp, float duration) {
		powerUp.applyPowerUp(this);
		activePowerUps.add(new ActivePowerUp(powerUp, duration));
	}
	
	/// Calculates the final knockback force applied to the character when hit.
	///
	/// @param base The base knockback force provided by the attacking weapon
	/// @return The final knockback force to be applied to the character
	public float calculateKnockback(float base) {
		
		float multiplier = 1.0f + (damagePercent / 100f);
		float scaledKnockback = base * multiplier;
		
		if (rollForHardKnockback()) {
			scaledKnockback *= 1.5f;
		}
		
		return scaledKnockback;
	}
	
	/// Determines whether a hit triggers a hard knockback effect.
	/// 
	/// @return true if hard knockback is triggered, false otherwise
	private boolean rollForHardKnockback() {
	    float chance = damagePercent /300f;
	    float clampedChance = Math.min(chance, 0.25f); 
	    return Math.random() < clampedChance;
	}

	private static class ActivePowerUp {
		PowerUp powerUp;
		float timeLeft;

		ActivePowerUp(PowerUp powerUp, float timeLeft) {
			this.powerUp = powerUp;
			this.timeLeft = timeLeft;
		}
	}
}
