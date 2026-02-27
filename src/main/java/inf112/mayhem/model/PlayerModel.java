package inf112.mayhem.model;

public class PlayerModel implements ModelObject {
	private double x, y, dx = 100, dy = 100;
	private int speed = 10;

	public PlayerModel(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	@Override
	public double width() {
		return 100;
	}

	@Override
	public double height() {
		return 100;
	}

	@Override
	public String appearance() {
		return "obligator.png";
	}

	@Override
	public String sound() {
		return "blipp.ogg";
	}

	@Override
	public void step(AppWorld world, double deltaTime, String direction) {
    	switch (direction) {
    	    case "right" -> x += speed;
    	    case "left"  -> x -= speed;
    	    case "up"    -> y += speed;
    	    case "down"  -> y -= speed;
    	}
	    x = Math.max(0, Math.min(x, world.width()  - width()));
	    y = Math.max(0, Math.min(y, world.height() - height()));
	}
}
