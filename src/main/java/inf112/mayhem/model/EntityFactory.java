package inf112.mayhem.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.controller.PlayerController;
import inf112.mayhem.controller.WeaponController;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.projectile.LongRangeProjectile;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.PistolWeapon;
import inf112.mayhem.model.weapons.WeaponType;
import inf112.mayhem.view.screens.activeGameView.WeaponView;

public class EntityFactory {
	private final AppWorld world;
	private final TiledMap map;
	
	public EntityFactory(AppWorld world, TiledMap map) {
		this.world = world;
		this.map = map;
	}
	
	public PlayerController spawnPlayer(float x, float y) {
		PlayerClass player = new PlayerClass(x, y, map);
		world.add(player);
		return new PlayerController(player, world, this);
	}

	public void spawnProjectile(PlayerClass player) {
		AbstractProjectile projectile = new LongRangeProjectile(player);
		world.add(projectile);
	}

}
