package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

public class MapCollisionHandler {
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer platformLayer;
	
	public MapCollisionHandler(TiledMap map) {
		this.groundLayer = (TiledMapTileLayer) map.getLayers().get("ground");
		this.platformLayer = (TiledMapTileLayer) map.getLayers().get("platform1");
	}
	
	public boolean isGrounded(Rectangle bounds, boolean isFalling) {
	    int tileY = (int) ((bounds.y - 1) / 32);
	    int tileXLeft = (int) (bounds.x / 32);
	    int tileXRight = (int) ((bounds.x + bounds.width - 1) / 32);

	    boolean onGround =
	            groundLayer.getCell(tileXLeft, tileY) != null ||
	            groundLayer.getCell(tileXRight, tileY) != null;

	    boolean onPlatform = false;

	    if (!isFalling && platformLayer != null) {
	        onPlatform =
	                platformLayer.getCell(tileXLeft, tileY) != null ||
	                platformLayer.getCell(tileXRight, tileY) != null;
	    }

	    return onGround || onPlatform;
	}
	
	public float getGroundY(Rectangle bounds) {
	    int tileY = (int) ((bounds.y - 1) / 32);
	    return (tileY + 1) * 32;
	}
	

}
