package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.characters.CharacterModel;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;
import inf112.mayhem.view.screens.activeGameView.animations.*;

public class CharacterView {
    private final CharacterRig characterRig;
    private final AbstractAnimation idleAnimation;
    private final AbstractAnimation walkingAnimation;
    private final AbstractAnimation jumpingAnimation;
    private final AbstractAnimation deathAnimation;

    /// Initiates a character and its animations
    ///
    /// @param characterRig Character to be added to view
    ///
    public CharacterView(CharacterRig characterRig) {
        this.characterRig = characterRig;

        idleAnimation = new IdleAnimation();
        walkingAnimation = new WalkingAnimation();
        jumpingAnimation = new JumpingAnimation();
        deathAnimation = new DeathAnimation();
    }

    /// Draws the character on a canvas
    ///
    /// @param batch Canvas to draw on
    /// @param obj Entity model to draw player-state from
    /// @param delta Time passed in rendering
    ///
    public void draw(SpriteBatch batch, CharacterModel obj, float delta) {
        characterRig.resetOffsets();

        AbstractAnimation currAnimation = switch(obj.state()) {
            case WALKING -> walkingAnimation;
            case JUMPING, FALLING -> jumpingAnimation;
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
