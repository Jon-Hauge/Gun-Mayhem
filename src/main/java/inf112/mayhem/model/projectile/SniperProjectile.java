package inf112.mayhem.model.projectile;

public class SniperProjectile extends AbstractProjectile {
    private static final float WIDTH = 15f;
    private static final float HEIGHT = 5f;
    private static final float BULLET_SPEED = 900f;
    private static final float KNOCKBACK = 5000f;

    public SniperProjectile(float startX, float startY, int direction) {
        super(startX, startY, WIDTH, HEIGHT, direction, BULLET_SPEED, KNOCKBACK);
    }
}