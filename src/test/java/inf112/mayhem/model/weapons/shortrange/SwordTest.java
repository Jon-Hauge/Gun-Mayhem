package inf112.mayhem.model.weapons.shortrange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.PlayerClass;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.weapons.WeaponType;

class SwordTest {

    private PlayerClass player;
    private AppWorld world;
    private Sword sword;

    @BeforeEach
    void setUp() {
        player = mock(PlayerClass.class);
        world = mock(AppWorld.class);

        when(player.x()).thenReturn(9f);
        when(player.y()).thenReturn(12f);

        sword = new Sword(
                1f,
                2f,
                player,
                8f,
                12f,
                WeaponType.SWORD,
                EntityState.IN_USE,
                -1,
                0.3f
        );
    }

    @Test
    void constructorUsesGivenValuesFromSuper() {
        assertEquals(1f, sword.x());
        assertEquals(2f, sword.y());
        assertEquals(8f, sword.width());
        assertEquals(12f, sword.height());
        assertEquals(WeaponType.SWORD, sword.getWeaponType());
        assertEquals(-1, sword.getMaxAmmo());
        assertEquals(-1, sword.getCurrentAmmo());
        assertEquals(0.3f, sword.getFiringDelay());
    }

    @Test
    void constructorSetsAttackBoundsFromPlayerPositionWithFixedSwordSize() {
        Rectangle bounds = sword.getAttackBounds();

        assertEquals(9f, bounds.x);
        assertEquals(12f, bounds.y);
        assertEquals(8f, bounds.width);
        assertEquals(12f, bounds.height);
    }

    @Test
    void updateMovesAttackBoundsToPlayerPosition() {
        when(player.x()).thenReturn(100f);
        when(player.y()).thenReturn(150f);

        sword.update(world, 0.016f);

        Rectangle bounds = sword.getAttackBounds();
        assertEquals(100f, bounds.x);
        assertEquals(150f, bounds.y);
        assertEquals(8f, bounds.width);
        assertEquals(12f, bounds.height);
    }

    @Test
    void infiniteAmmoSwordCanShootWithoutReducingAmmo() {
        assertTrue(sword.shoot());
        assertEquals(-1, sword.getCurrentAmmo());
    }
}