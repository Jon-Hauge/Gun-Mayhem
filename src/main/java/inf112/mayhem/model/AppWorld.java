package inf112.mayhem.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import inf112.mayhem.model.collision.ProjectileCollisionHandler;
import inf112.mayhem.model.entities.EntityFactory;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.characters.EnemyClass;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.longrange.PistolWeapon;
import inf112.mayhem.view.maps.LevelLoader;

public class AppWorld implements Iterable<EntityModel> {
	private double width;
	private double height;
	private double currentTime = 0;
	private List<EntityModel> objects = new ArrayList<>();
	private TiledMap map;
	private LevelLoader loader = new LevelLoader();
	private EntityFactory fac;
	private GameState state = GameState.MAIN_MENU;
	private ProjectileCollisionHandler pCollision;
	private PlayerClass playerClass;
	private EnemyClass enemyClass;
	private AbstractWeapon playerPistol;
	private AbstractWeapon enemyPistol;

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
		this.pCollision = new ProjectileCollisionHandler(this);

		// Player
		this.playerClass = fac.spawnPlayer(500f, 400f);
		this.enemyClass = fac.spawnEnemy(900f, 400f);

		// Weapons
		setupWeapons(fac);
	}

	private void setupWeapons(EntityFactory entityFactory) {
		this.playerPistol = new PistolWeapon(500f, 400f, playerClass);
		this.enemyPistol = new PistolWeapon(900f, 400f, enemyClass);
		playerClass.equipWeapon(playerPistol);
		enemyClass.equipWeapon(enemyPistol);
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
			objects.add(obj);
		}
	}

	public GameState state() {
		return state;
	}

	public PlayerClass getPlayerClass() {
		return playerClass;
	}

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

	public void update(float delta) {
		for (EntityModel obj : this) {
			obj.update(this, delta);
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

		if (currentLevel == unlockedLevels && unlockedLevels < 3) {
			newUnlockedLevels++;
		}
		return newUnlockedLevels;
	}

	public void reset() {
		this.setState(GameState.ACTIVE);
		playerClass.setLives(3);
		enemyClass.setLives(3);
	}

}
