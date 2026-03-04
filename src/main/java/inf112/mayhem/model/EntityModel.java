package inf112.mayhem.model;

public interface EntityModel {
	
	float x();
	float y();
	float width();
	float height();
	EntityType type();
	
	void update(AppWorld world, float delta);

}
