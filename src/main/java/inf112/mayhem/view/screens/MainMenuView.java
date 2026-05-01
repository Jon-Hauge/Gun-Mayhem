package inf112.mayhem.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;

public class MainMenuView extends AbstractScreenView {
    private AppWorld world;
    private AppController controller;

    private VideoPlayer videoPlayer;
    private SpriteBatch videoBatch;

    /// Constructs a new MainMenuView with the given world and controller.
    ///
    /// @param world The game world
    /// @param controller The application controller for handling state transitions
    ///
    public MainMenuView(AppWorld world, AppController controller) {
        this.world = world;
        this.controller = controller;

        setupBackgroundVideo();

        create(world.width(), world.height());
    }

    private void setupBackgroundVideo() {
        videoBatch = new SpriteBatch();

        try {
            videoPlayer = VideoPlayerCreator.createVideoPlayer();
            videoPlayer.load(Gdx.files.internal("screens/start_screen.webm"));
            videoPlayer.setLooping(true);
            videoPlayer.play();
        } catch (Exception e) {
            Gdx.app.error("MainMenuView", "Could not load background video", e);
            videoPlayer = null;
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

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
    protected Table createButtons() {
        Table table = new Table();
        table.setFillParent(true);

        BitmapFont buttonFont = generateFont("fonts/Inter_28pt-Black.ttf", 50, 1, 1, Color.BLACK, 3, Color.BLACK);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = buttonFont;
        buttonStyle.fontColor = Color.WHITE;

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

    @Override
    public void dispose() {
        if (videoPlayer != null) {
            videoPlayer.dispose();
        }

        if (videoBatch != null) {
            videoBatch.dispose();
        }

        super.dispose();
    }
}