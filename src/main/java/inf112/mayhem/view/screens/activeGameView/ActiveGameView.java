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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.mayhem.controller.AppController;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.characters.CharacterModel;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.view.maps.LevelLoader;
import inf112.mayhem.view.maps.TileMapHelper;

public class ActiveGameView {
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private BitmapFont font;
    private final ShapeRenderer shape;
    private final Map<String, Texture> textures = new HashMap<>();
    private final Map<AbstractWeapon, WeaponView> weaponViews = new HashMap<>();
    private final AppController controller;
    private final CharacterView characterView;
    private final float scale = 0.15f;
    private float smoothCamX = 0;
    private float smoothCamY = 0;
    private final LevelLoader lvlLoader;

    //TILEMAP
    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    /// Initiates the view for an active game of the app.
    ///
    /// @param world Gameworld to display
    /// @param controller Controller to control actions in the display
    ///
    public ActiveGameView(AppWorld world, AppController controller) {
        this.controller = controller;

        // To preserve aspect ratio, use FitViewport, ExtendViewport or FillViewport.
        // FitViewport keeps aspect by leaving empty space.
        // ExtendViewport keeps aspect by extending the world size in one direction.
        // FillViewport keeps aspect by hiding part of the world in the other direction.
        this.viewport = new FillViewport((float) world.width(), (float) world.height());
        this.camera = (OrthographicCamera) viewport.getCamera();
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();

        // LOAD ANIMATIONS
        CharacterRig characterRig = initiateCharacterRig();
        this.characterView = new CharacterView(characterRig);

        
        //LOAD TILEDMAP
        TileMapHelper tileMapHelper = new TileMapHelper();
        this.lvlLoader = new LevelLoader();
        tileMapHelper.setupMap(lvlLoader.getMapPath(controller.getCurrentLevelNumber()));
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

    private void preloadTextures() {
        for (String fileName : List.of(
                "maps/level1_heaven/background.png",
                "maps/level1_heaven/bg1.png",
                "maps/level1_heaven/bg2.png",
                "maps/level1_heaven/bg3.png",
                "maps/level1_heaven/bg4.png",

                "maps/level2_midnightcity/bg1.png",
                "maps/level2_midnightcity/bg2.png",
                "maps/level2_midnightcity/bg3.png",
                "maps/level2_midnightcity/bg4.png",
                "maps/level2_midnightcity/bg5.png",
                "maps/level2_midnightcity/bg6.png",


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

    /// Disposes all elements in stage
    ///
    public void dispose() {
        batch.dispose();
        shape.dispose();
        font.dispose();
        textures.values().forEach(tex -> {
            if (tex != null)
                tex.dispose();
        });
    }


    /// Plays a sound
    ///
    public void playSound() {
        // TODO: play different sounds
    }

    /// Renders the game world.
    /// Happens multiple times per second.
    ///
    /// @param world gameworld to render in
    /// @param delta elapsed time
    ///
    public void render(AppWorld world, float delta) {

        // Start with a blank screen
        ScreenUtils.clear(Color.BLACK);

        // Update camera with zoom-in
        updateCamera(world);

        // Draw background
        drawBackground();

        // Tiled Map
        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        // Draw entities
        drawEntities(world, delta);
    }

    private void updateCamera (AppWorld world) {
        float minX = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        float sumX = 0;
        float sumY = 0;
        int characterCount = 0;

        for (EntityModel obj : world) {
            if ((obj.type() == EntityType.PLAYER || obj.type() == EntityType.ENEMY) && obj.isActive()) {
                float x = obj.x();
                float y = obj.y();

                minX = Math.min(minX, x);
                maxX = Math.max(maxX, x);
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);

                sumX += x;
                sumY += y;

                characterCount++;
            }
        }

        if (characterCount > 0) {
            float avgX = sumX / characterCount;
            float avgY = sumY / characterCount;

            camera.position.set(avgX, avgY, 0);

            float width = maxX - minX;
            float height = maxY - minY;

            float padding = 2500f * scale;
            width += padding;
            height += padding;

            float zoomX = width / camera.viewportWidth;
            float zoomY = height / camera.viewportHeight;

            float targetZoom = Math.max(zoomX, zoomY);
            targetZoom = Math.max(0.5f, Math.min(targetZoom, 2.5f));
            camera.zoom += (targetZoom - camera.zoom) * 0.1f;

            smoothCamX += (camera.position.x - smoothCamX) * 0.1f;
            smoothCamY += (camera.position.y - smoothCamY) * 0.1f;
        }

        camera.update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shape.setProjectionMatrix(viewport.getCamera().combined);
    }

    private void drawBackground() {
        batch.begin();
        try {
            float viewWidth = camera.viewportWidth * camera.zoom;
            float viewHeight = camera.viewportHeight * camera.zoom;

            float camLeft = camera.position.x - (viewWidth / 2f);
            float camBottom = camera.position.y - (viewHeight / 2f);

            List<String> backgroundComponents = lvlLoader.getMapBackgroundPaths(controller.getCurrentLevelNumber());
            float pFactor = 0.1f;
            for(String path : backgroundComponents){
                drawLayer(getTexture(path), camLeft, camBottom, viewWidth, viewHeight, pFactor, 5.0f);
                pFactor += 0.1f;
            }
        }
        finally {
            batch.end();
        }
    }

    private void drawEntities(AppWorld world, float delta) {
        batch.begin();
        try {

            for (EntityModel obj : world) {

                if (obj instanceof CharacterModel character && character.isActive()) {
                    characterView.draw(batch, character, delta);
                }

                if (obj instanceof AbstractWeapon weapon) {
                	if (weapon.state() == EntityState.DROPPED) {
                		weaponViews.computeIfAbsent(weapon, WeaponView::new).draw(batch);
                	}
                }
            }

        } finally {
            batch.end();
        }

        // PROJECTILES
        shape.begin(ShapeRenderer.ShapeType.Filled);
        try {
            for (EntityModel obj : world) {
                if (obj.type() == EntityType.PROJECTILE && obj.isActive()) {
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

    private void drawLayer(Texture tex, float camLeft, float camBottom, float vW, float vH, float pFactor, float texScale) {
        if (tex == null) return;

        if (tex.getUWrap() != Texture.TextureWrap.Repeat || tex.getVWrap() != Texture.TextureWrap.Repeat) {
            tex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }

        float texWorldWidth = tex.getWidth() * texScale;
        float texWorldHeight = tex.getHeight() * texScale;

        float viewSpanU = vW / texWorldWidth;
        float viewSpanV = vH / texWorldHeight;

        float offsetU = (smoothCamX * pFactor) / texWorldWidth;
        float offsetV = (smoothCamY * pFactor) / texWorldHeight;

        float u = 0.5f + offsetU - (viewSpanU / 2f);
        float v = 0.5f + offsetV - (viewSpanV / 2f);

        float u2 = u + viewSpanU;
        float v2 = v + viewSpanV;

        batch.draw(tex,
                camLeft, camBottom,
                vW, vH,
                u, v2, u2, v
        );
    }

    /// Resizes screen to account for changes in resolution.
    ///
    /// @param width Width of the resized screen
    /// @param height Weight of the resized screen
    ///
    public void resize(int width, int height) {
        // Called whenever the window is resized (including with its original size at
        // startup)
        viewport.update(width, height, true);
        // System.out.printf("%dx%d, %fx%f%n", viewport.getScreenWidth(),
        // viewport.getScreenHeight(),
        // viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    ///
    /// @return current stage
    ///
    public Stage getStage() {return new Stage(viewport, batch); }
}
