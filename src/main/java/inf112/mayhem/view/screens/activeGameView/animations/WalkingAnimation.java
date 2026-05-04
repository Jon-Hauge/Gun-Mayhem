package inf112.mayhem.view.screens.activeGameView.animations;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public class WalkingAnimation extends AbstractAnimation {

    @Override
    protected void animate(CharacterRig character, float x, float y, float elapsedTime, Matrix4 projection) {
        float offsetLeft = MathUtils.sin(elapsedTime * 8f) * 5f;
        float offsetRight = MathUtils.cos(elapsedTime * 8f) * 5f;

        character.leftFoot.addOffset(offsetLeft, offsetLeft / 2);
        character.rightFoot.addOffset(offsetRight, offsetRight / 2);
    }
}
