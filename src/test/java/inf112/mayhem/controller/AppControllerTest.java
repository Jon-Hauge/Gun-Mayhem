package inf112.mayhem.controller;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.view.screens.ScreenManager;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;

public class AppControllerTest {

    private AppController appController;
    private AppWorld world;
    private ScreenManager screenManager;
    private PlayerController playerController;

    @BeforeEach
    void setUp() throws Exception {
        appController = new AppController();
        world = mock(AppWorld.class);
        screenManager = mock(ScreenManager.class);
        playerController = mock(PlayerController.class);

        setField(appController, "world", world);
        setField(appController, "screenManager", screenManager);
        setField(appController, "playerController", playerController);
        appController.skipCreate = true;
    }

    private void setField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private Object getField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    @Test
    void pauseSetsGameStateToPaused() {
        when(world.state()).thenReturn(GameState.ACTIVE, GameState.PAUSED);
        appController.pause();
        verify(world).setState(GameState.PAUSED);
        verify(screenManager).updateInputProcessor(GameState.PAUSED);
    }

    @Test
    void resumeSetsGameStateToActive() {
        when(world.state()).thenReturn(GameState.PAUSED, GameState.ACTIVE);
        appController.resume();
        verify(world).setState(GameState.ACTIVE);
        verify(screenManager).updateInputProcessor(GameState.ACTIVE);
    }

    @Test
    void goToMainMenuSetsGameStateToMainMenu() {
        when(world.state()).thenReturn(GameState.MAIN_MENU);
        appController.goToMainMenu();
        verify(world).setState(GameState.MAIN_MENU);
        verify(screenManager).updateInputProcessor(GameState.MAIN_MENU);
    }

    @Test
    void campaignSetsGameStateToCampaignAndRebuildsUI() {
        when(world.state()).thenReturn(GameState.CAMPAIGN);
        appController.campaign();
        verify(world).setState(GameState.CAMPAIGN);
        verify(screenManager).rebuildUI();
        verify(screenManager).updateInputProcessor(GameState.CAMPAIGN);
    }

    @Test
    void controlsSetsGameStateToControls() {
        when(world.state()).thenReturn(GameState.CONTROLS);
        appController.goToControls();
        verify(world).setState(GameState.CONTROLS);
        verify(screenManager).updateInputProcessor(GameState.CONTROLS);
    }

    @Test
    void setFullscreenModeUsesCurrentDisplayMode() {
        Gdx.graphics = mock(Graphics.class);
        DisplayMode displayMode = mock(DisplayMode.class);
        when(Gdx.graphics.getDisplayMode()).thenReturn(displayMode);

        appController.setFullscreenMode();

        verify(Gdx.graphics).setFullscreenMode(displayMode);
    }

    @Test
    void setWindowedModeUsesDefaultResolution() {
        Gdx.graphics = mock(Graphics.class);

        appController.setWindowedMode();

        verify(Gdx.graphics).setWindowedMode(1280, 768);
    }

    @Test
    void isLevelUnlockedReturnsTrueForUnlockedLevels() throws Exception {
        setField(appController, "unlockedLevels", 2);

        assertTrue(appController.isLevelUnlocked(1));
        assertTrue(appController.isLevelUnlocked(2));
        assertFalse(appController.isLevelUnlocked(3));
    }

    @Test
    void startLevelResetsWorldAndSetsActiveState() {
        appController.startLevel(2);

        verify(world).reset();
    }

    @Test
    void nextLevelProgressesToNextLevel() throws Exception {
        appController.skipCreate = true;
        setField(appController, "currentLevel", 1);
        appController.nextLevel();
        assertEquals(2, appController.getCurrentLevelNumber());

        setField(appController, "currentLevel", 2);
        appController.nextLevel();
        assertEquals(3, appController.getCurrentLevelNumber());

        setField(appController, "currentLevel", 3);
        appController.nextLevel();
        verify(world).setState(GameState.MAIN_MENU);
    }

    @Test
    void restartGameResetsWorld() {
        appController.restartGame();
        verify(world).reset();
    }

    @Test
    void disposeDisposesScreenManager() {
        appController.dispose();
        verify(screenManager).dispose();
    }

    @Test
    void resizeResizesScreenManager() {
        appController.resize(100, 200);
        verify(screenManager).resize(100, 200);
    }

    @Test
    void exitGameExitsApplication() {
        Gdx.app = mock(Application.class);
        appController.exitGame();
        verify(Gdx.app).exit();
    }

    @Test
    void getCurrentLevelNumberReturnsCorrectLevel() throws Exception {
        setField(appController, "currentLevel", 5);
        assertEquals(5, appController.getCurrentLevelNumber());
    }

    @Test
    void handleInputPausesWhenActiveAndEscPressed() {
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)).thenReturn(true);
        when(world.state()).thenReturn(GameState.ACTIVE);

        Gdx.graphics = mock(Graphics.class);
        appController.render();

        verify(world).setState(GameState.PAUSED);
    }

    @Test
    void handleInputResumesWhenPausedAndEscPressed() {
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)).thenReturn(true);
        when(world.state()).thenReturn(GameState.PAUSED);

        Gdx.graphics = mock(Graphics.class);
        appController.render();

        verify(world).setState(GameState.ACTIVE);
    }

    @Test
    void renderUpdatesWorldAndControllersWhenActive() {
        when(world.state()).thenReturn(GameState.ACTIVE);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.16f);
        Gdx.input = mock(Input.class);

        appController.render();

        verify(world).update(0.16f);
        verify(playerController).update(0.16f);
        verify(world).evaluateState();
        verify(world).updateLevelCount(anyInt(), anyInt());
    }

    @Test
    void renderUpdatesLevelCountOnVictory() {
        when(world.state()).thenReturn(GameState.VICTORY);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.16f);
        Gdx.input = mock(Input.class);

        appController.render();

        verify(world).updateLevelCount(anyInt(), anyInt());
    }

    @Test
    void renderUpdatesLevelCountOnGameOver() {
        when(world.state()).thenReturn(GameState.GAME_OVER);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.16f);
        Gdx.input = mock(Input.class);

        appController.render();

        verify(world).updateLevelCount(anyInt(), anyInt());
    }

    @Test
    void renderDoesNotUpdateWorldWhenNotActive() {
        when(world.state()).thenReturn(GameState.PAUSED);
        Gdx.graphics = mock(Graphics.class);
        Gdx.input = mock(Input.class);

        appController.render();

        verify(world, org.mockito.Mockito.never()).update(anyFloat());
        verify(playerController, org.mockito.Mockito.never()).update(anyFloat());
    }
}
