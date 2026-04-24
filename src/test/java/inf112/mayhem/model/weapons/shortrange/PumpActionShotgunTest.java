package inf112.mayhem.model.weapons.shortrange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.character.PlayerClass;
import inf112.mayhem.model.weapons.WeaponType;

class PumpActionTest {

    private PlayerClass player;
    private AppWorld world;
    private PumpAction weapon;

    @BeforeEach
    void setUp() {
        player = mock(PlayerClass.class);
        world = mock(AppWorld.class);

        when(player.x()).thenReturn(11f);
        when(player.y()).thenReturn(22f);

        weapon = new PumpAction(1f, 2f, player);
    }

    @Test
    void constructorSetsExpectedValues() {
        assertEquals(25f, weapon.width());
        assertEquals(10f, weapon.height());
        assertEquals(WeaponType.SHOTGUN, weapon.getWeaponType());
        assertEquals(5, weapon.getMaxAmmo());
        assertEquals(5, weapon.getCurrentAmmo());
        assertEquals(8f, weapon.getFiringDelay());
        assertEquals(0f, weapon.getBulletSpeed());
        assertEquals(0f, weapon.getKnockBackMultiplier());
    }

    @Test
    void constructorSetsAttackBoundsFromPlayerPosition() {
        Rectangle bounds = weapon.getAttackBounds();

        assertEquals(11f, bounds.x);
        assertEquals(22f, bounds.y);
        assertEquals(25f, bounds.width);
        assertEquals(10f, bounds.height);
    }

    @Test
    void updateMovesAttackBoundsToPlayerPosition() {
        when(player.x()).thenReturn(100f);
        when(player.y()).thenReturn(200f);

        weapon.update(world, 0.016f);

        Rectangle bounds = weapon.getAttackBounds();
        assertEquals(100f, bounds.x);
        assertEquals(200f, bounds.y);
        assertEquals(25f, bounds.width);
        assertEquals(10f, bounds.height);
    }

    @Test
    void shootReducesAmmo() {
        assertTrue(weapon.shoot());
        assertEquals(4, weapon.getCurrentAmmo());
    }
}