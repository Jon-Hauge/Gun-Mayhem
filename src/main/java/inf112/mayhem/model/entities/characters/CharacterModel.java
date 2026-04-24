package inf112.mayhem.model.entities.characters;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.weapons.AbstractWeapon;

public interface CharacterModel extends EntityModel {
    void jump();
    void drop();
    void hit(EntityModel obj);
    void setDirection(int n);
    void die();
    void respawn();
    void setLives(int lives);
    boolean outOfBounds(AppWorld world);
    int getDirection();
    int lives();
    void equipWeapon(AbstractWeapon weapon);
    AbstractWeapon getEquippedWeapon();
}