package inf112.mayhem.model.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.RandomXS128;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.characters.EnemyClass;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.projectile.LongRangeProjectile;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.longrange.PistolWeapon;
import inf112.mayhem.model.weapons.longrange.SniperWeapon;
import inf112.mayhem.model.weapons.shortrange.PumpAction;
import inf112.mayhem.model.weapons.shortrange.SemiActionShotgun;
import inf112.mayhem.model.weapons.shortrange.Sword;

public class EntityFactory {
	private final AppWorld world;
	private final TiledMap map;
	private final RandomXS128 rand = new RandomXS128();

	public EntityFactory(AppWorld world, TiledMap map) {
		this.world = world;
		this.map = map;
	}

	public PlayerClass spawnPlayer(float x, float y) {
		PlayerClass player = new PlayerClass(x, y, map);
		world.add(player);
		return player;
	}

	public EnemyClass spawnEnemy(float x, float y) {
		EnemyClass enemy = new EnemyClass(x, y, map, 3);
		world.add(enemy);
		return enemy;
	}

	public void spawnProjectile(AbstractCharacter character) {
		AbstractProjectile projectile = new LongRangeProjectile(character.getEquippedWeapon());
		world.add(projectile);
	}

	public void spawnCrates() {
		int numOfCrates = 3;

		for (int i = 0; i < numOfCrates; i++) {
			float x = rand.nextFloat() * (float) world.width();
			float y = rand.nextFloat() * (float) world.height();

			/// Add functionality for powerups here
			///AbstractPowerUp powerUp = createRandomPowerUp(x, y);

			///WeaponCrate crate = new WeaponCrate(x, y, powerUp);
			///world.add(crate);
		}
	}

	public void spawnWeapons() {
		int numOfWeapons = 5;

		for (int i = 0; i < numOfWeapons; i++) {
			float x = rand.nextFloat() * (float) world.width();
			float y = rand.nextFloat() * (float) world.height();

			AbstractWeapon weapon = createRandomWeapon(x, y);
			weapon.dropWeapon();
			world.add(weapon);
		}
	}

	private AbstractWeapon createRandomWeapon(float x, float y) {
		int num = rand.nextInt(4);

		switch (num) {
			case 0:
				return new PistolWeapon(x, y, null);
			case 1:
				return new SniperWeapon(x, y, null);
			case 2:
				return new PumpAction(x, y, null);
			case 3:
				return new SemiActionShotgun(x, y, null);
			case 4:
				return new Sword(x, y, null);
			default:
				return new PistolWeapon(x, y, null);
		}
	}

}
