package inf112.mayhem.view.screens.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.GameState;
import inf112.mayhem.model.PlayerModel;
import inf112.mayhem.view.screens.AbstractScreenView;


/**
 * The HudView class is responsible for displaying information during active gameplay
 * such as remaining lives and ammo levels.
 * The HUD elements are rendered as an overlay on top of the game world.
 */
public class HudView extends AbstractScreenView {
    private AppWorld world;
    private AppController controller;
    private Table table;
    private Label livesLabel;
    private Label ammoLabel;
    private Label gunLabel;
    private BitmapFont font;
    private BitmapFont ammoFont;
    // CHANGE BELOW WHEN ADDED AMMO/LIVES/GUN PROPERLY
    private int currentLives = 3;
    private int maxLives = 3;
    private int currentAmmo = 10;
    private int maxAmmo = 10;
    private ShapeRenderer shape;

    private float barWidth = 175;
    private float barHeight = 20;

    /**
     * Constructs a new HudView with the given world and controller.
     *
     * @param world The game world.
     * @param controller The application controller for handling state transitions.
     */
    public HudView(AppWorld world, AppController controller) {
        this.world = world;
        this.controller = controller;

        this.stage = new Stage(new FitViewport((float) world.width(), (float) world.height()));
        this.shape = new ShapeRenderer();
        create(world.width(), world.height());
    }

    /**
     * Initializes the HUD's stage and UI elements.
     * This includes creating fonts, labels, and the main UI table.
     *
     * @param worldWidth The width of the virtual viewport.
     * @param worldHeight The height of the virtual viewport.
     */
    @Override
    public void create(double worldWidth, double worldHeight) {
        table = new Table();
        table.bottom().left();
        table.padLeft(50);
        table.padBottom(30);
        table.setFillParent(true);

        try {
            this.font = generateFont("fonts/Inter_28pt-Black.ttf", 40, 3, 3, Color.BLACK, 3, Color.BLACK);
            this.ammoFont = generateFont("fonts/Inter_28pt-Black.ttf", 22, 3, 3, Color.BLACK, 2, Color.BLACK);
        } catch (Exception e) {
            Gdx.app.error("HudView", "Could not load font. Using default BitmapFont.", e);
            this.font = new BitmapFont();
            this.ammoFont = new BitmapFont();
        }

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label.LabelStyle ammoLabelStyle = new Label.LabelStyle(ammoFont, Color.WHITE);
        livesLabel = new Label("LIVES: ", labelStyle);
        ammoLabel = new Label("AMMO: ", ammoLabelStyle);

        table.add(livesLabel).left();
        table.row();
        table.add(ammoLabel).left();

        stage.addActor(table);
    }

    @Override
    protected Table createButtons() {
        return null;
    }

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        // Positioning the bar to the right of the ammoLabel
        float x = table.getX() + table.getPadLeft() + ammoLabel.getWidth() + 10;
        float y = table.getY() + table.getPadBottom() + (ammoLabel.getHeight() * ammoLabel.getScaleY() - barHeight) / 2;
        float width = barWidth;
        float height = barHeight;

        shape.setProjectionMatrix(stage.getCamera().combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Drop shadow ammo bar
        float shadowOffset = 5;
        shape.setColor(new Color(0, 0, 0, 0.5f)); // Semi-transparent black
        shape.rect(x + shadowOffset, y - shadowOffset, width, height);

        // Stroke (outline) ammo bar
        float borderWidth = 3;
        shape.setColor(Color.BLACK);
        shape.rect(x - borderWidth, y - borderWidth, width + 2 * borderWidth, height + 2 * borderWidth);

        // Background of ammo bar
        shape.setColor(Color.DARK_GRAY);
        shape.rect(x, y, width, height);

        float progress = (float) currentAmmo / maxAmmo;
        shape.setColor(progress > 0.5f ? Color.GREEN : progress > 0.2f ? Color.ORANGE : Color.RED);
        shape.rect(x, y, width * progress, height);

        shape.end();
    }

    /**
     * Updates the HUD's internal state and labels based on the current player model.
     *
     * @param player The current player instance.
     */
    public void update(PlayerModel player) {
        if (player != null) {
            this.currentLives = player.lives();
            this.currentAmmo = player.ammo();
            livesLabel.setText("LIVES: " + currentLives);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (font != null) {
            font.dispose();
        }
        if (ammoFont != null) {
            ammoFont.dispose();
        }
        if (shape != null) {
            shape.dispose();
        }
    }

}
