package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import inf112.mayhem.model.collision.MapCollisionHandler;
import inf112.mayhem.model.entities.EntityFactory;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.characters.EnemyClass;
import inf112.mayhem.model.entities.characters.PlayerClass;

public interface IWorld {

    /// Loads the current level into the gameworld.
    /// Which level is chosen is given by the level-number
    ///
    /// @param levelNumber Number of current level to load
    ///
    void loadLevel(int levelNumber);

    ///
    /// @return width of gameworld
    ///
    double width();

    ///
    /// @return height of gameworld
    ///
    double height();
    ///
    /// @return current time in the gameworld
    ///
    double currentTime();

    /// Adds objects to the gameworld provided they are not null
    ///
    /// @param obj Object to add to gameworld
    ///
    void add(EntityModel obj);

    ///
    /// @return State of the gameworld
    ///
    GameState state();

    ///
    /// @return player in the gameworld
    ///
    PlayerClass getPlayerClass();

    ///
    /// @return enemy in the gameworld
    ///
    EnemyClass getEnemyClass();

    ///
    /// @return map used in the gameworld
    ///
    TiledMap getMap();

    ///
    /// @return collision handler used between map and entities
    ///
    MapCollisionHandler getMapCollisionHandler();

    /// Sets state of the gameworld
    ///
    /// @param state State to set the gameworld to
    ///
    void setState(GameState state);

    ///
    /// @return Entity factory used in gameworld
    ///
    EntityFactory getEntityFactory();

    /// Removes inactive objects from gameworld
    ///
    void removeInactive();

    /// Updates the gameworld each gametic, i.e. since last update.
    ///
    /// @param delta One gametic
    ///
    void update(float delta);

    /// Evaluates state to check for victory or loss
    ///
    void evaluateState();

    ///
    /// @return Current level count, i.e. levels unlocked so far
    ///
    int updateLevelCount(int currentLevel, int unlockedLevels);

    /// Reset gameworld.
    ///
    void reset();

    /// Shows a quick popup of the current activated powerUp.
    ///
    /// @param message Name of powerUp to be displayed
    ///
    void showPopup(String message);

    ///
    /// @return Message of currently picked up powerUp
    ///
    String getPopupMessage();

}
