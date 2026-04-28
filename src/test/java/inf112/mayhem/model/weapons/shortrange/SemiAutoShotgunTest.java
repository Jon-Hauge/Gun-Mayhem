package inf112.mayhem.model.weapons.shortrange;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.weapons.WeaponType;

class SemiAutoShotgunTest {

    private AbstractCharacter character;
    private AppWorld world;
    private SemiActionShotgun weapon;

    @BeforeEach
    void setUp() {
        character = mock(AbstractCharacter.class);
        world = mock(AppWorld.class);

        when(character.x()).thenReturn(15f);
        when(character.y()).thenReturn(25f);

        weapon = new SemiActionShotgun(3f, 4f, character);
    }

    @Test
    void constructorSetsExpectedValues() {
        assertEquals(25f, weapon.width());
        assertEquals(15f, weapon.height());
        assertEquals(WeaponType.SEMIACTIONSHOTGUN, weapon.getWeaponType());
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
        when(character.x()).thenReturn(300f);
        when(character.y()).thenReturn(400f);

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