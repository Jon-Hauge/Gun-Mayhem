package inf112.mayhem.model.entities;

import com.badlogic.gdx.math.Rectangle;
import inf112.mayhem.model.AppWorld;

public class PowerUp implements EntityModel {

    @Override
    public float x() {
        return 0;
    }

    @Override
    public float y() {
        return 0;
    }

    @Override
    public float width() {
        return 0;
    }

    @Override
    public float height() {
        return 0;
    }

    @Override
    public EntityType type() {
        return null;
    }

    @Override
    public EntityState state() {
        return null;
    }

    @Override
    public void updateState() {

    }

    @Override
    public void updateBounds() {

    }

    @Override
    public void update(AppWorld world, float delta) {

    }

    @Override
    public Rectangle bounds() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
