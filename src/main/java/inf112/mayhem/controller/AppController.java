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
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.view.maps.TileMapHelper;
import inf112.mayhem.view.screens.GameOverView;
import inf112.mayhem.view.screens.MainMenuView;
import inf112.mayhem.view.screens.PauseView;
import inf112.mayhem.view.screens.VictoryView;
import inf112.mayhem.view.screens.activeGameView.ActiveGameView;
import inf112.mayhem.view.screens.hud.HudView;

public class AppController implements ApplicationListener, IController {
    private ActiveGameView activeGameView;
    private MainMenuView mainMenuView;
    private PauseView pauseView;
    private GameOverView gameOverView;
    private VictoryView victoryView;
    private HudView hudView;
    private AppWorld world;
    private boolean paused = false;
    private boolean gameOver = false;
    private PlayerModel playerModel;
    private PlayerModel enemyModel;
    private ProjectileCollisionHandler pCollision;
    private PlayerController playerController;
    private WeaponController weaponController;
    private AbstractWeapon weapon;
    
    @Override
    public void create() {

        // MODEL: our virtual world
        world = new AppWorld(1280, 768);
        
        // Last inn kartet via view
        TileMapHelper tileMapHelper = new TileMapHelper();
        TiledMap map = tileMapHelper.setupMap();
        EntityFactory fac = new EntityFactory(world, map);
        
        // Send kartet til collision handler i model
        pCollision = new ProjectileCollisionHandler(world);
        
        //PLAYER
        playerController = fac.spawnPlayer(500f, 400f);
        playerModel = playerController.getPlayer();
        weaponController = fac.spawnBaseWeapon(500f, 400f, playerController);        
        // Spawn dummies
        PlayerController enemyController = fac.spawnPlayer(900f, 400f);
        enemyModel = enemyController.getPlayer();

        // VIEW: graphics
        activeGameView = new ActiveGameView(world, this);
        mainMenuView = new MainMenuView(world, this);
        pauseView = new PauseView(world, this);
        gameOverView = new GameOverView(world, this);
        victoryView = new VictoryView(world, this);
        hudView = new HudView(world, this, weaponController.getWeapon());

        Gdx.graphics.setForegroundFPS(60);

        updateInputProcessor();
        
    }

    @Override
    public void dispose() {
        activeGameView.dispose();
        mainMenuView.dispose();
        pauseView.dispose();
        gameOverView.dispose();
        victoryView.dispose();
        hudView.dispose();
    }

    @Override
    public void render() {
    	float delta = Gdx.graphics.getDeltaTime();
    	if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (world.state() == GameState.ACTIVE || world.state() == GameState.PAUSED) {
                if (paused) resume();
                else pause();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (world.state() == GameState.ACTIVE) {
                restartGame();
            }
        }
    	
    	switch (world.state()) {
            case MAIN_MENU -> mainMenuView.render();
            case ACTIVE -> {
                // 1. Oppdater fysikk og isGrounded
                for (EntityModel obj : world) {
                    obj.update(world, delta);
                }

                // 2. Les input etter isGrounded er oppdatert
                playerController.update(delta);
                weaponController.update(delta);
                pCollision.checkCollisions();

                // 4. Tegn til slutt
                world.removeInactive();
                activeGameView.render(world, delta);
                hudView.update(playerModel);
                hudView.render();

                if (playerModel.lives() <= 0) {
                    world.setState(GameState.GAME_OVER);
                    updateInputProcessor();
                } else if (enemyModel.lives() <= 0) {
                    world.setState(GameState.VICTORY);
                    updateInputProcessor();
                }
            }
            case PAUSED    -> {
                activeGameView.render(world, delta);
                hudView.render();
                pauseView.render();
            }
            case GAME_OVER -> {
                activeGameView.render(world, delta);
                hudView.render();
                gameOverView.render();
            }
            case VICTORY -> {
                activeGameView.render(world, delta);
                hudView.render();
                victoryView.render();
            }
    	}
    	
    }

    @Override
    public void resize(int width, int height) {
        activeGameView.resize(width, height);
        mainMenuView.resize(width, height);
        pauseView.resize(width, height);
        gameOverView.resize(width, height);
        victoryView.resize(width, height);
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
        if (!skipCreate) {
            create();
        }
        world.setState(GameState.ACTIVE);
        playerModel.setLives(3);
        enemyModel.setLives(3);
        updateInputProcessor();
    }

    @Override
    public void exitGame() {
        Gdx.app.exit();
    }

    @Override
    public void restartGame() {
        if (!skipCreate) {
            create();
        }
        world.setState(GameState.ACTIVE);
        gameOver = false;
        playerModel.setLives(3);
        enemyModel.setLives(3);
        updateInputProcessor();
    }
    
    // For testing purposes
    protected boolean skipCreate = false;

    @Override
    public void goToMainMenu() {
        world.setState(GameState.MAIN_MENU);
        paused = false;
        updateInputProcessor();
    }

    // TODO: create next level function
    @Override
    public void nextLevel() {
        restartGame();
    }
    
    private void updateInputProcessor() {
        switch (world.state()) {
            case MAIN_MENU -> Gdx.input.setInputProcessor(mainMenuView.getStage());
            case ACTIVE    -> Gdx.input.setInputProcessor(activeGameView.getStage());
            case PAUSED    -> Gdx.input.setInputProcessor(pauseView.getStage());
            case GAME_OVER ->  Gdx.input.setInputProcessor(gameOverView.getStage());
            case VICTORY   -> Gdx.input.setInputProcessor(victoryView.getStage());
        }

    }

}
