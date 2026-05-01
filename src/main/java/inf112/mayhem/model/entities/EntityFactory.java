package inf112.mayhem.model.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.RandomXS128;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.characters.EnemyClass;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.projectile.ProjectileFactory;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.guns.PistolWeapon;
import inf112.mayhem.model.weapons.guns.PumpActionShotgun;
import inf112.mayhem.model.weapons.guns.SemiAutoShotgun;
import inf112.mayhem.model.weapons.guns.SniperWeapon;
import inf112.mayhem.model.weapons.melee.Sword;

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
	    float spawnX = character.getDirection() == 1
	        ? character.x() + character.width()
	        : character.x() - 10;
	    float spawnY = character.y() + (character.height() / 2);

	    AbstractProjectile projectile = ProjectileFactory.create(
	        spawnX, spawnY,
	        character.getDirection(),
	        character.getEquippedWeapon().getWeaponType()
	    );
	    if (projectile != null) {
	        world.add(projectile);
	    }
	}

	public void spawnCrate() {
		float x = rand.nextFloat() * (float) world.width();
		float y = rand.nextFloat() * (float) world.height();

			/// Add functionality for powerups here
			///AbstractPowerUp powerUp = createRandomPowerUp(x, y);

			///WeaponCrate crate = new WeaponCrate(x, y, powerUp);
			///world.add(crate);
			///

	}

	public void spawnWeapon() {
		float x = 300f + rand.nextFloat() * ((float) world.width() - 2 * 300f);
		float y = (float) world.height() + 100f;

		AbstractWeapon weapon = createRandomWeapon(x, y);
		weapon.dropWeapon();
		world.add(weapon);

	}

	private AbstractWeapon createRandomWeapon(float x, float y) {
		int num = rand.nextInt(4);

		switch (num) {
			case 0:
				return new PistolWeapon(x, y);
			case 1:
				return new SniperWeapon(x, y);
			case 2:
				return new PumpActionShotgun(x, y);
			case 3:
				return new SemiAutoShotgun(x, y);
			case 4:
				return new Sword(x, y);
			default:
				return new PistolWeapon(x, y);
		}
	}

}
