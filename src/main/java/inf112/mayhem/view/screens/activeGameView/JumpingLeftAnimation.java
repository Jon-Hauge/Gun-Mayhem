package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class JumpingLeftAnimation extends AbstractAnimation {
    private Texture texture;

    public JumpingLeftAnimation() {
        this.texture = new Texture(Gdx.files.internal("jumping_left_animation.png"));
    }

    @Override
    public Texture getAnimationTexture() {
        return this.texture;
    }
}
