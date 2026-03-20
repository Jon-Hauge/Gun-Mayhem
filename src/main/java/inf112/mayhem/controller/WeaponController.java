package inf112.mayhem.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityFactory;
import inf112.mayhem.model.PlayerModel;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class WeaponController {
	
	private static final float SPEED = 150f;
	private static final float COOLDOWN_DURATION = 0.3f;
	private float COOLDOWN_TIMER;
	
    private PlayerModel playerModel;
    private final AppWorld world;
    private final EntityFactory fac;
	private int ammo;
	
	public WeaponController(PlayerModel playerModel, AppWorld world, EntityFactory fac) {
		this.playerModel = playerModel;
		this.world = world;
		this.fac = fac;
	}

	public void update(float delta) {
	    AbstractWeapon weapon = getWeapon();
		if (weapon == null) {return;}
	    
	    if (COOLDOWN_TIMER > 0) COOLDOWN_TIMER -= delta;

		if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && COOLDOWN_TIMER <= 0) {
	        if(weapon.shoot()){
				fac.spawnProjectile(weapon.getPlayer());
				COOLDOWN_TIMER = COOLDOWN_DURATION;
			}
	    }
	    weapon.updateState();
	}

	public AbstractWeapon getWeapon() {
		return playerModel.getEquippedWeapon();
	}

}