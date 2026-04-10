package inf112.mayhem.model.projectile;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.abstracts.AbstractProjectile;

public class ShortRangeProjectile extends AbstractProjectile {

    private float timeAlive = 0f;
    private final float MAX_TIME = 0.1f; 

    public ShortRangeProjectile(PlayerClass player) {
        super(player);
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


