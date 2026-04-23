package inf112.mayhem.model.projectile;

import inf112.mayhem.model.entities.EntityModel;

public interface ProjectileModel extends EntityModel {
	void hit(EntityModel obj);
}
