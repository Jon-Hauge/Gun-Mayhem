package inf112.mayhem.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import inf112.mayhem.model.AppWorld;
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
	        float x = 0, y = 0;

	        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))  x -= 1;
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.D)) x += 1;
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)   || Gdx.input.isKeyPressed(Input.Keys.W)) y += 1;
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) y -= 1;

	        if (x != 0 || y != 0) {
	            float len = Vector2.len(x, y);
	            x /= len; y /= len;
	        }

	        player.velocity.set(x * SPEED, y * SPEED);
	    }
	
}
