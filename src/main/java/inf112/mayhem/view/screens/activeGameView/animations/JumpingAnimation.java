package inf112.mayhem.view.screens.activeGameView.animations;

import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public class JumpingAnimation extends AbstractAnimation {

    @Override
    public void animate(CharacterRig character, float elapsedTime) {
        character.leftFoot.addOffset(0, 5);
        character.rightFoot.addOffset(0, 5);
        character.head.addOffset(0, 0.5f);
    }
}
