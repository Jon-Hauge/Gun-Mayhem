package inf112.mayhem.view.screens.activeGameView.animations;

import com.badlogic.gdx.math.MathUtils;
import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public class IdleAnimation extends AbstractAnimation {

    @Override
    protected void animate(CharacterRig character, float elapsedTime) {
        float offset = MathUtils.sin(elapsedTime * 2f) * 2f;

        character.head.addOffset(0, offset);
        character.face.addOffset(0, offset);
        character.body.addOffset(0, offset);
    }
}
