package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BodyPart {
    private Texture texture;
    private float offsetX, offsetY;
    private float rotation;
    private float scale = 1f;

    /// Initiates a bodypart for a given texture.
    /// Acts as one part of a character.
    ///
    /// @param texture Texture of the bodypart
    /// @param offsetX How far the bodypart is offset horizontally
    /// @param offsetY How far the bodypart is offset vertically
    ///
    public BodyPart(Texture texture, float offsetX, float offsetY) {
        this.texture = texture;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /// Sets scale of bodypart.
    /// Allows only same scaling for x and y coordinates.
    ///
    /// @param scale Multiplicative bodypart is scaled with
    ///
    public void setScale(float scale) {
        this.scale = scale;
    }

    /// Sets amount of offset for bodypart.
    ///
    /// @param x Offset horizontally
    /// @param y Offset vertically
    ///
    public void setOffset(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    /// Adds offset for bodypart.
    ///
    /// @param x Offset added horizontally
    /// @param y Offset added vertically
    ///
    public void addOffset(float x, float y) {
        this.offsetX += x;
        this.offsetY += y;
    }

    /// Draws bodypart on canvas.
    /// Is called multiple times per second.
    ///
    /// @param batch Canvas to draw on
    /// @param x Determines where to draw horizontally
    /// @param y Determines where to draw vertically
    /// @param mirrored Determines whether bodypart is drawn mirrored along x-axis
    ///
    public void draw(SpriteBatch batch, float x, float y, boolean mirrored) {

        float width = texture.getWidth();
        float height = texture.getHeight();

        float direction = mirrored ? -1f : 1f;

        float drawX = x + offsetX * direction - width / 2f;
        float drawY = y + offsetY - height / 2f;

        batch.draw(texture,
                drawX,
                drawY,
                width / 2f,
                height / 2f,
                width,
                height,
                scale,
                scale,
                mirrored ? -rotation : rotation,
                0, 0,
                (int) width,
                (int) height,
                mirrored,
                false);
    }

    ///
    /// @return Width of texture
    ///
    public float getWidth() {return texture.getWidth() * scale; }

    ///
    /// @return Height of texture
    ///
    public float getHeight() {
        return texture.getHeight() * scale;
    }

    ///
    /// @return Scale of texture
    ///
    public float getScale() {
        return scale;
    }
}
