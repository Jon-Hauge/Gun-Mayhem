package inf112.mayhem.model;

public interface ModelObject {
	// TODO: document this!
	
	double x();
	double y();
	double width();
	double height();
	String appearance();
	String sound();
	void step(AppWorld world, double deltaTime);
}
