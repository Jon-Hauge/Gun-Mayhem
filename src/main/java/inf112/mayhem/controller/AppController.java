package inf112.mayhem.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityFactory;
import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.collision.ProjectileCollisionHandler;
import inf112.mayhem.model.enums.GameState;
import inf112.mayhem.model.interfaces.EntityModel;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.PistolWeapon;
import inf112.mayhem.model.weapons.SniperWeapon;
import inf112.mayhem.view.maps.TileMapHelper;
import inf112.mayhem.view.screens.CampaignView;
import inf112.mayhem.view.screens.GameOverView;
import inf112.mayhem.view.screens.MainMenuView;
import inf112.mayhem.view.screens.PauseView;
import inf112.mayhem.view.screens.VictoryView;
import inf112.mayhem.view.screens.activeGameView.ActiveGameView;
import inf112.mayhem.view.screens.activeGameView.WeaponView;
import inf112.mayhem.view.screens.activeGameView.WeaponViewFactory;
import inf112.mayhem.view.screens.hud.HudView;

public class AppController implements ApplicationListener, IController {
    private ActiveGameView activeGameView;
    private MainMenuView mainMenuView;
    private PauseView pauseView;
    private GameOverView gameOverView;
    private VictoryView victoryView;
    private HudView hudView;
    private CampaignView campaignView;
    private AppWorld world;
    private PlayerClass playerModel;
    private PlayerClass enemyModel;
    private ProjectileCollisionHandler pCollision;
    private PlayerController playerController;
    private WeaponController weaponController;
    private AbstractWeapon pistol;
    private AbstractWeapon sniper;

    private int currentLevel = 1;
    private int unlockedLevels = 1;

    @Override
    public void create() {
        createLevel(currentLevel);
    }

    public void createLevel(int levelNumber) {
        currentLevel = levelNumber;
        // MODEL: our virtual world
        world = new AppWorld(1280, 768);

        // Load in map and factories
        TileMapHelper tileMapHelper = new TileMapHelper();
        String mapPath = getMapPath(levelNumber);
        TiledMap map = tileMapHelper.setupMap(mapPath);
        EntityFactory entityFactory = new EntityFactory(world, map);
        WeaponViewFactory weaponViewFactory = new WeaponViewFactory();

        // Send kartet til collision handler i model
        pCollision = new ProjectileCollisionHandler(world);

        //PLAYER
        playerController = entityFactory.spawnPlayer(500f, 400f);
        playerModel = playerController.getPlayer();

        // Spawn dummies
        PlayerController enemyController = entityFactory.spawnPlayer(900f, 400f);
        enemyModel = enemyController.getPlayer();

        // Weapons
        weaponController = new WeaponController(playerModel, world, entityFactory);

        this.pistol = new PistolWeapon(500f, 400f, playerModel);
        this.sniper = new SniperWeapon(500f, 400f, playerModel);

        pistol.setWeaponView(weaponViewFactory.createWeaponView(pistol.getWeaponType()));
        sniper.setWeaponView(weaponViewFactory.createWeaponView(sniper.getWeaponType()));

        playerModel.equipWeapon(pistol);

        // VIEW: graphics
        activeGameView = new ActiveGameView(world, this);
        mainMenuView = new MainMenuView(world, this);
        pauseView = new PauseView(world, this);
        gameOverView = new GameOverView(world, this);
        victoryView = new VictoryView(world, this);
        campaignView = new CampaignView(world, this);
        hudView = new HudView(world, this, playerModel);

        Gdx.graphics.setForegroundFPS(60);

        updateInputProcessor();
        
    }

    private String getMapPath(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> "maps/level1_heaven/map.tmx";
            case 2 -> "maps/level2_midnightcity/map.tmx";
            case 3 -> "maps/level3_moistymire/map.tmx";
            default -> "maps/level1_heaven/map.tmx";
        };
    }

    @Override
    public void dispose() {
        activeGameView.dispose();
        mainMenuView.dispose();
        pauseView.dispose();
        gameOverView.dispose();
        victoryView.dispose();
        campaignView.dispose();
        hudView.dispose();
    }

    @Override
    public void render() {
    	float delta = Gdx.graphics.getDeltaTime();
    	if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (world.state() == GameState.ACTIVE && world.state() == GameState.PAUSED) {
                resume();
            } else {
                pause();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (world.state() == GameState.ACTIVE) {
                restartGame();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            playerModel.cycleWeapon(pistol, sniper);
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
                    if (currentLevel == unlockedLevels && unlockedLevels < 3) {
                        unlockedLevels++;
                    }

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


            case CAMPAIGN -> {
                campaignView.render();
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
        campaignView.resize(width, height);
        hudView.resize(width, height);
        // if appView can change the world size, we'd have to update *world* here
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
    public void startGame() {
        if (!skipCreate) {
            createLevel(1);
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
            createLevel(currentLevel);
        }
        world.setState(GameState.ACTIVE);
        playerModel.setLives(3);
        enemyModel.setLives(3);
        updateInputProcessor();
    }
    
    // For testing purposes
    protected boolean skipCreate = false;

    @Override
    public void goToMainMenu() {
        world.setState(GameState.MAIN_MENU);
        updateInputProcessor();
    }

    // TODO: create next level function
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
        campaignView.rebuildUI();
        updateInputProcessor();
    }

    @Override
    public void startLevel(int levelNumber) {
        if (!skipCreate) {
            createLevel(levelNumber);
        }
        world.setState(GameState.ACTIVE);
        playerModel.setLives(3);
        enemyModel.setLives(3);
        updateInputProcessor();
    }

    @Override
    public boolean isLevelUnlocked(int levelNumber) {
        return levelNumber <= unlockedLevels;
    }
    
    private void updateInputProcessor() {
        switch (world.state()) {
            case MAIN_MENU -> Gdx.input.setInputProcessor(mainMenuView.getStage());
            case ACTIVE    -> Gdx.input.setInputProcessor(activeGameView.getStage());
            case PAUSED    -> Gdx.input.setInputProcessor(pauseView.getStage());
            case GAME_OVER -> Gdx.input.setInputProcessor(gameOverView.getStage());
            case VICTORY   -> Gdx.input.setInputProcessor(victoryView.getStage());
            case CAMPAIGN  -> Gdx.input.setInputProcessor(campaignView.getStage());
            default -> Gdx.input.setInputProcessor(null);
        }

    }

}
