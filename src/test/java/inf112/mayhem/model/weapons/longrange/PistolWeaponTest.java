package inf112.mayhem.model.weapons.longrange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.weapons.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import inf112.mayhem.model.entities.characters.PlayerClass;

class PistolWeaponTest {

    private AbstractCharacter character;
    private PistolWeapon pistol;

    @BeforeEach
    void setUp() {
        character = mock(AbstractCharacter.class);
        pistol = new PistolWeapon(5f, 7f, character);
    }

    @Test
    void constructorSetsPositionAndSize() {
        assertEquals(5f, pistol.x());
        assertEquals(7f, pistol.y());
        assertEquals(10f, pistol.width());
        assertEquals(10f, pistol.height());
    }

    @Test
    void constructorSetsWeaponSpecificValues() {
        assertEquals(WeaponType.PISTOL, pistol.getWeaponType());
        assertEquals(-1, pistol.getMaxAmmo());
        assertEquals(-1, pistol.getCurrentAmmo());
        assertEquals(1f, pistol.getFiringDelay());
        assertEquals(300f, pistol.getKnockBackMultiplier());
        assertEquals(500f, pistol.getBulletSpeed());
        assertSame(character, pistol.getCharacter());
    }

}