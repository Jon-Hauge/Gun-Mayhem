package inf112.mayhem.view.screens.activeGameView.animations;

import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public class JumpingAnimation extends AbstractAnimation {

    @Override
    public void animate(CharacterRig character, float elapsedTime) {

        character.body.addOffset(0, 20f * character.body.getScaleY());
        character.head.addOffset(0, 20f * character.head.getScaleY());
        character.face.addOffset(0, 20f * character.face.getScaleY());

        float rightFootScale = character.rightFoot.getScaleY();
        float leftFootScale = character.leftFoot.getScaleY();

        float rightFootLift = (float) Math.sin(elapsedTime * 3f) * 12f * rightFootScale;
        float leftFootLift = (float) Math.sin(elapsedTime * 3f) * 8f * leftFootScale;

        character.rightFoot.addOffset(0, rightFootLift);
        character.leftFoot.addOffset(0, leftFootLift);
    }
}
