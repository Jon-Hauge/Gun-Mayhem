package inf112.mayhem.model.weapons.longrange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.weapons.WeaponType;
import inf112.mayhem.model.weapons.guns.SniperWeapon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import inf112.mayhem.model.entities.characters.PlayerClass;

class SniperWeaponTest {

    private SniperWeapon sniper;

    @BeforeEach
    void setUp() {
        sniper = new SniperWeapon(2f, 3f);
    }

    @Test
    void constructorSetsPositionAndSize() {
        assertEquals(2f, sniper.x());
        assertEquals(3f, sniper.y());
        assertEquals(25f, sniper.width());
        assertEquals(15f, sniper.height());
    }

    @Test
    void constructorSetsWeaponSpecificValues() {
        assertEquals(WeaponType.SNIPER, sniper.getWeaponType());
        assertEquals(10, sniper.getMaxAmmo());
        assertEquals(10, sniper.getCurrentAmmo());
        assertEquals(3f, sniper.getFiringDelay());
    }
}