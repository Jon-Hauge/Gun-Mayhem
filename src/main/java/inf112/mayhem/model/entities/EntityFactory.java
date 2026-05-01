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
import inf112.mayhem.model.weapons.WeaponType;
import inf112.mayhem.model.weapons.guns.PistolWeapon;
import inf112.mayhem.model.weapons.guns.PumpActionShotgun;
import inf112.mayhem.model.weapons.guns.SemiAutoShotgun;
import inf112.mayhem.model.weapons.guns.SniperWeapon;
import inf112.mayhem.model.weapons.melee.Sword;

public class EntityFactory {
	private final AppWorld world;
	private final TiledMap map;
	private final RandomXS128 rand = new RandomXS128();

	/// Initiates a factory capable of producing entities for the gameworld.
	///
	/// @param world Gameworld to produce entities within
	/// @param map The tiled map for collision detection
	///
	public EntityFactory(AppWorld world, TiledMap map) {
		this.world = world;
		this.map = map;
	}

	/// Spawns a player entity within the gameworld.
	///
	/// @param x Horizontal coordinate of character
	/// @param y Vertical coordinate of character
	///
	/// @return new player in the gameworld
	///
	public PlayerClass spawnPlayer(float x, float y) {
		PlayerClass player = new PlayerClass(x, y, map);
		world.add(player);
		return player;
	}

	/// Spawns an enemy entity within the gameworld.
	///
	/// @param x Horizontal coordinate of character
	/// @param y Vertical coordinate of character
	///
	/// @return new enemy in the gameworld
	///
	public EnemyClass spawnEnemy(float x, float y) {
		EnemyClass enemy = new EnemyClass(x, y, map, 3);
		world.add(enemy);
		return enemy;
	}

	/// Spawns a projectile entity within the gameworld.
	///
	/// @param character Character to spawn projectile from
	///
	public void spawnProjectile(AbstractCharacter character) {
		
		AbstractWeapon weapon = character.getEquippedWeapon();
		
	    float spawnX = character.getDirection() == 1
		        ? character.x() + character.width()
		        : character.x() - 10;
		    float spawnY = character.y() + (character.height() / 2);
		
		if (weapon.getWeaponType() == WeaponType.SEMIAUTOSHOTGUN ||
			weapon.getWeaponType() == WeaponType.PUMPACTION) {
			
			spawnY -= 15f;
		}
		

	    AbstractProjectile projectile = ProjectileFactory.create(
	        spawnX, spawnY,
	        character.getDirection(),
	        character.getEquippedWeapon().getWeaponType()
	    );
	    if (projectile != null) {
	        world.add(projectile);
	    }
	}

	/// Spawns a crate entity within the gameworld.
	///
	public void spawnCrate() {
		float x = rand.nextFloat() * (float) world.width();
		float y = rand.nextFloat() * (float) world.height();

			/// Add functionality for powerups here
			///AbstractPowerUp powerUp = createRandomPowerUp(x, y);

			///Crate crate = new Crate(x, y, powerUp);
			///world.add(crate);
			///
	}

	/// Spawns a weapon within the gameworld.
	public void spawnWeapon() {
		float x = 300f + rand.nextFloat() * ((float) world.width() - 2 * 300f);
		float y = (float) world.height() + 100f;

		AbstractWeapon weapon = createRandomWeapon(x, y);
		weapon.dropWeapon();
		world.add(weapon);
	}

	private AbstractWeapon createRandomWeapon(float x, float y) {
		int num = rand.nextInt(4);

        return switch (num) {
            case 0 -> new PistolWeapon(x, y);
            case 1 -> new SniperWeapon(x, y);
            case 2 -> new PumpActionShotgun(x, y);
            case 3 -> new SemiAutoShotgun(x, y);
            case 4 -> new Sword(x, y);
            default -> new PistolWeapon(x, y);
        };
	}

}
