package inf112.mayhem.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityState;
import inf112.mayhem.model.PlayerModel;

public class PlayerController {
	
	private static final float SPEED = 220f;

    private final PlayerModel player;
    private final AppWorld world;
	
	public PlayerController(PlayerModel player, AppWorld world) {
		this.player = player;
		this.world = world;
	}
	
	 public void update() {
	        float x = 0;
	        
	        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
	        	player.jump();	       
	        }
	        
	        if (!player.isGrounded()) {
	        	player.state = EntityState.JUMPING;       	
	        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))  {
	        	x -= 1;
	        	player.state = EntityState.WALKING_LEFT;
	        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.D)) {
	        	x += 1;
	        	player.state = EntityState.WALKING_RIGHT;
	        } else {
	        	player.state = EntityState.IDLE;
	        	System.out.println("IDLE");
	        }
	        
	        
	       
	        
	        player.velocity.x = x * SPEED;

	    }
	
}
