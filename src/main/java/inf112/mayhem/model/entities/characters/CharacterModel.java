package inf112.mayhem.model.entities.characters;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.weapons.AbstractWeapon;

public interface CharacterModel extends EntityModel {
    /// Initates the jump action for the character
    /// High aggression enemies (level 3 and above) are capable of double jumping.
    ///
    void jump();

    /// Initates the drop action for the character.
    /// Allows them to drop through platforms.
    ///
    void drop();

    /// Initates the hit action for the character.
    ///
    /// When hit by projectile: applies knockback based on the projectile's weapon.
    /// For players, the enemy's aggression level factors in to.
    ///
    /// @param obj Object character is hit by.
    ///
    void hit(EntityModel obj);

    /// Sets horizontal direction of character
    ///
    /// @param n -1 turns character left, 1 turns character right
    ///
    void setDirection(int n);

    /// Initiates the death action for the character.
    /// Used to check how many lives each character has and who wins the game.
    ///
    void die();

    /// Initiates the respawn action for the character.
    /// Allows the character to respawn after death provided they have lives left.
    ///
    void respawn();

    /// Sets the amount of lives of the character
    ///
    /// @param lives Lives to set for character
    ///
    void setLives(int lives);

    /// Checks if character is out of the bounds of the gameworld
    ///
    /// @param world Gameworld with its respective bounds
    ///
    boolean outOfBounds(AppWorld world);

    ///
    /// @return Direction of character: -1 for left, 1 for right
    ///
    int getDirection();

    ///
    /// @return Amount of lives character has left
    ///
    int lives();

    /// Equips weapon provided it is dropped
    ///
    /// @param weapon Weapon to be picked up
    ///
    void equipWeapon(AbstractWeapon weapon);

    ///
    /// @return Currently equipped weapon
    ///
    AbstractWeapon getEquippedWeapon();
}