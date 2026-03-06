package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WalkingRightAnimation extends AbstractAnimation {
    private Texture texture;

    public WalkingRightAnimation() {
        this.texture = new Texture(Gdx.files.internal("walking_right.png"));
    }

    @Override
    public Texture getAnimationTexture() {
        return this.texture;
    }
}
