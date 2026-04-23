package inf112.mayhem.model.weapons.longrange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import inf112.mayhem.model.weapons.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import inf112.mayhem.model.entities.character.PlayerClass;

class PistolWeaponTest {

    private PlayerClass player;
    private PistolWeapon pistol;

    @BeforeEach
    void setUp() {
        player = mock(PlayerClass.class);
        pistol = new PistolWeapon(5f, 7f, player);
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
        assertSame(player, pistol.getPlayer());
    }

}