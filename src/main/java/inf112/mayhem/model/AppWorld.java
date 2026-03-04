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

	public AppWorld(double width, double height) {
		super();
		this.width = width;
		this.height = height;
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
		objects.add(obj);
	}

	public GameState state() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
}
