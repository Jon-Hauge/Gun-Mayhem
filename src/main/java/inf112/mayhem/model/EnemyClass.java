package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.abstracts.AbstractCharacter;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.enums.EntityType;
import inf112.mayhem.model.interfaces.EntityModel;
import inf112.mayhem.model.weapons.AbstractWeapon;

public class EnemyClass extends AbstractCharacter {
	
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    

	public EnemyClass(float startX, float startY, TiledMap map) {
		super(startX, startY, WIDTH, HEIGHT, 3, map);
		this.type = EntityType.PLAYER;
        this.state = EntityState.IDLE;
		
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hit(EntityModel obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AppWorld world, float delta) {
		// TODO Auto-generated method stub
		
	}
	
	

}
