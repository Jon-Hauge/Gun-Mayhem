package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;

public abstract class AbstractScreenView {
    protected Stage stage;

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
