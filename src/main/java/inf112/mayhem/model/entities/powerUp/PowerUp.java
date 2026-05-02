package inf112.mayhem.model.entities.powerUp;

import inf112.mayhem.model.entities.characters.AbstractCharacter;

public interface PowerUp {

    /// Applies powerUp to character
    ///
    /// @param character Character to apply powerUp to
    ///
    void applyPowerUp(AbstractCharacter character);

    /// Removes powerUp from character
    ///
    /// @param character Character to remove powerUp from
    ///
    void removePowerUp(AbstractCharacter character);
}
