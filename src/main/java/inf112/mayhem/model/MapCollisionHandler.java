package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

public class MapCollisionHandler {
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer platformLayer1;
	
	public MapCollisionHandler(TiledMap map) {
		this.groundLayer = (TiledMapTileLayer) map.getLayers().get("ground");
		this.platformLayer1 = (TiledMapTileLayer) map.getLayers().get("platform1");
	}
	
	public boolean isGrounded(Rectangle bounds, float velocityY) {
	    if (velocityY > 0) return false; // hopper opp, ikke grounded

	    int tileX = (int) (bounds.x / 32);
	    int tileY = (int) (bounds.y / 32);

	    // Sjekk ground-laget (solid fra alle sider)
	    Cell groundCell = groundLayer.getCell(tileX, tileY);
	    if (groundCell != null) return true;

	    // Sjekk platform-laget (one-way, bare fra toppen)
	    Cell platformCell = platformLayer1.getCell(tileX, tileY);
	    if (platformCell != null) return true;

	    return false;
	}
	

}
