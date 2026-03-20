package inf112.mayhem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
<<<<<<< HEAD
=======
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
>>>>>>> 751dfff (Adding campaign feature and level selecting. Need to add more maps!)
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.PlayerModel;
<<<<<<< HEAD
import inf112.mayhem.model.enums.GameState;
import inf112.mayhem.view.screens.CampaignView;
=======
import inf112.mayhem.model.collision.ProjectileCollisionHandler;
<<<<<<< HEAD
import inf112.mayhem.model.enums.GameState;
=======
import inf112.mayhem.view.screens.CampaignView;
>>>>>>> 1744e4b (Adding campaign feature and level selecting. Need to add more maps!)
>>>>>>> 751dfff (Adding campaign feature and level selecting. Need to add more maps!)
import inf112.mayhem.view.screens.GameOverView;
import inf112.mayhem.view.screens.MainMenuView;
import inf112.mayhem.view.screens.PauseView;
import inf112.mayhem.view.screens.VictoryView;
import inf112.mayhem.view.screens.activeGameView.ActiveGameView;
import inf112.mayhem.view.screens.hud.HudView;

public class AppControllerTest {

    private AppController appController;
    private AppWorld world;

    @BeforeEach
    void setUp() throws Exception {
        appController = new AppController();
        world = new AppWorld(1280, 768);
        
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        Gdx.input = mock(Input.class);
        Gdx.files = mock(Files.class);

        setField(appController, "world", world);
        
        ActiveGameView activeGameView = mock(ActiveGameView.class);
        when(activeGameView.getStage()).thenReturn(mock(Stage.class));
        setField(appController, "activeGameView", activeGameView);

        MainMenuView mainMenuView = mock(MainMenuView.class);
        when(mainMenuView.getStage()).thenReturn(mock(Stage.class));
        setField(appController, "mainMenuView", mainMenuView);

        PauseView pauseView = mock(PauseView.class);
        when(pauseView.getStage()).thenReturn(mock(Stage.class));
        setField(appController, "pauseView", pauseView);

        GameOverView gameOverView = mock(GameOverView.class);
        when(gameOverView.getStage()).thenReturn(mock(Stage.class));
        setField(appController, "gameOverView", gameOverView);

        VictoryView victoryView = mock(VictoryView.class);
        when(victoryView.getStage()).thenReturn(mock(Stage.class));
        setField(appController, "victoryView", victoryView);

<<<<<<< HEAD
<<<<<<< HEAD
        CampaignView campaignView = mock(CampaignView.class);
        when(campaignView.getStage()).thenReturn(mock(Stage.class));
        setField(appController, "campaignView", campaignView);
=======
>>>>>>> 8f58501 (Adding VictoryView and tests for it, including small changes to the player for functionality)
=======
        CampaignView campaignView = mock(CampaignView.class);
        when(campaignView.getStage()).thenReturn(mock(Stage.class));
        setField(appController, "campaignView", campaignView);

>>>>>>> 751dfff (Adding campaign feature and level selecting. Need to add more maps!)
        HudView hudView = mock(HudView.class);
        setField(appController, "hudView", hudView);

        PlayerController playerController = mock(PlayerController.class);
        PlayerModel playerModel = mock(PlayerModel.class);
        when(playerModel.lives()).thenReturn(3);
        when(playerController.getPlayer()).thenReturn(playerModel);
        setField(appController, "playerController", playerController);
        setField(appController, "playerModel", playerModel);

        PlayerModel enemyModel = mock(PlayerModel.class);
        when(enemyModel.lives()).thenReturn(3);
        setField(appController, "enemyModel", enemyModel);

        WeaponController weaponController = mock(WeaponController.class);
        setField(appController, "weaponController", weaponController);

        ProjectileCollisionHandler pCollision = mock(ProjectileCollisionHandler.class);
        setField(appController, "pCollision", pCollision);
    }

    @Test
    void testCampaign() {
        appController.campaign();
        assertEquals(GameState.CAMPAIGN, world.state());
    }

    @Test
    void testIsLevelUnlocked() throws Exception {
        setField(appController, "unlockedLevels", 1);
        assertTrue(appController.isLevelUnlocked(1));
        assertFalse(appController.isLevelUnlocked(2));
    }

    @Test
    void testStartLevel() throws Exception {
        setField(appController, "skipCreate", true);
        PlayerModel playerModel = (PlayerModel) getField(appController, "playerModel");
        appController.startLevel(2);
        assertEquals(GameState.ACTIVE, world.state());
        verify(playerModel).setLives(3);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = AppController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testPause() {
        appController.pause();
        assertEquals(GameState.PAUSED, world.state());
    }

    @Test
    void testResume() {
        appController.resume();
        assertEquals(GameState.ACTIVE, world.state());
    }

    @Test
    void testStartGame() throws Exception {
        setField(appController, "skipCreate", true);
        appController.startGame();
        assertEquals(GameState.ACTIVE, world.state());
    }

    @Test
    void testGoToMainMenu() {
        appController.goToMainMenu();
        assertEquals(GameState.MAIN_MENU, world.state());
    }

    @Test
    void testExitGame() {
        appController.exitGame();
        verify(Gdx.app).exit();
    }

    @Test
    void testTogglePauseWithEscape() {
        world.setState(GameState.ACTIVE);
        when(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)).thenReturn(true);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.016f);

        appController.render();

        assertEquals(GameState.PAUSED, world.state());

        when(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)).thenReturn(true);
        appController.render();

        assertEquals(GameState.ACTIVE, world.state());
    }

    @Test
    void testEscapeDoesNotPauseInMainMenu() {
        world.setState(GameState.MAIN_MENU);
        when(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)).thenReturn(true);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.016f);

        appController.render();

        assertEquals(GameState.MAIN_MENU, world.state());
    }

    @Test
    void testResize() throws Exception {
        int width = 800;
        int height = 600;

        appController.resize(width, height);

        verify((ActiveGameView) getField(appController, "activeGameView")).resize(width, height);
        verify((MainMenuView) getField(appController, "mainMenuView")).resize(width, height);
        verify((PauseView) getField(appController, "pauseView")).resize(width, height);
        verify((GameOverView) getField(appController, "gameOverView")).resize(width, height);
        verify((HudView) getField(appController, "hudView")).resize(width, height);
    }

    @Test
    void testDispose() throws Exception {
        appController.dispose();

        verify((ActiveGameView) getField(appController, "activeGameView")).dispose();
        verify((MainMenuView) getField(appController, "mainMenuView")).dispose();
        verify((PauseView) getField(appController, "pauseView")).dispose();
        verify((GameOverView) getField(appController, "gameOverView")).dispose();
        verify((HudView) getField(appController, "hudView")).dispose();
    }

    @Test
    void testGameOverWhenLivesAreZero() throws Exception {
        world.setState(GameState.ACTIVE);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.016f);
        
        PlayerModel playerModel = mock(PlayerModel.class);
        when(playerModel.lives()).thenReturn(0);
        setField(appController, "playerModel", playerModel);

        appController.render();

        assertEquals(GameState.GAME_OVER, world.state());
    }

    @Test
    void testRestartGameResetsLives() throws Exception {
        PlayerModel playerModel = (PlayerModel) getField(appController, "playerModel");
        PlayerModel enemyModel = (PlayerModel) getField(appController, "enemyModel");
        
        setField(appController, "skipCreate", true);
        
        appController.restartGame();
        
        verify(playerModel).setLives(3);
        verify(enemyModel).setLives(3);
        assertEquals(GameState.ACTIVE, world.state());
    }

    @Test
    void testVictoryWhenEnemyLivesAreZero() throws Exception {
        world.setState(GameState.ACTIVE);
        PlayerModel enemyModel = (PlayerModel) getField(appController, "enemyModel");
        when(enemyModel.lives()).thenReturn(0);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.016f);

        appController.render();

        assertEquals(GameState.VICTORY, world.state());
    }

    @Test
    void testNextLevelRestartsGame() throws Exception {
        setField(appController, "skipCreate", true);
        appController.nextLevel();
        assertEquals(GameState.ACTIVE, world.state());
    }

    private Object getField(Object target, String fieldName) throws Exception {
        Field field = AppController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
}
