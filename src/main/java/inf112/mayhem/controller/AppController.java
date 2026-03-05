package inf112.mayhem.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.model.PlayerModel;
import inf112.mayhem.view.screens.ActiveGameView;
import inf112.mayhem.view.screens.GameOverView;
import inf112.mayhem.view.screens.MainMenuView;
import inf112.mayhem.view.screens.PauseView;

public class AppController implements ApplicationListener, IController {
    private ActiveGameView activeGameView;
    private MainMenuView mainMenuView;
    private PauseView pauseView;
    private GameOverView gameOverView;
    private AppWorld world;
    private boolean paused = false;
    private boolean gameOver = false;
    private PlayerModel playerModel;
    private PlayerController playerController;
    
    @Override
    public void create() {

        // MODEL: our virtual world
        world = new AppWorld(1280, 768);
        playerModel = new PlayerModel(123, 456);
        playerController = new PlayerController(playerModel, world);
        world.add(playerModel);

        // VIEW: graphics
        activeGameView = new ActiveGameView();
        activeGameView.create(world.width(), world.height(), this);
        mainMenuView = new MainMenuView(this);
        mainMenuView.create(world.width(), world.height());
        pauseView = new PauseView(this);
        pauseView.create(world.width(), world.height());
        gameOverView = new GameOverView(this);
        gameOverView.create(world.width(), world.height());

        Gdx.graphics.setForegroundFPS(60);

        updateInputProcessor();
        
    }

    @Override
    public void dispose() {
        activeGameView.dispose();
        mainMenuView.dispose();
        pauseView.dispose();
        gameOverView.dispose();
    }

    @Override
    public void render() {
    	if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (paused) resume();
            else pause();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            restartGame();
        }
    	
    	switch (world.state()) {
            case MAIN_MENU -> mainMenuView.render();
            case ACTIVE    -> {
                float delta = Gdx.graphics.getDeltaTime();

        playerController.update(delta);

     for (var obj : world) {
            if (!obj.isActive()) {
                world.addToRemove(obj);
            }
            obj.update(world, delta);
    }

    world.collisions();
    world.removeAllInactive();

    activeGameView.render(world);
            }
            case PAUSED    -> pauseView.render();
            case GAME_OVER -> gameOverView.render();
    	}
    	
    }

    @Override
    public void resize(int width, int height) {
        activeGameView.resize(width, height);
        mainMenuView.resize(width, height);
        pauseView.resize(width, height);
        gameOverView.resize(width, height);
        // if appView can change the world size, we'd have to update *world* here
    }

    @Override
    public void pause() {
    	world.setState(GameState.PAUSED);
        paused = true;
        updateInputProcessor();
    }

    @Override
    public void resume() {
    	world.setState(GameState.ACTIVE);
        paused = false;
        updateInputProcessor();
    }

    @Override
    public void startGame() {
        world.setState(GameState.ACTIVE);
        updateInputProcessor();
    }

    @Override
    public void exitGame() {
        Gdx.app.exit();
    }

    @Override
    public void restartGame() {
        create();
        world.setState(GameState.ACTIVE);
        gameOver = false;
        updateInputProcessor();
    }

    @Override
    public void goToMainMenu() {
        world.setState(GameState.MAIN_MENU);
        paused = false;
        updateInputProcessor();
    }
    
    private void updateInputProcessor() {
        switch (world.state()) {
            case MAIN_MENU -> Gdx.input.setInputProcessor(mainMenuView.getStage());
            case ACTIVE    -> Gdx.input.setInputProcessor(activeGameView.getStage());
            case PAUSED    -> Gdx.input.setInputProcessor(pauseView.getStage());
            case GAME_OVER ->  Gdx.input.setInputProcessor(gameOverView.getStage());
        }

    }

}
