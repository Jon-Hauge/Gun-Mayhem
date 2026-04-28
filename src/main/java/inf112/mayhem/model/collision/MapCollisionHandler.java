package inf112.mayhem.model.collision;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class MapCollisionHandler {
	private TiledMapTileLayer groundLayer;
	private List<TiledMapTileLayer> platformLayers;
	
	public MapCollisionHandler(TiledMap map) {
		this.groundLayer = (TiledMapTileLayer) map.getLayers().get("ground");
		this.platformLayers = new ArrayList<>();
		for (MapLayer layer : map.getLayers()) {
			if (layer.getName().startsWith("platform") && layer instanceof TiledMapTileLayer) {
				platformLayers.add((TiledMapTileLayer) layer);
			}
		}
	}
	
	public boolean isGrounded(Rectangle bounds, float previousY, boolean ignorePlatforms) {
	    int tileY = (int) ((bounds.y - 1) / 32);
	    int tileXLeft = (int) (bounds.x / 32);
	    int tileXRight = (int) ((bounds.x + bounds.width - 1) / 32);

	    float tileTop = (tileY + 1) * 32;

	    boolean wasAboveTile = previousY >= tileTop;

	    boolean onGround = groundLayer != null &&
	            (groundLayer.getCell(tileXLeft, tileY) != null ||
	             groundLayer.getCell(tileXRight, tileY) != null);

	    boolean onPlatform = false;
	    if (!ignorePlatforms) {
			for (TiledMapTileLayer platformLayer : platformLayers) {
				if (platformLayer.getCell(tileXLeft, tileY) != null ||
					platformLayer.getCell(tileXRight, tileY) != null) {
					onPlatform = true;
					break;
				}
			}
	    }

	    return wasAboveTile && (onGround || onPlatform);
	}
	
	public float getGroundY(Rectangle bounds, boolean ignorePlatforms) {
	    int tileY = (int) ((bounds.y - 1) / 32);
	    return (tileY + 1) * 32;
	}
	

}
