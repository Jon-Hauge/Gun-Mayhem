package inf112.mayhem.view.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.mayhem.controller.AppController;

public class GameOverView extends AbstractScreenView {
    private AppController controller;

    public GameOverView(AppController controller) { this.controller = controller; }

    @Override
    protected void createUI() {
        Table gameOverButtons = createButtons();
        stage.addActor(gameOverButtons);
    }

    @Override
    protected Table createButtons() {
        Table table = new Table();
        table.setFillParent(true);

        BitmapFont font = generateFont("fonts/Inter_28pt-Black.ttf", 32, 0, 0, Color.BLACK, 1, Color.BLACK);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label title = new Label("GAME OVER", labelStyle);
        table.add(title).padBottom(20).row();

        TextButton restartButton = new TextButton("Restart button", buttonStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.restartGame();
            }
        });
        table.add(restartButton).padBottom(10).row();

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
