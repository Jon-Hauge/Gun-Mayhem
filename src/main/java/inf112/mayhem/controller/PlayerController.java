package inf112.mayhem.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;

public class PlayerController {
	
	private static final float SPEED = 150f;
	private float COOLDOWN_TIMER = 0;
	
    private final PlayerClass player;
    private final AppWorld world;

	/// Creates a controller for the player to execute tasks in game
	///
	/// @param player Player to create controller for
	/// @param world World player resides in
	///
	public PlayerController(PlayerClass player, AppWorld world) {
		this.player = player;
		this.world = world;
	}

	/// Updates controller for every tick in game
	///
	/// @param delta One gametic
	///
	public void update(float delta) {
	    float x = 0f;
	    
	    if (COOLDOWN_TIMER > 0) COOLDOWN_TIMER -= delta;
	        
	    // MOVEMENT INPUT
	    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
	        x -= 1;
	        player.setDirection(-1);
	    }

	    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.D)) {
	        x += 1;
	        player.setDirection(1);
	    }
	        
	    // VELOCITY
	    player.velocity.x = x * SPEED;
	    
	    // JUMP AND DROP
	    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
	        player.jump();
	    }
	    
	    if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
	    	player.drop();
	    }

		// SHOOTING
	    if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && COOLDOWN_TIMER <= 0) {
	        AbstractWeapon weapon = player.getEquippedWeapon();
	        if (weapon != null && weapon.shoot()) {
	            world.getEntityFactory().spawnProjectile(player);
	            COOLDOWN_TIMER = weapon.getFiringDelay();
	        }
	    }
	     
	    player.updateState();
	}

	/// Picks up weapon if weapon is dropped
	///
	/// @param weapon Weapon to be picked up
	///
	public void pickUpWeapon(AbstractWeapon weapon) {
		player.equipWeapon(weapon);
	}
}