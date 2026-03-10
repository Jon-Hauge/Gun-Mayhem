package inf112.mayhem.view.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;

// HUD shown during gameplay. This hud includes elements displaying useful information about each player in the game.
public class HudView {
    private Stage stage;
    private AppController controller;
    private Table table;
    private Label playerLabel;
    private Label livesLabel;
    private Label gunLabel;
    private Label ammoLabel;
    private BitmapFont font;

    public HudView(AppWorld world, AppController controller) {
        this.stage = new Stage(new ScreenViewport());
        this.controller = controller;

        table = new Table();
        table.top();
        table.setFillParent(true);

        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Inter_28pt-Black.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 18;
            parameter.borderWidth = 1;
            parameter.borderColor = Color.BLACK;
            this.font = generator.generateFont(parameter);
            generator.dispose();
        } catch (Exception e) {
            Gdx.app.error("HudView", "Could not load font. Using default BitmapFont.", e);
            this.font = new BitmapFont();
        }

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        playerLabel = new Label("PLAYER: ", labelStyle);
        livesLabel = new Label("LIVES: ", labelStyle);
        gunLabel = new Label("GUN: ", labelStyle);
        ammoLabel = new Label("AMMO: ", labelStyle);

        table.add(playerLabel).expandX().padTop(10);
        table.row();
        table.add(livesLabel).expandX();
        table.row();
        table.add(gunLabel).expandX();
        table.row();
        table.add(ammoLabel).expandX();

        stage.addActor(table);
    }

    public void render() {
        com.badlogic.gdx.utils.ScreenUtils.clear(Color.WHITE);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
        if (font != null) {
            font.dispose();
        }
    }

}
