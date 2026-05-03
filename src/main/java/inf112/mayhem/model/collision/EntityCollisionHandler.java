package inf112.mayhem.model.collision;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.powerUp.Crate;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.characters.CharacterModel;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.projectile.ProjectileModel;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class EntityCollisionHandler {
	private final AppWorld world;

	/// Creates a collision handler for the entities in the gameworld,
	/// allowing the entities to interact with each other.
	///
	/// @param world Gameworld to handle collision in
	///
	public EntityCollisionHandler(AppWorld world) {
		this.world = world;
	}

	/// Checks collisions for all entities in the gameworld.
	/// Is called every time the game is updated.
	///
	public void checkCollisions() {
	    for (EntityModel obj : world) {

	    	if (obj instanceof AbstractWeapon weapon && weapon.state() == EntityState.DROPPED) {
	    	    for (EntityModel other : world) {
	    	        if (other instanceof AbstractCharacter character) {
	    	            if (weapon.bounds().overlaps(character.bounds())) {
	    	                character.equipWeapon(weapon);
	    	                weapon.setState(EntityState.IN_USE);
	    	            }
	    	        }
	    	    }
	    	}

			if (obj instanceof Crate crate) {
				for (EntityModel other : world) {
					if (other instanceof AbstractCharacter character) {
						if (crate.bounds().overlaps(character.bounds())) {
							crate.open(world, character);
						}
					}
				}
			}

	        if (!(obj instanceof AbstractProjectile projectile)) continue;

	        for (EntityModel target : world) {
	            if (target == projectile) continue;

	            if (!(target instanceof ProjectileModel) && !(target instanceof CharacterModel)) {
	                continue;
	            }

	            if (projectileHit(projectile, target)) {
	                projectile.hit();

	                if (target instanceof ProjectileModel targetProjectile) {
	                    targetProjectile.hit();
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
