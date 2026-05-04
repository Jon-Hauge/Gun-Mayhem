package inf112.mayhem.view.maps;

import java.util.List;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class LevelLoader {

    /// Loads a given level based on the level number.
    ///
    /// @param levelNumber Number of level to load in
    ///
    public TiledMap loadLevel(int levelNumber) {
        TileMapHelper tileMapHelper = new TileMapHelper();
        String mapPath = getMapPath(levelNumber);
        return tileMapHelper.setupMap(mapPath);
    }

    ///
    /// @param levelNumber Number of level to find path to
    /// @return String of path to load in
    ///
    public String getMapPath(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> "maps/level1_heaven/map.tmx";
            case 2 -> "maps/level2_midnightcity/map.tmx";
            case 3 -> "maps/level3_moistymire/map.tmx";
            default -> "maps/level1_heaven/map.tmx";
        };
    }

    ///
    /// @param levelNumber Number of level to find background for
    /// @return list of paths to backgrounds for levels
    ///
    public List<String> getMapBackgroundPaths(int levelNumber){
    return switch (levelNumber) {
            case 1 -> List.of(
                "maps/level1_heaven/bg1.png",
                "maps/level1_heaven/bg2.png",
                "maps/level1_heaven/bg3.png",
                "maps/level1_heaven/bg4.png"
            );
            case 2 -> List.of(
                "maps/level2_midnightcity/bg1.png",
                "maps/level2_midnightcity/bg2.png",
                "maps/level2_midnightcity/bg3.png",
                "maps/level2_midnightcity/bg4.png",
                "maps/level2_midnightcity/bg5.png",
                "maps/level2_midnightcity/bg6.png"
            );
            case 3 -> List.of(
                "maps/level3_moistymire/bg1.png",
                "maps/level3_moistymire/bg2.png",
                "maps/level3_moistymire/bg3.png",
                "maps/level3_moistymire/bg4.png",
                "maps/level3_moistymire/bg5.png"
            );
        default -> throw new IllegalStateException("Unexpected value: " + (levelNumber));
        };
    }
}
