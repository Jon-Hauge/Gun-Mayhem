package inf112.mayhem.model.projectile;

import com.badlogic.gdx.math.Vector2;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class LongRangeProjectile extends AbstractProjectile {
    private final Vector2 size = new Vector2(10, 7);

    public LongRangeProjectile(AbstractWeapon weapon) {
        super(weapon);
        updateBounds();
    }

    @Override
    public void update(AppWorld world, float delta) {
        position.x += getWeapon().getBulletSpeed() * this.getDirection() * delta;
        updateBounds();

        if (position.x + size.x < 0 ||
                position.x > world.width() ||
                position.y + size.y < 0 ||
                position.y > world.height()) {
            active = false;
        }
        updateState();
    }

    @Override
    public void updateBounds() {
        bounds.set(position.x, position.y, size.x, size.y);
    }
}