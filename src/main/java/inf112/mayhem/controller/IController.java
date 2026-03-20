package inf112.mayhem.controller;

public interface IController {
    /// Creates gameworld by initiating model and view.
    /// Is called at startup and after restarting.
    ///
    void create();

    /// Disposes assets to ensure resources are cleaned up automatically when application exits.
    /// Called at shutdown.
    ///
    void dispose();

    /// Renders the gameworld.
    /// Is called several times per second.
    ///
    void render();

    /// Resizes screen in accordance with resolution.
    /// Called whenever the window is resized, including startup.
    ///
    /// @param width width of the gameworld
    /// @param height height of the gameworld
    void resize(int width, int height);

    /// Pauses the game
    ///
    void pause();

    /// Resumes the game
    ///
    void resume();

    /// Starts the game
    ///
    void startGame();

    /// Exits the game
    ///
    void exitGame();

    /// Restarts the game
    ///
    void restartGame();

    /// Returns game to main menu
    void goToMainMenu();

    /// Opens the campaign screen
    void campaign();

    /// Starts a specific level
    void startLevel(int levelNumber);

    /// Checks if a level is unlocked
    boolean isLevelUnlocked(int levelNumber);

    /// Progresses to the next level
    void nextLevel();

}
