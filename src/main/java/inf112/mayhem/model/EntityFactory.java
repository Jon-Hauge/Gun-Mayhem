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

	public WeaponController spawnBaseWeapon(float x, float y, PlayerController playerController) {
		AbstractWeapon playerBaseWeapon = new PistolWeapon(x, y, playerController.getPlayer());
		playerBaseWeapon.setWeaponView(createWeaponView(playerBaseWeapon.getWeaponType()));
		world.add(playerBaseWeapon);
		playerController.getPlayer().equipWeapon(playerBaseWeapon);
		return new WeaponController(playerBaseWeapon, world, this);

	}

	public void spawnProjectile(PlayerClass player) {
		AbstractProjectile projectile = new LongRangeProjectile(player.getEquippedWeapon());
		world.add(projectile);
	}

	public static WeaponView createWeaponView(WeaponType weapon) {
		switch (weapon) {
			case SHOTGUN:
				return new WeaponView(new Texture("weapons/shotgun.png"),
						60f, 170f, 1f);
			case AK:
				return new WeaponView(new Texture("weapons/assault_rifle.png"),
						60f, 170f, 1.2f);
			case SWORD:
				return new WeaponView(new Texture("weapons/sword.png"),
						30f, 150f, 1f);
			case SNIPER:
				return new WeaponView(new Texture("weapons/sniper.png"),
						30f, 150f, 1f);
			default:
				return new WeaponView(new Texture("weapons/pistol.png"),
						40f, 160f, 1f);
		}

	}

}
