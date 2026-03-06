package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WalkingLeftAnimation  extends AbstractAnimation {
    private Texture texture;

    public WalkingLeftAnimation() {
        this.texture = new Texture(Gdx.files.internal("walking_left.png"));
    }

    @Override
    public Texture getAnimationTexture() {
        return this.texture;
    }
}
