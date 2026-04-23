package inf112.mayhem.model.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import inf112.mayhem.model.PlayerClass;

class SniperWeaponTest {

    private PlayerClass player;
    private SniperWeapon sniper;

    @BeforeEach
    void setUp() {
        player = mock(PlayerClass.class);
        sniper = new SniperWeapon(2f, 3f, player);
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
        assertEquals(200f, sniper.getKnockBackMultiplier());
        assertEquals(800f, sniper.getBulletSpeed());
        assertSame(player, sniper.getPlayer());
    }
}