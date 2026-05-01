package inf112.mayhem.model.weapons.longrange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.weapons.WeaponType;
import inf112.mayhem.model.weapons.guns.PistolWeapon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import inf112.mayhem.model.entities.characters.PlayerClass;

class PistolWeaponTest {

    private PistolWeapon pistol;

    @BeforeEach
    void setUp() {
        pistol = new PistolWeapon(5f, 7f);
    }

    @Test
    void constructorSetsPositionAndSize() {
        assertEquals(5, pistol.x());
        assertEquals(7, pistol.y());
        assertEquals(10, pistol.width());
        assertEquals(10, pistol.height());
    }

    @Test
    void constructorSetsWeaponSpecificValues() {
        assertEquals(WeaponType.PISTOL, pistol.getWeaponType());
        assertEquals(-1, pistol.getMaxAmmo());
        assertEquals(-1, pistol.getCurrentAmmo());
        assertEquals(1f, pistol.getFiringDelay());
    }

}