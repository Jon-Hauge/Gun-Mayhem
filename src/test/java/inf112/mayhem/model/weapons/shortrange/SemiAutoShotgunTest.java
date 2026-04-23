package inf112.mayhem.model.weapons.shortrange;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.character.PlayerClass;
import inf112.mayhem.model.weapons.WeaponType;

class SemiAutoShotgunTest {

    private PlayerClass player;
    private AppWorld world;
    private SemiActionShotgun weapon;

    @BeforeEach
    void setUp() {
        player = mock(PlayerClass.class);
        world = mock(AppWorld.class);

        when(player.x()).thenReturn(15f);
        when(player.y()).thenReturn(25f);

        weapon = new SemiActionShotgun(3f, 4f, player);
    }

    @Test
    void constructorSetsExpectedValues() {
        assertEquals(25f, weapon.width());
        assertEquals(15f, weapon.height());
        assertEquals(WeaponType.SHOTGUN, weapon.getWeaponType());
        assertEquals(8, weapon.getMaxAmmo());
        assertEquals(8, weapon.getCurrentAmmo());
        assertEquals(4f, weapon.getFiringDelay());
        assertEquals(0f, weapon.getBulletSpeed());
        assertEquals(0f, weapon.getKnockBackMultiplier());
    }

    @Test
    void constructorSetsAttackBoundsFromPlayerPosition() {
        Rectangle bounds = weapon.getAttackBounds();

        assertEquals(15f, bounds.x);
        assertEquals(25f, bounds.y);
        assertEquals(25f, bounds.width);
        assertEquals(15f, bounds.height);
    }

    @Test
    void updateMovesAttackBoundsToPlayerPosition() {
        when(player.x()).thenReturn(300f);
        when(player.y()).thenReturn(400f);

        weapon.update(world, 0.016f);

        Rectangle bounds = weapon.getAttackBounds();
        assertEquals(300f, bounds.x);
        assertEquals(400f, bounds.y);
        assertEquals(25f, bounds.width);
        assertEquals(15f, bounds.height);
    }

    @Test
    void shootReducesAmmo() {
        assertTrue(weapon.shoot());
        assertEquals(7, weapon.getCurrentAmmo());
    }
}