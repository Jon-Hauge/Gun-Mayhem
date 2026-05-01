package inf112.mayhem.view.screens.activeGameView.animations;

import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public abstract class AbstractAnimation {
    private float elapsedTime = 0;

    /// Updates animations each gametic, i.e. since last update
    ///
    /// @param character Character-rig to update
    /// @param delta One gametic
    ///
    public void update(CharacterRig character, float delta) {
        elapsedTime += delta;
        animate(character, elapsedTime);
    }

    /// Animates the character by adding offset to the assets.
    ///
    /// @param character The character, whose parts need to be animated
    /// @param elapsedTime Time passed in rendering
    ///
    protected abstract void animate(CharacterRig character, float elapsedTime);
}
