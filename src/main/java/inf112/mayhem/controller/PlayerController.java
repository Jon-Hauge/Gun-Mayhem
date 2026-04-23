package inf112.mayhem.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityFactory;
import inf112.mayhem.model.entities.character.PlayerClass;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class PlayerController {
	
	private static final float SPEED = 150f;
	private static final float COOLDOWN_DURATION = 0.3f;
	private float COOLDOWN_TIMER = 0;
	
    private final PlayerClass player;
    private final AppWorld world;
	
	public PlayerController(PlayerClass player, AppWorld world) {
		this.player = player;
		this.world = world;
	}
	
	public PlayerClass getPlayer() {
		return player;
	}

	public void update(float delta) {
	    float x = 0f;
	    
	    if (COOLDOWN_TIMER > 0) COOLDOWN_TIMER -= delta;
	        
	    //INPUT
	    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
	        x -= 1;
	        player.setDirection(-1);
	    }

	    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.D)) {
	        x += 1;
	        player.setDirection(1);
	    }
	        
	    //VELOCITY
	    player.velocity.x = x * SPEED;
	    
	    // JUMP AND DROP
	    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
	        player.jump();
	    }
	    
	    if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
	    	player.drop();
	    }
	     
	    player.updateState();
	}

	public void pickUpWeapon(AbstractWeapon weapon) {
		player.equipWeapon(weapon);
	}
}