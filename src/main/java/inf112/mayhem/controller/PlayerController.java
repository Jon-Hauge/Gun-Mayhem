package inf112.mayhem.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.PlayerModel;

public class PlayerController {
	
	private static final float SPEED = 150f;
	private static final float COOLDOWN_DURATION = 0.3f;
	private float COOLDOWN_TIMER = 0;
	
	//FIXED INPUT LAG WITH JUSTKEYPRESSED 
	private float jumpBufferTimer = 0f;
	private static final float JUMP_BUFFER = 0.15f;

    private final PlayerModel player;
    private final AppWorld world;
	
	public PlayerController(PlayerModel player, AppWorld world) {
		this.player = player;
		this.world = world;
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
	        
	 // Lagre hopp-input i buffer
	    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
	        jumpBufferTimer = JUMP_BUFFER;
	    }

	    // Hopp hvis buffer er aktiv og spilleren er grounded
	    if (jumpBufferTimer > 0) {
	        jumpBufferTimer -= delta;
	        player.jump(); // jump() sjekker isGrounded internt
	    }

	    if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && COOLDOWN_TIMER <= 0) {
	        this.world.spawnProjectileAt(player);
	        COOLDOWN_TIMER = COOLDOWN_DURATION;
	    }
	      
	    player.updateState();
	}
}