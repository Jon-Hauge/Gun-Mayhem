package inf112.mayhem.model.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.controller.PlayerController;
import inf112.mayhem.controller.WeaponController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.character.PlayerClass;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.projectile.LongRangeProjectile;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.longrange.PistolWeapon;
import inf112.mayhem.view.screens.activeGameView.WeaponViewFactory;

public class EntityFactory {
	private final AppWorld world;
	private final TiledMap map;

	public EntityFactory(AppWorld world, TiledMap map) {
		this.world = world;
		this.map = map;
	}

	public PlayerClass spawnPlayer(float x, float y) {
		PlayerClass player = new PlayerClass(x, y, world.getMap());
		world.add(player);
		return player;
	}

	public WeaponController spawnBaseWeapon(float x, float y, PlayerController playerController) {
		AbstractWeapon playerBaseWeapon = new PistolWeapon(x, y, playerController.getPlayer());
		world.add(playerBaseWeapon);
		playerController.getPlayer().equipWeapon(playerBaseWeapon);

		return new WeaponController(playerBaseWeapon, world);

	}

	public void spawnProjectile(PlayerClass player) {
		AbstractProjectile projectile = new LongRangeProjectile(player.getEquippedWeapon());
		world.add(projectile);
	}

}
