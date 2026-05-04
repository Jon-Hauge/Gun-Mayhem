package inf112.mayhem.view.screens.activeGameView.animations;

import com.badlogic.gdx.math.Matrix4;
import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public abstract class AbstractAnimation {
    private float elapsedTime = 0;

    /// Updates animations each gametic, i.e. since last update
    ///
    /// @param character Character-rig to update
    /// @param x Horizontal coordinate of character to update
    /// @param y Vertical coordinate of character to update
    /// @param delta One gametic
    /// @param projection Projection to use to match shapes to correct coordinates
    ///
    public void update(CharacterRig character, float x, float y, float delta, Matrix4 projection) {
        elapsedTime += delta;
        animate(character, x, y, elapsedTime, projection);
    }

    /// Resets elapsed time to update animations correctly
    ///
    public void reset() {
        elapsedTime = 0f;
    }

    /// Animates the character by adding offset to the assets.
    ///
    /// @param character The character, whose parts need to be animated
    /// @param x Horizontal coordinate of character to animate
    /// @param y Vertical coordinate of character to animate
    /// @param elapsedTime Time passed in rendering
    /// @param projection Projection to use to match shapes to correct coordinates
    ///
    protected abstract void animate(CharacterRig character, float x, float y, float elapsedTime, Matrix4 projection);
}
