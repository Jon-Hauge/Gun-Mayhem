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
    private AppController controller;

    public MainMenuView(AppController controller) {
        this.controller = controller;
    }

    @Override
    protected void createUI() {
        Table mainMenuButtons = createButtons();
        stage.addActor(mainMenuButtons);
    }

    @Override
    protected Table createButtons() {
        Table table = new Table();
        table.setFillParent(true);

        BitmapFont font = new BitmapFont();
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.font.getData().setScale(2f);
        buttonStyle.fontColor = Color.BLACK;

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label title = new Label("TEAM MAYHEM", labelStyle);
        table.add(title).padBottom(20).row();

        TextButton startButton = new TextButton("Start button", buttonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame();
            }
        });
        table.add(startButton).padBottom(10).row();

        TextButton exitButton = new TextButton("Exit button", buttonStyle);
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
