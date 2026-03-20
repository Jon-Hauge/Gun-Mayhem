package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenuView extends AbstractScreenView {
    private AppWorld world;
    private AppController controller;

    /**
     * Constructs a new MainMenuView with the given world and controller.
     *
     * @param world The game world.
     * @param controller The application controller for handling state transitions.
     */
    public MainMenuView(AppWorld world, AppController controller) {
        this.world = world;
        this.controller = controller;
        create(world.width(), world.height());
    }

    @Override
    protected Table createButtons() {
        Table table = new Table();
        table.setFillParent(true);

        BitmapFont titleFont = generateFont("fonts/Inter_28pt-Black.ttf", 48, 2, 2, Color.BLACK, 3, Color.BLACK);
        BitmapFont buttonFont = generateFont("fonts/Inter_28pt-Black.ttf", 24, 1, 1, Color.BLACK, 3, Color.BLACK);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = buttonFont;
        buttonStyle.fontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle(titleFont, Color.WHITE);
        Label title = new Label("TEAM MAYHEM", labelStyle);
        table.add(title).padBottom(20).row();

        TextButton campaignButton = new TextButton("Campaign", buttonStyle);
        campaignButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.campaign();
            }
        });
        table.add(campaignButton).padBottom(10).row();

        TextButton exitButton = new TextButton("Exit game", buttonStyle);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exitGame();
            }
        });
        table.add(exitButton);

        return table;
    }
}
