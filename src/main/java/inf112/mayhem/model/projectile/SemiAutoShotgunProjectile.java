package inf112.mayhem.model.projectile;

public class SemiAutoShotgunProjectile extends AbstractProjectile {
	private static final float WIDTH = 10f;
    private static final float HEIGHT = 20f;
    private static final float BULLET_SPEED = 500f;
    private static final float KNOCKBACK = 2000f;

    public SemiAutoShotgunProjectile(float startX, float startY, int direction) {
        super(startX, startY, WIDTH, HEIGHT, direction, BULLET_SPEED, KNOCKBACK);
    }
}
