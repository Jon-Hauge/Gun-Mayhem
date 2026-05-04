package inf112.mayhem.view.screens.activeGameView.animations;

import com.badlogic.gdx.math.Matrix4;
import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public class JumpingAnimation extends AbstractAnimation {

    @Override
    public void animate(CharacterRig character, float x, float y, float elapsedTime, Matrix4 projection) {

        character.body.addOffset(0, 20f * character.body.getScale());
        character.head.addOffset(0, 20f * character.head.getScale());
        character.face.addOffset(0, 20f * character.face.getScale());

        float rightFootScale = character.rightFoot.getScale();
        float leftFootScale = character.leftFoot.getScale();

        float rightFootLift = (float) Math.sin(elapsedTime * 3f) * 12f * rightFootScale;
        float leftFootLift = (float) Math.sin(elapsedTime * 3f) * 8f * leftFootScale;

        character.rightFoot.addOffset(0, rightFootLift);
        character.leftFoot.addOffset(0, leftFootLift);
    }
}
