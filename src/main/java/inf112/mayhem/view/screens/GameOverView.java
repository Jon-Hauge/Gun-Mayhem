package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;

public class GameOverView extends AbstractScreenView {
    private AppWorld world;
    private AppController controller;

    /**
     * Constructs a new PauseView with the given world and controller.
     *
     * @param world The game world.
     * @param controller The application controller for handling state transitions.
     */
    public GameOverView(AppWorld world, AppController controller) {
        this.world = world;
        this.controller = controller;
        create(world.width(), world.height());
    }

    @Override
    protected Table createButtons() {
        Table table = new Table();
        table.setFillParent(true);

        BitmapFont font = generateFont("fonts/Inter_28pt-Black.ttf", 24, 0, 0, Color.BLACK, 1, Color.BLACK);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;

        TextButton startButton = new TextButton("Restart Game", buttonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.restartGame();
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
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
