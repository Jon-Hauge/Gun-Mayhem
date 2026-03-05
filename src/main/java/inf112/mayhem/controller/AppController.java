package inf112.mayhem.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
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
    private PlayerController playerController;
    
    @Override
    public void create() { // Called at startup

        // MODEL: our virtual world
        world = new AppWorld(1280, 768);
        playerModel = new PlayerModel(123, 456);
        playerController = new PlayerController(playerModel, world);
        world.add(playerModel);

        // VIEW: graphics
        activeGameView.create(world.width(), world.height(), this);
        mainMenuView = new MainMenuView(this);
        mainMenuView.create(world.width(), world.height());
        pauseView = new PauseView(this);
        pauseView.create(world.width(), world.height());
        

        Gdx.graphics.setForegroundFPS(60);

        updateInputProcessor();
        
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
    	
    	float delta = Gdx.graphics.getDeltaTime();
    	
    	if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (paused) resume();
            else pause();
        }
    	
    	switch (world.state()) {
        case MAIN_MENU -> mainMenuView.render();
        case ACTIVE    -> {
        	playerController.update(delta);
        	playerModel.update(world, delta);
        	activeGameView.render(world);
        	}
        case PAUSED    -> {
        	activeGameView.render(world);
        	pauseView.getStage();
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

    public void startGame() {
        world.setState(GameState.ACTIVE);
        updateInputProcessor();
    }

    public void exitGame() {
        Gdx.app.exit();
    }

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
        }

    }

}
