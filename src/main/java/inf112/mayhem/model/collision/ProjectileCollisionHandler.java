package inf112.mayhem.model.collision;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.PlayerModel;

public class ProjectileCollisionHandler {
	private final AppWorld world;
	private final PlayerModel player;
	
	public ProjectileCollisionHandler(AppWorld world, PlayerModel player) {
		this.world = world;
		this.player = player;
	}
	
	public boolean projectileHit() {
		return false;
	}
	
	public boolean knockback() {
		return false;
	}

}
