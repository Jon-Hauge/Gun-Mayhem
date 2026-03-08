package inf112.mayhem.model.collision;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityModel;
import inf112.mayhem.model.ProjectileModel;

public class ProjectileCollisionHandler {
	private final AppWorld world;
	
	public ProjectileCollisionHandler(AppWorld world) {
		this.world = world;
	}
	
	public void checkCollisions() {
		for (EntityModel obj : world) {
			if (!(obj instanceof ProjectileModel projectile)) continue;
			
			for (EntityModel target : world) {
				if (target == projectile || target instanceof ProjectileModel) continue;
				
				if (projectileHit(projectile, target)) {
					projectile.hit(target);
					target.hit(projectile);
				}
			}
		}
	}
	
	private boolean projectileHit(ProjectileModel projectile, EntityModel target) {
		return projectile.bounds().overlaps(target.bounds());
	}

}
