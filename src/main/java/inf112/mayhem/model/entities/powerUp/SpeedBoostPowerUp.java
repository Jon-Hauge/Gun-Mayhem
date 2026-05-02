package inf112.mayhem.model.entities.powerUp;


import inf112.mayhem.model.entities.characters.AbstractCharacter;

public class SpeedBoostPowerUp implements PowerUp {
    private final static float SPEEDBOOSTMULTIPLIER = 1.5f;

    /// Initiates an instance of a speedboost powerUp.
    /// If character activates a powerUp, they receive its benefits.
    ///
    public SpeedBoostPowerUp() {}

    @Override
    public void applyPowerUp(AbstractCharacter character) {
        character.setSpeedMultiplier(SPEEDBOOSTMULTIPLIER);
    }

    @Override
    public void removePowerUp(AbstractCharacter character) {
        character.setSpeedMultiplier(1f);
    }
}
