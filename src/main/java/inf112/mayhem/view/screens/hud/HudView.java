package inf112.mayhem.view.screens.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.view.screens.AbstractScreenView;

public class HudView extends AbstractScreenView {
    private Table table;
    private Label livesLabel;
    private Label ammoLabel;
    private BitmapFont font;
    private BitmapFont ammoFont;
    private int currentAmmo;
    private int maxAmmo;
    private final ShapeRenderer shape;

    private final float BAR_WIDTH = 175;
    private final float BAR_HEIGHT = 20;

    /// Constructs a new HudView with the given world and controller.
    /// The hud is responsible for displaying information during active gameplay
    /// such as remaining lives and ammo levels, rendered on top of the world.
    ///
    /// @param world The game world to render the hud in
    /// @param controller The application controller for handling state transitions
    ///
    public HudView(AppWorld world, AppController controller, AbstractCharacter character) {
        this.currentAmmo = character.getEquippedWeapon().getCurrentAmmo();
        this.maxAmmo = character.getEquippedWeapon().getMaxAmmo();
        this.stage = new Stage(new FitViewport((float) world.width(), (float) world.height()));
        this.shape = new ShapeRenderer();
        create(world.width(), world.height());
    }

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
        float y = table.getY() + table.getPadBottom() + (ammoLabel.getHeight() * ammoLabel.getScaleY() - BAR_HEIGHT) / 2;
        float width = BAR_WIDTH;
        float height = BAR_HEIGHT;

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

        float progress = 0;
        if (currentAmmo < 0) {
            progress = 1f;
        } else if (maxAmmo > 0) {
            progress = (float) currentAmmo / maxAmmo;
            progress = Math.max(0f, Math.min(1f, progress));
        } else {
            progress = 0f;
        }

        shape.setColor(progress > 0.5f ? Color.GREEN : progress > 0.2f ? Color.ORANGE : Color.RED);
        shape.rect(x, y, width * progress, height);

        shape.end();
    }

    /// Updates the HUD's internal state and labels based on the current character model.
    ///
    /// @param character The current character instance to update for
    ///
    public void update(AbstractCharacter character) {
        if (character != null) {
            int currentLives = character.lives();
            this.currentAmmo = character.getEquippedWeapon().getCurrentAmmo();
            this.maxAmmo = character.getEquippedWeapon().getMaxAmmo();
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
