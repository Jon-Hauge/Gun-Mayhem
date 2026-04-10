package inf112.mayhem.model.weapons;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.enums.EntityType;
import inf112.mayhem.model.interfaces.EntityModel;
import inf112.mayhem.view.screens.activeGameView.WeaponView;

public abstract class AbstractWeapon implements EntityModel {

    private float x;
    private float y;
    private float width; 
    private float height;
    private EntityState state;
    private WeaponType weaponType;
    private int maxAmmo; //set ammo to -1 for inf
    private int currentAmmo;
    private float firingDelay;
    protected PlayerClass player;
    private WeaponView weaponView;

    public AbstractWeapon(float startX, float startY, PlayerClass player, float width, float height, WeaponType weaponType, EntityState startingState, int startingAmmo, float firingDelay) {
        this.x = startX;
        this.y = startY;
        this.player = player;
        this.height = height;
        this.width = width;
        this.weaponType = weaponType;
        this.maxAmmo = startingAmmo;
        this.currentAmmo = startingAmmo;
        this.firingDelay = firingDelay;
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
        int i = 1; //MIDDLERTIDIG
    }

    @Override
    public void update(AppWorld world, float delta) {
        int i = 1; //MIDDLERTIDIG
    }

    @Override
    public Rectangle bounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bounds'");
    }

    @Override
    public boolean isActive() {
        return true; //MIDDLERTIDLIG
    }

    @Override
    public void updateBounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setActive'");
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

    public WeaponType getWeaponType(){
        return weaponType;
    }

    public boolean shoot(){
        if(currentAmmo == 0){
            return false;
        }
        if(currentAmmo < 0){
            return true;
        }
        else {
            currentAmmo -= 1;
            return true;
        }
    }

    public void pickUpAmmo(){
        currentAmmo = maxAmmo;
    }

    public float getFiringDelay(){
        return firingDelay;
    }

    public int getCurrentAmmo(){
        return currentAmmo;
    }

    public int getMaxAmmo(){
        return maxAmmo;
    }

    public PlayerClass getPlayer(){
        return player;
    }

    public WeaponView getWeaponView() {
        return weaponView;
    }

    public void setWeaponView(WeaponView weaponView) {
        this.weaponView = weaponView;
    }
}
