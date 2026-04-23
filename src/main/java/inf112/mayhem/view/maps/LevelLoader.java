package inf112.mayhem.view.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class LevelLoader {

    public TiledMap loadLevel(int levelNumber) {
        TileMapHelper tileMapHelper = new TileMapHelper();
        String mapPath = getMapPath(levelNumber);
        return tileMapHelper.setupMap(mapPath);
    }

    public String getMapPath(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> "maps/level1_heaven/map.tmx";
            case 2 -> "maps/level2_midnightcity/map.tmx";
            case 3 -> "maps/level3_moistymire/map.tmx";
            default -> "maps/level1_heaven/map.tmx";
        };
    }
}
