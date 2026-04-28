package inf112.mayhem.model.entities;

import com.badlogic.gdx.math.Rectangle;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class WeaponCrate implements EntityModel {
    private float x,y;
    private boolean opened = false;
    private AbstractWeapon hiddenWeapon;
    private Rectangle bounds;
    private float width = 50f;
    private float height = 50f;

    public WeaponCrate(float x, float y, AbstractWeapon weapon) {
        this.x = x;
        this.y = y;
        this.hiddenWeapon = weapon;
    }

    public void open(AppWorld world) {
        if (!opened) {
            opened = true;
            hiddenWeapon.dropWeapon();
            world.add(hiddenWeapon);
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
            return EntityState.UNKNOWN_WEAPON_CRATE;
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
