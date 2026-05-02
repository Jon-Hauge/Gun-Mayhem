package inf112.mayhem.model.entities.powerUp;

import inf112.mayhem.model.entities.characters.AbstractCharacter;

public class LowGravityPowerUp implements PowerUp {
    private final static float GRAVITYMULTIPLIER = 0.5f;

    /// Initiates an instance of a low gravity powerUp.
    /// If character activates a powerUp, they receive its benefits.
    ///
    public LowGravityPowerUp() {}

    @Override
    public void applyPowerUp(AbstractCharacter character) {
        character.setGravityMultiplier(GRAVITYMULTIPLIER);
    }

    @Override
    public void removePowerUp(AbstractCharacter character) {
        character.setGravityMultiplier(1f);
    }
}
