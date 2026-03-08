package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class EntityFactory {
	private final AppWorld world;
	private final TiledMap map;
	
	public EntityFactory(AppWorld world, TiledMap map) {
		this.world = world;
		this.map = map;
	}
	
	public PlayerModel spawnPlayer(float x, float y) {
		PlayerModel player = new PlayerModel(x, y, map);
		world.add(player);
		return player;
	}
	
	public void spawnProjectile(PlayerModel player) {
		ProjectileModel projectile = new ProjectileModel(player);
		world.add(projectile);
	}

}
