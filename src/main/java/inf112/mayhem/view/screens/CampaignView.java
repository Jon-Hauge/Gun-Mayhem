package inf112.mayhem.view.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;

public class CampaignView extends AbstractScreenView {
    private AppWorld world;
    private AppController controller;

    public CampaignView(AppWorld world, AppController controller) {
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

        TextButton.TextButtonStyle lockedButtonStyle = new TextButton.TextButtonStyle();
        lockedButtonStyle.font = buttonFont;
        lockedButtonStyle.fontColor = Color.GRAY;

        Label.LabelStyle labelStyle = new Label.LabelStyle(titleFont, Color.WHITE);
        Label title = new Label("CAMPAIGN", labelStyle);
        table.add(title).padBottom(40).colspan(3).row();
        
        rebuildLevelButtons(table, buttonStyle, lockedButtonStyle);

        TextButton backButton = new TextButton("Back", buttonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToMainMenu();
            }
        });
        table.add(backButton).padTop(40).colspan(3);

        return table;
    }

    private void rebuildLevelButtons(Table table, TextButton.TextButtonStyle buttonStyle, TextButton.TextButtonStyle lockedButtonStyle) {
        // Level 1
        TextButton level1Button = new TextButton("Level 1", buttonStyle);
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startLevel(1);
            }
        });
        table.add(level1Button).pad(20);

        // Level 2
        boolean level2Unlocked = controller.isLevelUnlocked(2);
        TextButton level2Button = new TextButton("Level 2", level2Unlocked ? buttonStyle : lockedButtonStyle);
        if (level2Unlocked) {
            level2Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.startLevel(2);
                }
            });
        }
        table.add(level2Button).pad(20);

        // Level 3
        boolean level3Unlocked = controller.isLevelUnlocked(3);
        TextButton level3Button = new TextButton("Level 3", level3Unlocked ? buttonStyle : lockedButtonStyle);
        if (level3Unlocked) {
            level3Button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.startLevel(3);
                }
            });
        }
        table.add(level3Button).pad(20).row();
    }

    public void render() {
        super.render();
    }
}
