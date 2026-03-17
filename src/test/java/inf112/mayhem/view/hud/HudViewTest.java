package inf112.mayhem.view.hud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.math.Matrix4;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class HudViewTest {

    private HudView hudView;
    private AppWorld world;
    private AppController controller;
    private PlayerModel player;

    private Label livesLabel;
    private Label ammoLabel;
    private Stage stage;
    private ShapeRenderer shape;
    private BitmapFont font;
    private BitmapFont ammoFont;

    @BeforeEach
    void setUp() throws Exception {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        world = mock(AppWorld.class);
        controller = mock(AppController.class);
        player = mock(PlayerModel.class);

        hudView = new HudView(world, controller);

        // Mock internal components to avoid NPEs and native dependencies
        livesLabel = mock(Label.class);
        ammoLabel = mock(Label.class);
        stage = mock(Stage.class);
        shape = mock(ShapeRenderer.class);
        font = mock(BitmapFont.class);
        ammoFont = mock(BitmapFont.class);

        setField(hudView, "livesLabel", livesLabel);
        setField(hudView, "ammoLabel", ammoLabel);
        setField(hudView, "stage", stage);
        setField(hudView, "shape", shape);
        setField(hudView, "font", font);
        setField(hudView, "ammoFont", ammoFont);

        // For render test
        Table table = mock(Table.class);
        setField(hudView, "table", table);
        Camera camera = mock(Camera.class);
        setField(camera, "combined", new Matrix4());
        when(stage.getCamera()).thenReturn(camera);
    }

    @Test
    void testRenderCallsStageAndShape() {
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.016f);

        hudView.render();

        verify(stage).act(0.016f);
        verify(stage).draw();
        verify(shape).begin(ShapeRenderer.ShapeType.Filled);
        verify(shape, atLeastOnce()).rect(anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(shape).end();
    }

    // juni AI (jetbrains)
    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field;
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                return;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + fieldName + " not found in " + target.getClass());
    }

    private Object getField(Object target, String fieldName) throws Exception {
        Field field = HudView.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    @Test
    void testUpdateUpdatesLivesAndAmmo() throws Exception {
        when(player.getLives()).thenReturn(5);
        when(player.getAmmo()).thenReturn(15);

        hudView.update(player);

        assertEquals(5, getField(hudView, "currentLives"));
        assertEquals(15, getField(hudView, "currentAmmo"));
        verify(livesLabel).setText("LIVES: 5");
    }

    @Test
    void testUpdateDoesNothingIfPlayerIsNull() throws Exception {
        setField(hudView, "currentLives", 3);
        setField(hudView, "currentAmmo", 10);

        hudView.update(null);

        assertEquals(3, getField(hudView, "currentLives"));
        assertEquals(10, getField(hudView, "currentAmmo"));
        verify(livesLabel, never()).setText(anyString());
    }

    @Test
    void testDisposeReleasesResources() {
        hudView.dispose();

        verify(stage).dispose();
        verify(font).dispose();
        verify(ammoFont).dispose();
        verify(shape).dispose();
    }
}
