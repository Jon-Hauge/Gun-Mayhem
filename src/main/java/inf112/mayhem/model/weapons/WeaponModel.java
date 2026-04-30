package inf112.mayhem.model.weapons;

public interface WeaponModel {
	
	public boolean shoot();
	public float getFiringDelay();
	public int getCurrentAmmo();
	public int getMaxAmmo();
	public void dropWeapon();
	public void openCrate();
	public void pickUpWeapon();
	public WeaponType getWeaponType();
	
}
