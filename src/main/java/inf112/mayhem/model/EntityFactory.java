package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.controller.PlayerController;
import inf112.mayhem.controller.WeaponController;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.PistolWeapon;

public class EntityFactory {
	private final AppWorld world;
	private final TiledMap map;
	
	public EntityFactory(AppWorld world, TiledMap map) {
		this.world = world;
		this.map = map;
	}
	
	public PlayerController spawnPlayer(float x, float y) {
		PlayerModel player = new PlayerModel(x, y, map);
		world.add(player);
		return new PlayerController(player, world, this);
	}
	
	public WeaponController spawnBaseWeapon(float x, float y, PlayerController playerController){
		AbstractWeapon playerBaseWeapon = new PistolWeapon(x, y, playerController.getPlayer());
		world.add(playerBaseWeapon);
		return new WeaponController(playerBaseWeapon, world, this);

	}

	public void spawnProjectile(PlayerModel player) {
		ProjectileClass projectile = new ProjectileClass(player);
		world.add(projectile);
	}
}
