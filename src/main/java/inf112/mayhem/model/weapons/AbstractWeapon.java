package inf112.mayhem.model.weapons;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityModel;
import inf112.mayhem.model.EntityState;
import inf112.mayhem.model.EntityType;

public class AbstractWeapon implements EntityModel{
    float x;
    float y;
    float width; 
    float height;
    EntityState state;
    
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
        return height();
    }

    @Override
    public EntityType type() {
        return EntityType.WEAPON;
    }

    @Override
    public EntityState state() {
        return state;
    }

    @Override
    public void updateState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(AppWorld world, float delta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Rectangle bounds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isActive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setActive(boolean active) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDirection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void hit(EntityModel obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
