package inf112.mayhem.view.screens.activeGameView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.EntityModel;
import inf112.mayhem.model.EntityState;
import inf112.mayhem.model.EntityType;
import inf112.mayhem.view.maps.TileMapHelper;

public class ActiveGameView {

    private Viewport viewport; // defines screen / world size, aspect ratio and camera
    private OrthographicCamera camera; // defines which part of the world to show
    private SpriteBatch batch; // used for drawing images / textures
    private BitmapFont font; // used for writing
    private ShapeRenderer shape; // used for drawing shapes
    private Map<String, Texture> textures = new HashMap<>();
    private AppController controller;
    private CharacterRig characterRig;
    private CharacterView characterView;
    private final float scale = 0.15f;
    
    //TILEMAP
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper = new TileMapHelper();
    
    public void create(double worldWidth, double worldHeight, AppController controller) {

        // To preserve aspect ratio, use FitViewport, ExtendViewport or FillViewport.
        // FitViewport keeps aspect by leaving empty space.
        // ExtendViewport keeps aspect by extending the world size in one direction.
        // FillViewport keeps aspect by hiding part of the world in the other direction.
        this.viewport = new FillViewport((float) worldWidth, (float) worldHeight);
        this.camera = (OrthographicCamera) viewport.getCamera();
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();

        // LOAD ANIMATIONS
        this.characterRig = initiateCharacterRig();
        this.characterView = new CharacterView(characterRig);

        
        //LOAD TILEDMAP
        this.tileMapHelper = new TileMapHelper();
        tileMapHelper.setupMap();
        this.orthogonalTiledMapRenderer = tileMapHelper.getRenderer();

        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Inter_28pt-Black.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 12;
            parameter.borderWidth = 1;
            parameter.borderColor = Color.BLACK;
            this.font = generator.generateFont(parameter);
            generator.dispose();
        } catch (Exception e) {
            Gdx.app.error("ActiveGameView", "Could not load font. Using default BitmapFont.", e);
            this.font = new BitmapFont();
        }
        font.setColor(Color.WHITE);

        preloadTextures();

        Gdx.graphics.setForegroundFPS(60);
    }

    private CharacterRig initiateCharacterRig() {
        return new CharacterRig(
                new Texture(Gdx.files.internal("character_sprites/body.png")),
                new Texture(Gdx.files.internal("character_sprites/face.png")),
                new Texture(Gdx.files.internal("character_sprites/feet.png")),
                new Texture(Gdx.files.internal("character_sprites/feet.png")),
                new Texture(Gdx.files.internal("character_sprites/gun_glock.png")),
                new Texture(Gdx.files.internal("character_sprites/hands.png")),
                new Texture(Gdx.files.internal("character_sprites/hands.png")),
                new Texture(Gdx.files.internal("character_sprites/head.png")),
                scale);
    }

    /*
     * Loading everything at startup prevents glitches later on.
     */
    private void preloadTextures() {
        for (String fileName : List.of(
                "maps/level1_heaven/background.png",
        
                "maps/level2_midnightcity/background.png",
                "maps/level2_midnightcity/4.png",
                "maps/level2_midnightcity/18.png",
                "maps/level2_midnightcity/24.png",
                "maps/level2_midnightcity/25.png",
                "maps/level2_midnightcity/26.png",
                "maps/level2_midnightcity/Box1.png",
                "maps/level2_midnightcity/Box3.png",
                "maps/level2_midnightcity/Box5.png",
                "maps/level2_midnightcity/Ladder2.png",
                "maps/level2_midnightcity/Ladder3.png",
                "maps/level2_midnightcity/Tileset.png",
                "maps/level2_midnightcity/Trap.png",
        
                "character_sprites/body.png",
                "character_sprites/face.png",
                "character_sprites/feet.png",
                "character_sprites/gun_glock.png",
                "character_sprites/hands.png",
                "character_sprites/head.png"
        )) {
            getTexture(fileName);
        }
    }
    
    /// Example: load textures only once, and save them in a hash map.
    ///
    /// You should do the same with sounds and other resources. LibGDX has a built-in
    /// AssetManager that can help with this.
    ///
    /// If you do new Texture() every time you draw, you'll slow the game down and
    /// fill up memory.
    ///
    /// @param name
    ///
    /// @return
    ///
    private Texture getTexture(String name) {
        if (!textures.containsKey(name)) {
            FileHandle file = Gdx.files.internal(name);
            if (file != null)
                textures.put(name, new Texture(file));
            else
                textures.put(name, null);
        }
        return textures.get(name);
    }

    public void dispose() {
        // Called at shutdown

        // Graphics and sound resources aren't managed by Java's garbage collector, so
        // they must generally be disposed of manually when no longer needed. But,
        // any remaining resources are typically cleaned up automatically when the
        // application exits, so these aren't strictly necessary here.
        // (We might need to do something like this when loading a new game level in
        // a large game, for instance, or if the user switches to another application
        // temporarily (e.g., incoming phone call on a phone, or something).
        batch.dispose();
        shape.dispose();
        font.dispose();
        textures.values().forEach(tex -> {
            if (tex != null)
                tex.dispose();
        });
    }


    public void playSound() {
        // TODO: play different sounds
    }

    ///
    /// Example:
    ///
    /// {@snippet :
    /// appView.render(painter -> {
    /// painter.draw(x, y, w, h, tex1);
    /// painter.draw(x, y, w, h, tex2);
    /// });
    /// }
    ///
    /// TODO: probably easier to just accept a list of objects to draw.
    ///
    /// See lectures for better examples.
    ///
    /// Doing it this way has the advantage of making sure that SpriteBatch's begin()
    /// and end() methods are called properly.
    ///
    public void render(AppWorld world, float delta) {
        // Called when the application should draw a new frame (many times per second).

        // Start with a blank screen
        ScreenUtils.clear(Color.BLACK);

        // Camera
        camera.update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shape.setProjectionMatrix(viewport.getCamera().combined);

        // Draw background
        batch.begin();
        try {
            Texture background = getTexture("maps/level1_heaven/background.png");
            if (background != null) {
                batch.draw(background, 0, 0, (float) worldWidth(), (float) worldHeight());
            }
        } finally {
            batch.end();
        }

        // Tiled Map
        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        // Entities
        
        // PLAYER
        batch.begin();
        try {
            for (EntityModel obj : world) {
                if (obj.type().equals(EntityType.PLAYER)) {
                    characterView.draw(batch, obj, delta);
                }
            }

        } finally {
        	batch.end();
        }
        
        // PROJECTILES
        shape.begin(ShapeRenderer.ShapeType.Filled);
        try {
            for (EntityModel obj : world) {
                if (obj.type() == EntityType.PROJECTILE) {
                    shape.setColor(Color.YELLOW);
                    shape.rect(obj.x() - 12.5f * scale, obj.y() + 125 * scale, obj.width(), obj.height());
                }
                // Debugging code used to see center of player
                // Remove once game is finished
                //if (obj.type() == EntityType.PLAYER) {
                //    shape.setColor(Color.RED);
                //    shape.circle(obj.x(), obj.y(), 3);
                //}
                //shape.setColor(Color.GREEN);
                //shape.rectLine(obj.x(), obj.y(), obj.x(), obj.y() + 100, 1);
            }
        } finally {
            shape.end();
        }


    }

    public void resize(int width, int height) {
        // Called whenever the window is resized (including with its original size at
        // startup)

        viewport.update(width, height, true);
        // System.out.printf("%dx%d, %fx%f%n", viewport.getScreenWidth(),
        // viewport.getScreenHeight(),
        // viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    // If we use ExtendViewport, the world size might change when the window is
    // resize, so the controller needs access to this information
    public double worldWidth() {
        return viewport.getWorldWidth();
    }

    public double worldHeight() {
        return viewport.getWorldHeight();
    }

    public Stage getStage() {return new Stage(viewport, batch); }

}
