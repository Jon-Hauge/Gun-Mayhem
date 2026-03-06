package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AbstractAnimation {
    private Animation<TextureRegion> animation;

    public void createAnimation(Texture texture) {
        int frameWidth = texture.getWidth() / 4;
        int frameHeight = texture.getHeight();
        TextureRegion[][] tmp = TextureRegion.split(texture, frameWidth, frameHeight);
        TextureRegion[] frames = tmp[0];
        this.animation = new Animation<TextureRegion>(0.1f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public abstract Texture getAnimationTexture();

}
