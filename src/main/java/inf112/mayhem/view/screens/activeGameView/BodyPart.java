package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BodyPart {
    private Texture texture;
    private float offsetX, offsetY;
    private float rotation;
    private float scaleX = 1f, scaleY = 1f;

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
        this.scaleX = scale;
        this.scaleY = scale;
    }

    /// Sets scale of bodypart.
    /// Allows separate scaling for x and y coordinates.
    ///
    /// @param scaleX Multiplicative bodypart is scaled with horizontally
    /// @param scaleY Multiplicative bodypart is scaled with vertically
    ///
    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
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
        offsetX += x;
        offsetY += y;
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

        float drawX = mirrored ? x + offsetX - width / 2  : x - offsetX - width / 2;
        float drawY = y + offsetY - width / 2;

        batch.draw(texture,
                drawX,
                drawY,
                width / 2f,
                height / 2f,
                width,
                height,
                scaleX,
                scaleY,
                mirrored ? -rotation : rotation,
                0, 0,
                (int) width,
                (int) height,
                mirrored,
                false);
    }
}
