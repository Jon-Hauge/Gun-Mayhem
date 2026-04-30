package inf112.mayhem.model.projectile;

public class SniperProjectile extends AbstractProjectile {
    private static final int WIDTH = 15;
    private static final int HEIGHT = 5;
    private static final float BULLET_SPEED = 900f;
    private static final float KNOCKBACK = 5000f;

    public SniperProjectile(float startX, float startY, int direction) {
        super(WIDTH, HEIGHT, startX, startY, direction, BULLET_SPEED, KNOCKBACK);
    }
}