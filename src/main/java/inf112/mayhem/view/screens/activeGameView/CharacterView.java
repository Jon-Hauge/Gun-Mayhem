package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.mayhem.model.EntityModel;
import inf112.mayhem.view.screens.activeGameView.animations.AbstractAnimation;
import inf112.mayhem.view.screens.activeGameView.animations.IdleAnimation;
import inf112.mayhem.view.screens.activeGameView.animations.JumpingAnimation;
import inf112.mayhem.view.screens.activeGameView.animations.WalkingAnimation;

public class CharacterView {
    private CharacterRig characterRig;
    private AbstractAnimation idleAnimation;
    private AbstractAnimation walkingAnimation;
    private AbstractAnimation jumpingAnimation;
    private AbstractAnimation fallingAnimation;

    /// Inititates a character and its animations
    ///
    /// @param characterRig Character to be added to view
    ///
    public CharacterView(CharacterRig characterRig) {
        this.characterRig = characterRig;

        idleAnimation = new IdleAnimation();
        walkingAnimation = new WalkingAnimation();
        jumpingAnimation = new JumpingAnimation();
        fallingAnimation = new JumpingAnimation();
    }

    /// Draws the view on a canvas
    ///
    /// @param batch Canvas to draw on
    /// @param entity Entity model to draw player-state from
    /// @param delta Time passed in rendering
    ///
    public void draw(SpriteBatch batch, EntityModel entity, float delta) {
        characterRig.resetOffsets();

        AbstractAnimation currAnimation = switch(entity.state()) {
            case WALKING ->  walkingAnimation;
            case JUMPING ->   jumpingAnimation;
            case FALLING ->   fallingAnimation;
            default ->  idleAnimation;
        };

        currAnimation.update(characterRig, delta);
        boolean mirrored = entity.getDirection() == -1;
        characterRig.draw(batch, entity.x(), entity.y(), mirrored);
    }
}
