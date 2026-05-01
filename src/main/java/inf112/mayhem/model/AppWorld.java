package inf112.mayhem.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import inf112.mayhem.model.collision.MapCollisionHandler;
import inf112.mayhem.model.collision.EntityCollisionHandler;
import inf112.mayhem.model.entities.EntityFactory;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.characters.EnemyClass;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.guns.PistolWeapon;
import inf112.mayhem.model.weapons.guns.PumpActionShotgun;
import inf112.mayhem.view.maps.LevelLoader;

public class AppWorld implements Iterable<EntityModel> {
	private double width;
	private double height;
	private double currentTime = 0;
    private List<EntityModel> objects = new ArrayList<>();
    private List<EntityModel> toAdd = new ArrayList<>();
	private TiledMap map;
	private MapCollisionHandler collisionHandler;
	private LevelLoader loader = new LevelLoader();
	private EntityFactory fac;
	private GameState state = GameState.MAIN_MENU;
	private EntityCollisionHandler pCollision;
	private PlayerClass playerClass;
	private EnemyClass enemyClass;
    private float weaponSpawnTimer = 0f;
	private final float weaponSpawnInterval = 100f;
	private float crateSpawnTimer = 0f;
	private final float crateSpawnInterval = 15f;

	public AppWorld(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}

	public void loadLevel(int levelNumber) {
		// Load in map and factories
		this.map = loader.loadLevel(levelNumber);
		this.fac = new EntityFactory(this, map);

		// Send map to collision handler
		this.pCollision = new EntityCollisionHandler(this);
		this.collisionHandler = new MapCollisionHandler(map);

		// Player
		this.playerClass = fac.spawnPlayer(500f, 400f);
		this.enemyClass = fac.spawnEnemy(900f, 400f);
		
		this.playerClass.equipWeapon(new PumpActionShotgun(500f, 400f));
	}

	@Override
	public Iterator<EntityModel> iterator() {
		return objects.iterator();
	}

	public double width() {
		return width;
	}

	public double height() {
		return height;
	}

	public double currentTime() {
		return currentTime;
	}

	public void add(EntityModel obj) {
		if (obj != null) {
			toAdd.add(obj);
		}
	}

	public GameState state() {
		return state;
	}

	public PlayerClass getPlayerClass() {
		return playerClass;
	}

	public EnemyClass getEnemyClass() {return enemyClass; }

	public TiledMap getMap() {
		return map;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public void removeInactive() {
		int before = objects.size();
		objects.removeIf(obj -> !obj.isActive());
		int after = objects.size();
		
		if (before != after) {
	        System.out.println("Removed " + (before - after) + " objects. Remaining: " + after);
	    }
	}

	public void remove(Object obj) {
		int before = objects.size();
		objects.remove(obj);
		int after = objects.size();

		if (before != after) {
			System.out.println("Removed " + (before - after) + " objects. Remaining: " + after);
		}
	}

	public EntityFactory getEntityFactory() {
		return fac;
	}

	public void update(float delta) {
		objects.addAll(toAdd);
		toAdd.clear();

		for (EntityModel obj : this) {
			obj.update(this, delta);
		}
		weaponSpawnTimer += delta;
		if (weaponSpawnTimer >= weaponSpawnInterval) {
			System.out.println("Spawning weapon");
			fac.spawnWeapon();
			weaponSpawnTimer = 0f;
		}

		crateSpawnTimer += delta;
		if (crateSpawnTimer >= crateSpawnInterval) {
			System.out.println("Spawning crate");
			fac.spawnCrate();
			crateSpawnTimer = 0f;
		}

		pCollision.checkCollisions();
		this.removeInactive();
	}

	public void evaluateState() {
		if (playerClass.lives() <= 0) {
			this.setState(GameState.GAME_OVER);
		} else if (enemyClass.lives() <= 0) {
			this.setState(GameState.VICTORY);
		}
	}

	public int updateLevelCount(int currentLevel, int unlockedLevels) {
		int newUnlockedLevels = unlockedLevels;

		if (state == GameState.VICTORY && currentLevel == unlockedLevels && unlockedLevels < 3) {
			newUnlockedLevels++;
		}
		return newUnlockedLevels;
	}

	public void reset() {
		this.setState(GameState.ACTIVE);
		playerClass.setLives(3);
		enemyClass.setLives(3);
	}

	public MapCollisionHandler getMapCollisionHandler() {
		return collisionHandler;
	}
}
