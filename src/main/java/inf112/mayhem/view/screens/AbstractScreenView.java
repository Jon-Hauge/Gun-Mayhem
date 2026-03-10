package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;

public abstract class AbstractScreenView {
    protected Stage stage;

    /// Generates a BitmapFont using FreeTypeFontGenerator
    ///
    /// @param fontPath - path to the .ttf font file
    /// @param size - font size in pixels
    /// @return generated BitmapFont
    ///
    protected BitmapFont generateFont(String fontPath, int size) {
        return generateFont(fontPath, size, 0, 0, Color.BLACK, 0, Color.BLACK);
    }

    /// Generates a BitmapFont using FreeTypeFontGenerator with drop shadows
    ///
    /// @param fontPath - path to the .ttf font file
    /// @param size - font size in pixels
    /// @param shadowOffsetX - shadow offset on X axis
    /// @param shadowOffsetY - shadow offset on Y axis
    /// @param shadowColor - color of the shadow
    /// @return generated BitmapFont
    ///
    protected BitmapFont generateFont(String fontPath, int size, int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
        return generateFont(fontPath, size, shadowOffsetX, shadowOffsetY, shadowColor, 0, Color.BLACK);
    }

    /// Generates a BitmapFont using FreeTypeFontGenerator with drop shadows and stroke/border
    ///
    /// @param fontPath - path to the .ttf font file
    /// @param size - font size in pixels
    /// @param shadowOffsetX - shadow offset on X axis
    /// @param shadowOffsetY - shadow offset on Y axis
    /// @param shadowColor - color of the shadow
    /// @param borderWidth - thickness of the border (stroke)
    /// @param borderColor - color of the border
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
            BitmapFont font = generator.generateFont(parameter);
            generator.dispose();
            return font;
        } catch (Exception e) {
            Gdx.app.error("AbstractScreenView", "Could not load font: " + fontPath + ". Using default BitmapFont.", e);
            return new BitmapFont();
        }
    }

    /// Creates a stationary stage and initiates UI
    /// Should be called once before render()
    ///
    /// @param worldWidth - width of the gameworld
    /// @param worldHeight - height of the gameworld
    ///
    public void create(double worldWidth, double worldHeight) {
        this.stage = new Stage(new FillViewport((float) worldWidth, (float) worldHeight));
        createUI();
    }

    /// Creates UI for the game
    ///
    protected abstract void createUI();

    /// Creates a table containing the game-buttons separately from the stage
    ///
    protected abstract Table createButtons();

    /// Renders the game world
    /// Happens multiple times per second
    ///
    public void render() {
        com.badlogic.gdx.utils.ScreenUtils.clear(Color.WHITE);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /// Resizes screen to account for changes in resolution
    ///
    /// @param width - width of the resized screen
    /// @param height - height of the resized screen
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

    /// Disposes all elements in stage
    ///
    public void dispose() {
        stage.dispose();
    }

}
