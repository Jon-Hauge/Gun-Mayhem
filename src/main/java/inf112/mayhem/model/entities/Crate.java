package inf112.mayhem.model.entities;

import com.badlogic.gdx.math.Rectangle;
import inf112.mayhem.model.AppWorld;

public class Crate implements EntityModel {
    private float x,y;
    private boolean opened = false;
    private PowerUp powerUp;
    private Rectangle bounds;
    private float width = 50f;
    private float height = 50f;

    /// Initiates a crate entity containing powerUps.
    ///
    /// @param x Horizontal coordinate of character
    /// @param y Vertical coordinate of character
    /// @param powerUp PowerUp contained within crate
    ///
    public Crate(float x, float y, PowerUp powerUp) {
        this.x = x;
        this.y = y;
        this.powerUp = powerUp;
    }

    /// Opens crate revealing powerUp inside.
    ///
    /// @param world Gameworld crate is opened in
    ///
    public void open(AppWorld world) {
        if (!opened) {
            opened = true;
            // Activate powerUp
            world.add(powerUp);
        }
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public float height() {
        return height;
    }

    @Override
    public EntityType type() {
        return EntityType.CRATE;
    }

    @Override
    public EntityState state() {
        if (!opened) {
            return EntityState.UNOPENED_WEAPON_CRATE;
        } else {
            return EntityState.OPENED_WEAPON_CRATE;
        }
    }

    @Override
    public void updateBounds() {
        bounds.set(x, y, width, height);
    }

    @Override
    public Rectangle bounds() {
        return bounds;
    }

    @Override
    public boolean isActive() {
        return !opened;
    }

    @Override
    public void update(AppWorld world, float delta) {}

    @Override
    public void updateState() {}
}
