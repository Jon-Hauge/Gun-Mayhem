package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.video.VideoPlayer;

public abstract class AbstractScreenView {
    protected Stage stage;
    protected VideoPlayer videoPlayer;
    protected SpriteBatch videoBatch;

    /// Creates a stationary stage and initiates UI
    /// Should be called once before render()
    ///
    /// @param worldWidth width of the gameworld
    /// @param worldHeight height of the gameworld
    ///
    public void create(double worldWidth, double worldHeight) {
        if (this.stage == null) {
            this.stage = new Stage(new FillViewport((float) worldWidth, (float) worldHeight));
            Table buttons = createButtons();
            stage.addActor(buttons);
        }
    }

    /// Creates a table containing the game-buttons separately from the stage
    ///
    protected abstract Table createButtons();

    /// Renders the game world.
    /// Happens multiple times per second.
    ///
    public void render() {
        com.badlogic.gdx.utils.ScreenUtils.clear(Color.WHITE);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /// Resizes screen to account for changes in resolution
    ///
    /// @param width width of the resized screen
    /// @param height height of the resized screen
    ///
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    ///
    /// @return current stage
    ///
    public Stage getStage() {
        return stage;
    }

    public void rebuildUI() {
        if (stage != null) {
            stage.clear();
            Table buttons = createButtons();
            stage.addActor(buttons);
        }
    }

    /// Disposes all elements in stage
    ///
    public void dispose() {
        disposeVideo();

        if (stage != null) {
            stage.dispose();
        }
    }

    /// Shows background video.
    /// Used for screens unrelated to gameplay.
    ///
    public void show() {
        if (videoPlayer == null) {
            setupBackgroundVideo();
        }
    }

    /// Hides background video.
    /// Used for screens related to gameplay.
    ///
    public void hide() {
        disposeVideo();
    }

    ///
    /// @return false if current screen uses video
    ///
    public boolean usesVideo() {
        return false;
    }

    /// Sets up background video for screens where needed
    ///
    protected void setupBackgroundVideo() {};

    private void disposeVideo() {
        if (videoPlayer != null) {
            videoPlayer.stop();
            videoPlayer.dispose();
            videoPlayer = null;
        }
        if (videoBatch != null) {
            videoBatch.dispose();
            videoBatch = null;
        }
    }

    /// Generates a BitmapFont using FreeTypeFontGenerator
    ///
    /// @param fontPath path to the .ttf font file
    /// @param size font size in pixels
    /// @return generated BitmapFont
    ///
    protected BitmapFont generateFont(String fontPath, int size) {
        return generateFont(fontPath, size, 0, 0, Color.BLACK, 0, Color.BLACK);
    }

    /// Generates a BitmapFont using FreeTypeFontGenerator with drop shadows
    ///
    /// @param fontPath path to the .ttf font file
    /// @param size font size in pixels
    /// @param shadowOffsetX shadow offset on X axis
    /// @param shadowOffsetY shadow offset on Y axis
    /// @param shadowColor color of the shadow
    /// @return generated BitmapFont
    ///
    protected BitmapFont generateFont(String fontPath, int size, int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
        return generateFont(fontPath, size, shadowOffsetX, shadowOffsetY, shadowColor, 0, Color.BLACK);
    }

    /// Generates a BitmapFont using FreeTypeFontGenerator with drop shadows and stroke/border
    ///
    /// @param fontPath path to the .ttf font file
    /// @param size font size in pixels
    /// @param shadowOffsetX shadow offset on X axis
    /// @param shadowOffsetY shadow offset on Y axis
    /// @param shadowColor color of the shadow
    /// @param borderWidth thickness of the border (stroke)
    /// @param borderColor color of the border
    /// @return generated BitmapFont
    ///
    protected BitmapFont generateFont(String fontPath, int size, int shadowOffsetX, int shadowOffsetY, Color shadowColor, float borderWidth, Color borderColor) {
        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = size;
            parameter.shadowOffsetX = shadowOffsetX;
            parameter.shadowOffsetY = shadowOffsetY;
            parameter.shadowColor = shadowColor;
            parameter.borderWidth = borderWidth;
            parameter.borderColor = borderColor;
            parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "↑↓←→";
            BitmapFont font = generator.generateFont(parameter);
            generator.dispose();
            return font;
        } catch (Exception e) {
            Gdx.app.error("AbstractScreenView", "Could not load font: " + fontPath + ". Using default BitmapFont.", e);
            return new BitmapFont();
        }
    }
}
