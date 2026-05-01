package inf112.mayhem.model.projectile;

/// 
/// Projectile fired by the PumpActionShotgun.
/// Has a short range and wide hitbox to simulate a shotgun blast.
///
public class PumpActionShotgunProjectile extends AbstractShotgunProjectile {
	
    private static final float WIDTH = 35f;
    private static final float HEIGHT = 40f;
    private static final float BULLET_SPEED = 500f;
    private static final float KNOCKBACK = 2500f;
    private static final float MAX_RANGE = 20f;
    
    /// 
    ///@param startX	 the x position where the projectile spawns
    /// @param startY	 the y position where the projectile spawns
    /// @param direction the direction of travel (-1 for left, 1 for right)
    ///
    public PumpActionShotgunProjectile(float startX, float startY, int direction) {
        super(startX, startY, WIDTH, HEIGHT, direction, BULLET_SPEED, KNOCKBACK, MAX_RANGE);
    }
}
