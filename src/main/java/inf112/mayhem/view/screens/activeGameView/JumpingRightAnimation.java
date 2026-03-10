package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class JumpingRightAnimation extends AbstractAnimation {
    private Texture texture;

    public JumpingRightAnimation() {
        this.texture = new Texture(Gdx.files.internal("jumping_right_animation.png"));
    }

    @Override
    public Texture getAnimationTexture() {
        return this.texture;
    }
}
