package inf112.mayhem.controller;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.model.collision.ProjectileCollisionHandler;
import inf112.mayhem.view.screens.GameOverView;
import inf112.mayhem.view.screens.MainMenuView;
import inf112.mayhem.view.screens.PauseView;
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

        HudView hudView = mock(HudView.class);
        setField(appController, "hudView", hudView);

        PlayerController playerController = mock(PlayerController.class);
        WeaponController weaponController = mock(WeaponController.class);
        setField(appController, "playerController", playerController);
        setField(appController, "weaponController", weaponController);

        ProjectileCollisionHandler pCollision = mock(ProjectileCollisionHandler.class);
        setField(appController, "pCollision", pCollision);
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
    void testStartGame() {
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

    private Object getField(Object target, String fieldName) throws Exception {
        Field field = AppController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
}
