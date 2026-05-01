package inf112.mayhem.model.projectile;

/// 
/// Projectile fired by the SemiAutoShotgun.
/// Has a short range and wide hitbox to simulate a shotgun blast.
///
public class SemiAutoShotgunProjectile extends AbstractShotgunProjectile {
	private static final float WIDTH = 35f;
    private static final float HEIGHT = 40f;
    private static final float BULLET_SPEED = 500f;
    private static final float KNOCKBACK = 2000f;
    private static final float MAX_RANGE = 15f;
    
    /// 
    /// @param startX	 the x position where the projectile spawns
    /// @param startY	 the y position where the projectile spawns
    /// @param direction the direction of travel (-1 for left, 1 for right)
    ///
    public SemiAutoShotgunProjectile(float startX, float startY, int direction) {
        super(startX, startY, WIDTH, HEIGHT, direction, BULLET_SPEED, KNOCKBACK, MAX_RANGE);
    }
}
