package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;

public class ControlsView extends AbstractScreenView {
    private AppWorld world;
    private AppController controller;
    private ShapeRenderer shapeRenderer;

    private VideoPlayer videoPlayer;
    private SpriteBatch videoBatch;

    /// Constructs a new view over the controls of the game with the given world and controller.
    ///
    /// @param world The game world
    /// @param controller The application controller for handling state transitions
    ///
    public ControlsView(AppWorld world, AppController controller) {
        this.world = world;
        this.controller = controller;
        this.shapeRenderer = new ShapeRenderer();

        setupBackgroundVideo();

        create(world.width(), world.height());
    }

    private void setupBackgroundVideo() {
        videoBatch = new SpriteBatch();

        try {
            videoPlayer = VideoPlayerCreator.createVideoPlayer();
            videoPlayer.load(Gdx.files.internal("screens/level_select.webm"));
            videoPlayer.setLooping(true);
            videoPlayer.play();
        } catch (Exception e) {
            Gdx.app.error("CampaignView", "Could not load background video", e);
            videoPlayer = null;
        }
    }

    @Override
    protected Table createButtons() {
        Table table = new Table();
        table.setFillParent(true);

        BitmapFont titleFont = generateFont("fonts/Inter_28pt-Black.ttf", 56, 2, 2, Color.BLACK, 3, Color.BLACK);
        BitmapFont textFont = generateFont("fonts/DejaVuSans.ttf", 24, 1, 1, Color.BLACK, 3, Color.BLACK);
        BitmapFont buttonFont = generateFont("fonts/Inter_28pt-Black.ttf", 32, 1, 1, Color.BLACK, 3, Color.BLACK);

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.WHITE);
        titleStyle.font = titleFont;
        titleStyle.fontColor = Color.ORANGE;
        Label.LabelStyle textStyle = new Label.LabelStyle(textFont, Color.WHITE);
        textStyle.font = textFont;
        textStyle.fontColor = Color.WHITE;
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = buttonFont;
        buttonStyle.fontColor = Color.ORANGE;

        // Title
        table.add(new Label("Controls", titleStyle)).padBottom(50f).row();

        // Controls
        table.add(new Label("Jump: W / \u2191", textStyle)).padBottom(10).row();
        table.add(new Label("Drop Down: S / \u2193", textStyle)).padBottom(10).row();
        table.add(new Label("Move Right: D / \u2192", textStyle)).padBottom(10).row();
        table.add(new Label("Move Left: A / \u2190", textStyle)).padBottom(10).row();
        table.add(new Label("Shoot: Z", textStyle)).padBottom(10).row();

        // Buttons
        TextButton goToMainMenuButton = new TextButton("Return to main menu", buttonStyle);
        goToMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToMainMenu();
            }
        });
        table.add(goToMainMenuButton).padTop(50).row();

        return table;
    }

    @Override
    public void render() {
        if (videoPlayer != null) {
            videoPlayer.update();

            Texture videoTexture = videoPlayer.getTexture();

            if (videoTexture != null) {
                videoBatch.begin();
                videoBatch.draw(
                        videoTexture,
                        0,
                        0,
                        Gdx.graphics.getWidth(),
                        Gdx.graphics.getHeight()
                );
                videoBatch.end();
            }
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        if (videoPlayer != null) {
            videoPlayer.stop();
            videoPlayer.dispose();
        }

        if (videoBatch != null) {
            videoBatch.dispose();
        }

        super.dispose();
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}

