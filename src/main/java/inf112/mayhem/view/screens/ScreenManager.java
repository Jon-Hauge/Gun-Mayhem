package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;

import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;
import inf112.mayhem.view.screens.activeGameView.ActiveGameView;
import inf112.mayhem.view.screens.activeGameView.GameSounds;
import inf112.mayhem.view.screens.activeGameView.WeaponView;
import inf112.mayhem.view.screens.hud.HudView;

public class ScreenManager {
    private final ActiveGameView activeGameView;
    private final MainMenuView mainMenuView;
    private final PauseView pauseView;
    private final GameOverView gameOverView;
    private final VictoryView victoryView;
    private final HudView hudView;
    private final CampaignView campaignView;
    private final ControlsView controlsView;
    private final AbstractCharacter player;
    private final AppController controller;
    private GameState currentState;
    private final GameSounds gameSounds = new GameSounds();

    /// Initiates a screen manager with the given world and controller.
    /// Is responsible for displaying the different views correctly.
    ///
    /// @param world The game world
    /// @param controller The application controller for handling state transitions
    /// @param player Player to display hud for
    ///
    public ScreenManager(AppWorld world, AppController controller, AbstractCharacter player) {
        this.player = player;
        this.activeGameView = new ActiveGameView(world, controller);
        this.mainMenuView = new MainMenuView(world, controller);
        this.pauseView = new PauseView(world, controller);
        this.gameOverView = new GameOverView(world, controller);
        this.victoryView = new VictoryView(world, controller);
        this.campaignView = new CampaignView(world, controller);
        this.hudView = new HudView(world, controller, player);
        this.controlsView = new ControlsView(world, controller);
        this.controller = controller;

        currentState = world.state();
        AbstractScreenView initial = getScreen(currentState);
        if (initial != null && initial.usesVideo()) {
            initial.show();
            musicHandler(currentState);
        }
    }

    /// Disposes of all elements in stage.
    ///
    public void dispose() {
        activeGameView.dispose();
        mainMenuView.dispose();
        pauseView.dispose();
        gameOverView.dispose();
        victoryView.dispose();
        campaignView.dispose();
        hudView.dispose();
        controlsView.dispose();
        gameSounds.dispose();
    }

    /// Resizes screen to account for changes in resolution
    ///
    /// @param width width of the resized screen
    /// @param height height of the resized screen
    ///
    public void resize(int width, int height) {
        activeGameView.resize(width, height);
        mainMenuView.resize(width, height);
        pauseView.resize(width, height);
        gameOverView.resize(width, height);
        victoryView.resize(width, height);
        campaignView.resize(width, height);
        hudView.resize(width, height);
        controlsView.resize(width, height);
    }

    /// Renders the game world.
    /// Happens multiple times per second.
    ///
    public void render(AppWorld world, float delta) {
        if (world.state() != currentState) {
            handleStateChange(currentState, world.state());
            currentState = world.state();
            musicHandler(currentState);
        }

        switch (world.state()) {
            case MAIN_MENU -> {
                mainMenuView.render();
            }
            case ACTIVE -> {
                activeGameView.render(world, delta);
                hudView.update(player);
                hudView.render();
            }
            case PAUSED -> {
                activeGameView.render(world, 0);
                hudView.render();
                pauseView.render();
            }
            case GAME_OVER -> {
                activeGameView.render(world, 0);
                hudView.render();
                gameOverView.render();
            }
            case VICTORY -> {
                activeGameView.render(world, 0);
                hudView.render();
                victoryView.render();
            }
            case CAMPAIGN -> campaignView.render();
            case CONTROLS ->  controlsView.render();
        }
    }

    private void handleStateChange(GameState oldState, GameState newState) {
        AbstractScreenView oldScreen = oldState != null ? getScreen(oldState) : null;
        AbstractScreenView newScreen = getScreen(newState);

        if (oldScreen != null && oldScreen.usesVideo()) {
            oldScreen.hide();
        }

        if (newScreen != null && newScreen.usesVideo()) {
            newScreen.show();
        }
    }

    private AbstractScreenView getScreen(GameState state) {
        return switch (state) {
            case MAIN_MENU -> mainMenuView;
            case PAUSED -> pauseView;
            case GAME_OVER -> gameOverView;
            case VICTORY -> victoryView;
            case CAMPAIGN -> campaignView;
            case CONTROLS -> controlsView;
            default -> null; // Account for ActiveGameView
        };
    }

    /// Updates input processor for the stages.
    /// Makes sure the correct view is displayed at a given time.
    ///
    /// @param state State of the gameworld
    ///
    public void updateInputProcessor(GameState state) {
        switch (state) {
            case MAIN_MENU -> Gdx.input.setInputProcessor(mainMenuView.getStage());
            case ACTIVE -> Gdx.input.setInputProcessor(activeGameView.getStage());
            case PAUSED -> Gdx.input.setInputProcessor(pauseView.getStage());
            case GAME_OVER -> Gdx.input.setInputProcessor(gameOverView.getStage());
            case VICTORY -> Gdx.input.setInputProcessor(victoryView.getStage());
            case CAMPAIGN -> Gdx.input.setInputProcessor(campaignView.getStage());
            case CONTROLS -> Gdx.input.setInputProcessor(controlsView.getStage());
            default -> Gdx.input.setInputProcessor(null);
        }
    }

    /// Rebuild UI when starting or restarting game
    ///
    public void rebuildUI() {
        campaignView.rebuildUI();
    }

    /// Sets up views for the weapons.
    /// Since these are dynamic, they are constantly updated.
    ///
    /// @param player Player's weapon to display
    /// @param enemy Enemy's weapon to display
    ///
    public void setupWeaponViews(AbstractCharacter player, AbstractCharacter enemy) {
        AbstractWeapon playerWeapon = player.getEquippedWeapon();
       new WeaponView(playerWeapon);

        AbstractWeapon enemyWeapon = enemy.getEquippedWeapon();
        new WeaponView(enemyWeapon);
    }

    private void musicHandler(GameState state){
        switch (state) {
            case ACTIVE -> {
                switch (controller.getCurrentLevelNumber()){
                    case 1 -> gameSounds.playMusic(GameSounds.MusicType.LEVEL1);
                    case 2 -> gameSounds.playMusic(GameSounds.MusicType.LEVEL2);
                    case 3 -> gameSounds.playMusic(GameSounds.MusicType.LEVEL3);
                }
            }
            case MAIN_MENU -> gameSounds.playMusic(GameSounds.MusicType.MAIN_MENU);
        }
    }
}
