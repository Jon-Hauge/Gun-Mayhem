package inf112.mayhem.view.screens.activeGameView.animations;

import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public class FallingAnimation extends AbstractAnimation {

    @Override
    protected void animate(CharacterRig character, float elapsedTime) {
        character.body.addOffset(0, -10f * character.body.getScaleY());
        character.head.addOffset(0, -10f * character.head.getScaleY());
        character.face.addOffset(0, -10f * character.face.getScaleY());

        character.leftFoot.addOffset(0, 8f * character.leftFoot.getScaleY());
        character.rightFoot.addOffset(0, 8f * character.rightFoot.getScaleY());
    }
}
