package inf112.mayhem.view.screens.activeGameView.animations;

import com.badlogic.gdx.math.Matrix4;
import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public class FallingAnimation extends AbstractAnimation {

    @Override
    protected void animate(CharacterRig character, float x, float y, float elapsedTime, Matrix4 projection) {
        character.body.addOffset(0, -10f * character.body.getScale());
        character.head.addOffset(0, -10f * character.head.getScale());
        character.face.addOffset(0, -10f * character.face.getScale());

        character.leftFoot.addOffset(0, 8f * character.leftFoot.getScale());
        character.rightFoot.addOffset(0, 8f * character.rightFoot.getScale());
    }
}
