package inf112.mayhem.model.projectile;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class ShortRangeProjectile extends AbstractProjectile {

    private float timeAlive = 0f;
    private final float MAX_TIME = 0.1f; 
    protected Rectangle weaponsBounds = new Rectangle();

    public ShortRangeProjectile(AbstractWeapon weapon) {
        super(weapon);
        updateBounds();
    }

    @Override
    public void updateBounds() {}

    @Override
    public void update(AppWorld world, float delta) {
        timeAlive += delta;
        
        if (timeAlive >= MAX_TIME) {
            active = false;
        }
        
        updateBounds();
        updateState();
    }
    }


