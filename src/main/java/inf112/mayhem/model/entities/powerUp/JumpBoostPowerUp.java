package inf112.mayhem.model.entities.powerUp;

import inf112.mayhem.model.entities.characters.AbstractCharacter;

public class JumpBoostPowerUp implements PowerUp {
    private final static float JUMPBOOSTMULTIPLIER = 1.5f;

    /// Initiates an instance of a jumpboost powerUp.
    /// If character activates a powerUp, they receive its benefits.
    ///
    public JumpBoostPowerUp() {}

    @Override
    public void applyPowerUp(AbstractCharacter character) {
        character.setJumpBoostMultiplier(JUMPBOOSTMULTIPLIER);
    }

    @Override
    public void removePowerUp(AbstractCharacter character) {
        character.setJumpBoostMultiplier(1f);
    }
}
