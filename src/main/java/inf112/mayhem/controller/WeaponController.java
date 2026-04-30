package inf112.mayhem.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityFactory;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class WeaponController {
	
	private static final float COOLDOWN_DURATION = 0.3f;
	private float cooldownTimer;
	
    private final AbstractWeapon weapon;
    private final AppWorld world;
	private final EntityFactory fac;
	
	public WeaponController(AbstractWeapon weapon, AppWorld world) {
		this.weapon = weapon;
		this.world = world;
		this.fac = world.getEntityFactory();
	}

	public void update(float delta) {
		if (weapon == null) return;
	    
	    if (cooldownTimer > 0) cooldownTimer -= delta;

		if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && cooldownTimer <= 0) {
	        if (weapon.shoot()) {
				fac.spawnProjectile(weapon.getCharacter());
				cooldownTimer = weapon.getFiringDelay();
			}
	    }
	    weapon.updateState();
	}

	public AbstractWeapon getWeapon() {
		return weapon;
	}
}