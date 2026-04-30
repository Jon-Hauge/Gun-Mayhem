package inf112.mayhem.model.projectile;

public class PistolProjectile extends AbstractProjectile {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 7;
    private static final float BULLET_SPEED = 500f;
    private static final float KNOCKBACK = 2500f;

    public PistolProjectile(float startX, float startY, int direction) {
        super(WIDTH, HEIGHT, startX, startY, direction, BULLET_SPEED, KNOCKBACK);
    }
}