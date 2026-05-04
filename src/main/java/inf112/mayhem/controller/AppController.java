package inf112.mayhem.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.view.screens.ScreenManager;

/// Creates a controller for the current instance of the app, allowing
/// global navigation, event coordination and executing tasks in the model.
///
public class AppController implements ApplicationListener, IController {
    private AppWorld world;
    private ScreenManager screenManager;
    private PlayerController playerController;
    private int currentLevel = 1;
    private int unlockedLevels = 1;

    @Override
    public void create() {
        createLevel(currentLevel);
    }

    private void createLevel(int levelNumber) {
        currentLevel = levelNumber;
        world = new AppWorld(1280, 768);
        world.loadLevel(levelNumber);

        playerController = new PlayerController(world.getPlayerClass(), world);

        dispose();

        screenManager = new ScreenManager(world, this, world.getPlayerClass());
        screenManager.setupWeaponViews(world.getPlayerClass(), world.getEnemyClass());

        Gdx.graphics.setForegroundFPS(60);
        updateInputProcessor();
    }

    @Override
    public void dispose() {
        if(screenManager != null){
            screenManager.dispose();
        }
    }
    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        handleInput();

        if (world.state() == GameState.ACTIVE || world.state() == GameState.VICTORY || world.state() == GameState.GAME_OVER) {
            if (world.state() == GameState.ACTIVE) {
                world.update(delta);
                playerController.update(delta);
                world.evaluateState();
            }
            unlockedLevels = world.updateLevelCount(currentLevel, unlockedLevels);
        }

        screenManager.render(world, delta);
        updateInputProcessor();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (world.state() == GameState.PAUSED) {
                resume();
            } else if (world.state() == GameState.ACTIVE) {
                pause();
            }
        }
    }

    @Override
    public int getCurrentLevelNumber(){
        return currentLevel;
    }

    @Override
    public void resize(int width, int height) {
        screenManager.resize(width, height);
    }

    @Override
    public void pause() {
        world.setState(GameState.PAUSED);
        updateInputProcessor();
    }

    @Override
    public void resume() {
        world.setState(GameState.ACTIVE);
        updateInputProcessor();
    }

    @Override
    public void exitGame() {
        Gdx.app.exit();
    }

    @Override
    public void restartGame() {
        if (!skipCreate) {
            createLevel(currentLevel);
        }
        world.reset();
        updateInputProcessor();
    }

    @Override
    public void goToMainMenu() {
        world.setState(GameState.MAIN_MENU);
        updateInputProcessor();
    }

    @Override
    public void goToControls() {
        world.setState(GameState.CONTROLS);
        updateInputProcessor();
    }

    @Override
    public void nextLevel() {
        if (currentLevel < 3) {
            startLevel(currentLevel + 1);
        } else {
            goToMainMenu();
        }
    }

    @Override
    public void campaign() {
        world.setState(GameState.CAMPAIGN);
        screenManager.rebuildUI();
        updateInputProcessor();
    }

    @Override
    public void startLevel(int levelNumber) {
        currentLevel = levelNumber;
        if (!skipCreate) {
            createLevel(levelNumber);
        }
        world.reset();
        updateInputProcessor();
    }

    @Override
    public boolean isLevelUnlocked(int levelNumber) {
        return levelNumber <= unlockedLevels;
    }

    private void updateInputProcessor() {
        screenManager.updateInputProcessor(world.state());
    }

    /// Used for testing
    ///
    protected boolean skipCreate = false;

}
