package inf112.mayhem.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.model.ModelObject;
import inf112.mayhem.model.PlayerModel;
import inf112.mayhem.view.screens.ActiveGameView;
import inf112.mayhem.view.screens.MainMenuView;
import inf112.mayhem.view.screens.PauseView;

public class AppController implements ApplicationListener {
    private ActiveGameView activeGameView = new ActiveGameView();
    private MainMenuView mainMenuView;
    private PauseView pauseView;
    private AppWorld world;
    private boolean paused = false;
    private PlayerModel playerModel;
    private float delta;

    @Override
    public void create() { // Called at startup

        // MODEL: our virtual world
        world = new AppWorld(1280, 768);
        playerModel = new PlayerModel(123, 456);
        world.add(playerModel);

        // VIEW: graphics
        activeGameView.create(world.width(), world.height(), this);
        mainMenuView = new MainMenuView(this);
        mainMenuView.create(world.width(), world.height());
        pauseView = new PauseView(this);
        pauseView.create(world.width(), world.height());

        Gdx.input.setInputProcessor(mainMenuView.getStage());

        Gdx.graphics.setForegroundFPS(60);

        delta = Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dispose() {
        // Called at shutdown

        // Graphics and sound resources aren't managed by Java's garbage collector, so
        // they must generally be disposed of manually when no longer needed. But,
        // any remaining resources are typically cleaned up automatically when the
        // application exits, so these aren't strictly necessary here.
        // (We might need to do something like this when loading a new game level in
        // a large game, for instance, or if the user switches to another application
        // temporarily (e.g., incoming phone call on a phone, or something).
        activeGameView.dispose();
        mainMenuView.dispose();
        pauseView.dispose();
    }

    @Override
    public void render() {
        // Called when the application should draw a new frame (many times per second).
        if (world.state() == GameState.MAIN_MENU) {
            mainMenuView.render();
            return;
        }
        // VIEW: draw stuff
        activeGameView.render(painter -> {
            for (ModelObject obj : world) {
                // commands to draw stuff
                painter.draw(obj.x(), obj.y(), obj.width(), obj.height(), obj.appearance());
            }
        });

        if(paused){
            pauseView.render();
        }

        // Don't handle input this way – use event handlers!
        if (!paused) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerModel.step(world, delta, "right");
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerModel.step(world, delta, "left");
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) playerModel.step(world, delta, "up");
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerModel.step(world, delta, "down");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // check for key press
            if (paused) {
                resume();
            } else {
                pause();
            }
        }        
    }

    @Override
    public void resize(int width, int height) {
        // Called whenever the window is resized (including with its original size at
        // startup)
        activeGameView.resize(width, height);
        mainMenuView.resize(width, height);
        pauseView.resize(width, height);
        // if appView can change the world size, we'd have to update *world* here
    }

    @Override
    public void pause() {
        paused = true;
        Gdx.input.setInputProcessor(pauseView.getStage());
    }

    @Override
    public void resume() {
        paused = false;
        Gdx.input.setInputProcessor(activeGameView.getStage());
    }

    public void startGame() {
        world.setState(GameState.ACTIVE);
        Gdx.input.setInputProcessor(activeGameView.getStage());
    }

    public void exitGame() {
        Gdx.app.exit();
    }

    public void goToMainMenu() {
        world.setState(GameState.MAIN_MENU);
        paused = false;
        Gdx.input.setInputProcessor(mainMenuView.getStage());
    }

}
