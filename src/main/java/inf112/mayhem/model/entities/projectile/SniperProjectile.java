package inf112.mayhem.model.entities.projectile;

public class SniperProjectile extends AbstractProjectile {

    private static final float WIDTH = 15f;
    private static final float HEIGHT = 5f;
    private static final float BULLET_SPEED = 900f;
    private static final float KNOCKBACK = 3000f;
    private static final float DAMAGE = 20f;

    /// Initiates a sniper projectile from a sniper weapon.
    ///
    /// @param startX Starting horizontal coordinate of projectile
    /// @param startY Starting vertical coordinate of projectile
    /// @param direction Direction of projectile: -1 for left, 1 for right
    ///
    public SniperProjectile(float startX, float startY, int direction) {
        super(startX, startY, WIDTH, HEIGHT, direction, BULLET_SPEED, KNOCKBACK, DAMAGE);
    }
}