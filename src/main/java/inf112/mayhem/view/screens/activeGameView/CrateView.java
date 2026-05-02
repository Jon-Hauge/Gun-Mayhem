package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.mayhem.model.entities.powerUp.Crate;

public class CrateView {

    private final Crate crate;
    private final Texture texture;

    /// Constructs a CrateView to display falling crates.
    ///
    /// @param crate Crate to display
    ///
    public CrateView(Crate crate) {
        this.crate = crate;
        this.texture = new Texture("crate/crate.png");
    }

    /// Draws the crate in the gameworld
    ///
    /// @param batch Batch to draw on
    ///
    public void draw(SpriteBatch batch) {
        batch.draw(texture,
                crate.x(),
                crate.y(),
                crate.width(),
                crate.height());
    }
}

