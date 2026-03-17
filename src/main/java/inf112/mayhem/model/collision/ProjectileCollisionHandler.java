package inf112.mayhem.model.collision;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityModel;
import inf112.mayhem.model.ProjectileClass;
import inf112.mayhem.model.ProjectileModel;

public class ProjectileCollisionHandler {
	private final AppWorld world;
	
	public ProjectileCollisionHandler(AppWorld world) {
		this.world = world;
	}
	
	public void checkCollisions() {
	    for (EntityModel obj : world) {
	        if (!(obj instanceof ProjectileClass projectile)) continue;

	        for (EntityModel target : world) {
	            if (!(target instanceof ProjectileModel targetModel)) continue;

	            if (target == projectile) continue;
	            if (target instanceof ProjectileClass) continue;

	            if (projectileHit(projectile, target)) {
	                projectile.hit(targetModel);
	                targetModel.hit(projectile);
	            }
	        }
	    }
	}
	
	private boolean projectileHit(ProjectileClass projectile, EntityModel target) {
		return projectile.bounds().overlaps(target.bounds());
	}

}
