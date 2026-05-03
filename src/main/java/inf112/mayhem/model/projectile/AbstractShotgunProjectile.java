package inf112.mayhem.model.projectile;

/// 
/// Base class for shotgun projectiles
/// Extends {@link AbstractProjectile} with a maximum range,
/// after which the projectile becomes inactive.
///
public class AbstractShotgunProjectile extends AbstractProjectile {
	
	// Maximum distance the projectile can travel before becoming inactive.
	protected float maxRange;
	private float startX;
	
	///
	/// @param startX	   the x position where the projectile spawns
	/// @param startY	   the y position where the projectile spawns
	/// @param width	   the width of the projectile hitbox
	/// @param height	   the height of the projectile hitbox
	/// @param direction   the direction of travel (-1 for left, 1 for right)
	/// @param bulletspeed the speed of the projectile
	/// @param knockback   the speed of the projectile
	/// @param range       the knockback force applied on hit
	///
	public AbstractShotgunProjectile(float startX, float startY, float width, float height, int direction, float bulletSpeed, float knockback, float range, float damage) {
		super(startX, startY, width, height, direction, bulletSpeed, knockback, damage);
		this.maxRange = range;
		this.startX = startX;
	}
	
	///
	/// Overrides isActive() function from AbstractProjectile
	/// Returns false if the projectile has exceeded its maximum range
	///
	@Override
	public boolean isActive() {
		return active && !isOutOfRange();
	}
	
	///
	/// @return true if the projectile has traveled beyond {@link #maxRange}
	///
	public boolean isOutOfRange() {
        return Math.abs(position.x - startX) >= maxRange;
    }

}
