package inf112.mayhem.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityState;
import inf112.mayhem.model.PlayerModel;

public class PlayerController {
	
	private static final float SPEED = 220f;
	private static final float COOLDOWN_DURATION = 0.3f;
	private static float COOLDOWN_TIMER = 0;

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
	    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
	        x -= 1;
	    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.D))
	        x += 1;
	    if (Gdx.input.isKeyPressed(Input.Keys.T) && COOLDOWN_TIMER <= 0) {
	    	
	    	//CHANGE PRINT WITH PROJECTILE METHOD
	    	System.out.println("PEW");
	    	COOLDOWN_TIMER = COOLDOWN_DURATION;
	    }
	        
	    //VELOCITY
	    player.velocity.x = x * SPEED;
	        
	    //JUMP INPUT
	    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
	        player.jump();
	    }
	        
	    player.updateState();
	}
}
