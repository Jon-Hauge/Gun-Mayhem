package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.characters.CharacterModel;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.view.screens.activeGameView.animations.*;

public class CharacterView {
    private CharacterRig characterRig;
    private AbstractAnimation idleAnimation;
    private AbstractAnimation walkingAnimation;
    private AbstractAnimation jumpingAnimation;
    private AbstractAnimation fallingAnimation;
    private AbstractAnimation deathAnimation;

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
        deathAnimation = new DeathAnimation();
    }

    /// Draws the view on a canvas
    ///
    /// @param batch Canvas to draw on
    /// @param obj Entity model to draw player-state from
    /// @param delta Time passed in rendering
    ///
    public void draw(SpriteBatch batch, CharacterModel obj, float delta) {
        characterRig.resetOffsets();

        AbstractAnimation currAnimation = switch(obj.state()) {
            case WALKING ->  walkingAnimation;
            case JUMPING ->   jumpingAnimation;
            case FALLING ->   fallingAnimation;
            case RECENTLY_DEAD -> deathAnimation;
            default ->  idleAnimation;
        };

        boolean recentlyDead = obj.state() == EntityState.RECENTLY_DEAD;

        batch.end();
        if (!recentlyDead) {
            currAnimation.update(characterRig, obj.x(), obj.y(), delta, batch.getProjectionMatrix());
        }
        batch.begin();

        boolean mirrored = obj.getDirection() == -1;
        AbstractWeapon weapon = obj.getEquippedWeapon();

        characterRig.draw(batch, obj.x(), obj.y(), mirrored, weapon);

        if (recentlyDead) {
            batch.end();
            currAnimation.update(characterRig,
                    obj.x(),
                    obj.y(),
                    delta,
                    batch.getProjectionMatrix()
            );
            batch.begin();
        }

    }
}
