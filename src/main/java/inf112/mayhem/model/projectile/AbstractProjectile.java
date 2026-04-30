package inf112.mayhem.model.projectile;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.AbstractEntity;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;

public abstract class AbstractProjectile extends AbstractEntity implements ProjectileModel {

    protected boolean active = true;
    private final int direction;
    private final float bulletSpeed;
    private final float knockBackMultiplier;

    public AbstractProjectile(int width, int height, float startX, float startY, int direction, float bulletSpeed, float knockBackMultiplier) {
        super(startX, startY, width, height);
        this.type = EntityType.PROJECTILE;
        this.state = EntityState.ACTIVE;
        this.direction = direction;
        this.bulletSpeed = bulletSpeed;
        this.knockBackMultiplier = knockBackMultiplier;
    }

    @Override
    public boolean isActive() {
    	return active;
    }

    @Override
    public void updateState() {
        this.state = active ? EntityState.ACTIVE : EntityState.HIT;
    }

    @Override
    public void update(AppWorld world, float delta) {
        position.x += bulletSpeed * direction * delta;
        updateBounds();
        if (position.x < 0 || position.x > world.width() ||
            position.y < 0 || position.y > world.height()) {
            active = false;
        }
        updateState();
    }

    @Override
    public void hit(EntityModel obj) { active = false; }

    public int getDirection() {
    	return direction;
    }
    
    public float getKnockBackMultiplier() {
    	return knockBackMultiplier;
    }
    
    public float getBulletSpeed() {
    	return bulletSpeed;
    }
}