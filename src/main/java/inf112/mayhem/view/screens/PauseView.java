package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;

public class PauseView extends AbstractScreenView {
    private final AppWorld world;
    private final AppController controller;
    private final ShapeRenderer shapeRenderer;

    /// Constructs a new PauseView with the given world and controller.
    ///
    /// @param world The game world.
    /// @param controller The application controller for handling state transitions.
    ///
    public PauseView(AppWorld world, AppController controller) {
        this.world = world;
        this.controller = controller;
        this.shapeRenderer = new ShapeRenderer();
        create(world.width(), world.height());
    }

    @Override
    protected Table createButtons() {
        Table table = new Table();
        table.setFillParent(true);

        BitmapFont titleFont = generateFont("fonts/Inter_28pt-Black.ttf", 56, 2, 2, Color.BLACK, 3, Color.BLACK);

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.WHITE);
        titleStyle.font = titleFont;
        titleStyle.fontColor = Color.ORANGE;

        // Title
        table.add(new Label("PAUSED", titleStyle)).padBottom(50f).row();


        BitmapFont font = generateFont("fonts/Inter_28pt-Black.ttf", 32, 0, 0, Color.BLACK, 1, Color.BLACK);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;

        // Buttons
        TextButton startButton = new TextButton("Resume Game", buttonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.resume();
            }
        });
        table.add(startButton).padBottom(10).row();

        TextButton exitToMainMenuButton = new TextButton("Exit to Main Menu", buttonStyle);
        exitToMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToMainMenu();
            }
        });
        table.add(exitToMainMenuButton);

        return table;
    }

    @Override
    public void render() {
        /*
         * Code snippet from Stack Overflow answer to "OpenGL (libgdx) - blending alpha map"
         * by Daniel Funicello https://stackoverflow.com/users/5738818/daniel-funicello
         * Link to original post: https://stackoverflow.com/questions/30233364/opengl-libgdx-blending-alpha-map#:~:text=1%20Answer&text=I%20use%20this:,which%20libGDX%20does%20not%20include.
         * Licensed under CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0/)
         */

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.3f));
        shapeRenderer.rect(0, 0, (float) world.width(), (float) world.height());
        shapeRenderer.end();

        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
