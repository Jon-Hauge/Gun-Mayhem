package inf112.mayhem.model.projectile;

public class PistolProjectile extends AbstractProjectile {
	
    private static final float WIDTH = 10f;
    private static final float HEIGHT = 7f;
    private static final float BULLET_SPEED = 500f;
    private static final float KNOCKBACK = 1000f;
    private static final float DAMAGE = 2f;

    /// Initiates a pistol projectile from a pistol weapon.
    ///
    /// @param startX Starting horizontal coordinate of projectile
    /// @param startY Starting vertical coordinate of projectile
    /// @param direction Direction of projectile: -1 for left, 1 for right
    public PistolProjectile(float startX, float startY, int direction) {
        super(startX, startY, WIDTH, HEIGHT, direction, BULLET_SPEED, KNOCKBACK, DAMAGE);
    }
}