package inf112.mayhem.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityFactory;
import inf112.mayhem.model.EntityModel;
import inf112.mayhem.model.GameState;
import inf112.mayhem.model.PlayerModel;
import inf112.mayhem.model.collision.ProjectileCollisionHandler;
import inf112.mayhem.view.maps.TileMapHelper;
import inf112.mayhem.view.hud.HudView;
import inf112.mayhem.view.screens.GameOverView;
import inf112.mayhem.view.screens.MainMenuView;
import inf112.mayhem.view.screens.PauseView;
import inf112.mayhem.view.screens.activeGameView.ActiveGameView;

public class AppController implements ApplicationListener, IController {
    private ActiveGameView activeGameView;
    private MainMenuView mainMenuView;
    private PauseView pauseView;
    private GameOverView gameOverView;
    private HudView hudView;
    private AppWorld world;
    private boolean paused = false;
    private boolean gameOver = false;
    private PlayerModel playerModel;
    ProjectileCollisionHandler pCollision;
    private PlayerController playerController;
    
    @Override
    public void create() {

        // MODEL: our virtual world
        world = new AppWorld(1280, 768);
        
        // Last inn kartet via view
        TileMapHelper tileMapHelper = new TileMapHelper();
        TiledMap map = tileMapHelper.setupMap();
        EntityFactory fac = new EntityFactory(world, map);
        EntityFactory dummy = new EntityFactory(world, map);
        
        // Send kartet til collision handler i model
        pCollision = new ProjectileCollisionHandler(world);
        
        //PLAYER
        
        playerController = fac.spawnPlayer(500f, 400f);
        dummy.spawnPlayer(900f, 400f);
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
        hudView = new HudView(world, this);

        Gdx.graphics.setForegroundFPS(60);

        updateInputProcessor();
        
    }

    @Override
    public void dispose() {
        activeGameView.dispose();
        mainMenuView.dispose();
        pauseView.dispose();
        gameOverView.dispose();
        hudView.dispose();
    }

    @Override
    public void render() {
    	float delta = Gdx.graphics.getDeltaTime();
    	if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (paused) resume();
            else pause();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            restartGame();
        }
    	
    	switch (world.state()) {
            case MAIN_MENU -> mainMenuView.render();
            case ACTIVE -> {
                // 1. Oppdater fysikk og isGrounded
                for (EntityModel obj : world) {
                    if (!obj.isActive()) {
                    }
                    obj.update(world, delta);
                }

                // 2. Les input etter isGrounded er oppdatert
                playerController.update(delta);
                pCollision.checkCollisions();

                // 4. Tegn til slutt
                world.removeInactive();
                activeGameView.render(world, delta);
                hudView.update(playerController.getPlayer());
                hudView.render();
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
        hudView.resize(width, height);
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
