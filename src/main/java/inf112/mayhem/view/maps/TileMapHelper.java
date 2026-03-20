package inf112.mayhem.view.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMapHelper {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public TiledMap setupMap(String mapPath) {
        map = new TmxMapLoader().load(mapPath);
        return map;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
    	renderer = new OrthogonalTiledMapRenderer(map);
        return renderer;
    }
}
