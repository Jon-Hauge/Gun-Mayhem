package inf112.mayhem.model.projectile;

public class PumpActionShotgunProjectile extends AbstractShotgunProjectile {
    private static final float WIDTH = 10f;
    private static final float HEIGHT = 20f;
    private static final float BULLET_SPEED = 500f;
    private static final float KNOCKBACK = 1000f;
    private static final float MAX_RANGE = 20f;
    private static final float DAMAGE = 10f;

    /// Initiates a pump action shotgun projectile from a pump action shotgun weapon.
    ///
    /// @param startX Starting horizontal coordinate of projectile
    /// @param startY Starting vertical coordinate of projectile
    /// @param direction Direction of projectile: -1 for left, 1 for right
    ///
    public PumpActionShotgunProjectile(float startX, float startY, int direction) {
        super(startX, startY, WIDTH, HEIGHT, direction, BULLET_SPEED, KNOCKBACK, MAX_RANGE, DAMAGE);
    }
}
