package inf112.mayhem.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppWorld implements Iterable<EntityModel> {
	double width;
	double height;
	double currentTime = 0;
	List<EntityModel> objects = new ArrayList<>();
	private GameState state = GameState.MAIN_MENU;
	private ArrayList<EntityModel> objToRemove;


	public AppWorld(double width, double height) {
		super();
		this.width = width;
		this.height = height;
		this.objToRemove = new ArrayList<>(); 
	}

	public void spawnProjectileAt(PlayerModel player) {
		ProjectileModel projectile = new ProjectileModel(player);
		objects.add(projectile);
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

	public void setState(GameState state) {
		this.state = state;
	}

	
	public void removeEnity(EntityModel obj) {
		objects.remove(obj);
	}

	public void addToRemove(EntityModel enitity) {
		objToRemove.add(enitity);
	}

	public void removeAllInactive() {
		for(EntityModel obj : objToRemove) {
			objects.remove(obj);
		}
		objToRemove.clear();
	}

	public void collisions() {
		ArrayList<EntityModel> players = new ArrayList<>();
		ArrayList<EntityModel> projectiles = new ArrayList<>();

		for(EntityModel obj : objects) {
			if(obj.type() == EntityType.PLAYER) {
				players.add(obj);
			}
			
			if(obj.type() == EntityType.PROJECTILE) {
				projectiles.add(obj);
			}
		}

		for (EntityModel player : players) {

			for (EntityModel projectile : projectiles) {
				if(player.bounds().overlaps(projectile.bounds())) {
					player.hit(projectile);
					projectile.hit(player);
				}		

			}
		}
	}
}
