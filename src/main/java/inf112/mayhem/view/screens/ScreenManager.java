package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.model.entities.character.PlayerClass;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.view.screens.activeGameView.ActiveGameView;
import inf112.mayhem.view.screens.activeGameView.WeaponViewFactory;
import inf112.mayhem.view.screens.hud.HudView;

public class ScreenManager {
    private final ActiveGameView activeGameView;
    private final MainMenuView mainMenuView;
    private final PauseView pauseView;
    private final GameOverView gameOverView;
    private final VictoryView victoryView;
    private final HudView hudView;
    private final CampaignView campaignView;
    private final PlayerClass playerClass;
    private final WeaponViewFactory weaponViewFactory = new WeaponViewFactory();

    public ScreenManager(AppWorld world, AppController controller, PlayerClass playerClass) {
        this.activeGameView = new ActiveGameView(world, controller);
        this.mainMenuView = new MainMenuView(world, controller);
        this.pauseView = new PauseView(world, controller);
        this.gameOverView = new GameOverView(world, controller);
        this.victoryView = new VictoryView(world, controller);
        this.campaignView = new CampaignView(world, controller);
        this.hudView = new HudView(world, controller, playerClass);
        this.playerClass = playerClass;
    }

    public void dispose() {
        activeGameView.dispose();
        mainMenuView.dispose();
        pauseView.dispose();
        gameOverView.dispose();
        victoryView.dispose();
        campaignView.dispose();
        hudView.dispose();
    }

    public void resize(int width, int height) {
        activeGameView.resize(width, height);
        mainMenuView.resize(width, height);
        pauseView.resize(width, height);
        gameOverView.resize(width, height);
        victoryView.resize(width, height);
        campaignView.resize(width, height);
        hudView.resize(width, height);
    }

    public void render(AppWorld world, float delta) {
        switch (world.state()) {
            case MAIN_MENU -> mainMenuView.render();
            case ACTIVE -> {
                activeGameView.render(world, delta);
                hudView.update(playerClass);
                hudView.render();
            }
            case PAUSED -> {
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
            case CAMPAIGN -> campaignView.render();
        }
    }

    public void updateInputProcessor(GameState state) {
        switch (state) {
            case MAIN_MENU -> Gdx.input.setInputProcessor(mainMenuView.getStage());
            case ACTIVE -> Gdx.input.setInputProcessor(activeGameView.getStage());
            case PAUSED -> Gdx.input.setInputProcessor(pauseView.getStage());
            case GAME_OVER -> Gdx.input.setInputProcessor(gameOverView.getStage());
            case VICTORY -> Gdx.input.setInputProcessor(victoryView.getStage());
            case CAMPAIGN -> Gdx.input.setInputProcessor(campaignView.getStage());
            default -> Gdx.input.setInputProcessor(null);
        }
    }

    public void rebuildUI() {
        campaignView.rebuildUI();
    }

    public void setupWeaponViews(PlayerClass playerClass) {
        AbstractWeapon weapon = playerClass.getEquippedWeapon();
        weapon.setWeaponView(weaponViewFactory.createWeaponView(weapon.getWeaponType()));
    }
}
