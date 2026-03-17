package inf112.mayhem.model.weapons;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityModel;
import inf112.mayhem.model.EntityState;
import inf112.mayhem.model.EntityType;

public abstract class AbstractWeapon implements EntityModel{

    private float x;
    private float y;
    private float width; 
    private float height;
    private EntityState state;
    private WeaponType weaponType;

    public AbstractWeapon(float startX, float startY, float width, float height, WeaponType weaponType, EntityState startingState) {
        this.x = startX;
        this.y = startY;
        this.height = height;
        this.width = width;
        this.weaponType = weaponType;
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
        return EntityType.WEAPON;
    }

    @Override
    public EntityState state() {
        return state;
    }

    @Override
    public void updateState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateState'");
    }

    @Override
    public void update(AppWorld world, float delta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Rectangle bounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bounds'");
    }

    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isActive'");
    }

    @Override
    public void setActive(boolean active) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setActive'");
    }

    @Override
    public int getDirection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDirection'");
    }

    @Override
    public void hit(EntityModel obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
    
    public void dropWeapon(){
        state = EntityState.DROPPED;
    }

    public void openCrate(){
        state = EntityState.OPENED_WEAPON_CRATE;
    }

    public void pickUpWeapon(){
        state = EntityState.OPENED_WEAPON_CRATE;
    }
}
