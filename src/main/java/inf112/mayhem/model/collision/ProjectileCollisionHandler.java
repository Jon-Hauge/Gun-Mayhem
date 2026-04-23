package inf112.mayhem.model.collision;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.character.CharacterModel;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.projectile.ProjectileModel;
import inf112.mayhem.model.projectile.AbstractProjectile;

public class ProjectileCollisionHandler {
	private final AppWorld world;
	
	public ProjectileCollisionHandler(AppWorld world) {
		this.world = world;
	}
	
	public void checkCollisions() {
	    for (EntityModel obj : world) {
	        if (!(obj instanceof AbstractProjectile projectile)) continue;

	        for (EntityModel target : world) {
	            if (target == projectile) continue;

	            if (!(target instanceof ProjectileModel) && !(target instanceof CharacterModel)) {
	                continue;
	            }

	            if (projectileHit(projectile, target)) {
	                projectile.hit(target);

	                if (target instanceof ProjectileModel targetProjectile) {
	                    targetProjectile.hit(projectile);
	                } else if (target instanceof CharacterModel character) {
	                    character.hit(projectile);
	                }
	            }
	        }
	    }
	}

	private boolean projectileHit(AbstractProjectile projectile, EntityModel target) {
	    return projectile.bounds().overlaps(target.bounds());
	}

}
