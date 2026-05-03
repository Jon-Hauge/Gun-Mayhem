package inf112.mayhem.model.projectile;

public class SemiAutoShotgunProjectile extends AbstractShotgunProjectile {
	private static final float WIDTH = 10f;
    private static final float HEIGHT = 30f;
    private static final float BULLET_SPEED = 500f;
    private static final float KNOCKBACK = 2500f;
    private static final float MAX_RANGE = 50f;
    private static final float DAMAGE = 8f;

    /// Initiates a semi auto shotgun projectile from a semi auto shotgun weapon.
    ///
    /// @param startX Starting horizontal coordinate of projectile
    /// @param startY Starting vertical coordinate of projectile
    /// @param direction Direction of projectile: -1 for left, 1 for right
    ///
    public SemiAutoShotgunProjectile(float startX, float startY, int direction) {
        super(startX, startY, WIDTH, HEIGHT, direction, BULLET_SPEED, KNOCKBACK, MAX_RANGE, DAMAGE);
    }
}
