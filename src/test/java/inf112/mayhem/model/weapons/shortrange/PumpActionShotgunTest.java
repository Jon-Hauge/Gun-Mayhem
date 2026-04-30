package inf112.mayhem.model.weapons.shortrange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.weapons.WeaponType;

class PumpActionTest {

    private AbstractCharacter character;
    private AppWorld world;
    private PumpAction weapon;

    @BeforeEach
    void setUp() {
        character = mock(AbstractCharacter.class);
        world = mock(AppWorld.class);

        when(character.x()).thenReturn(11f);
        when(character.y()).thenReturn(22f);

        weapon = new PumpAction(1f, 2f, character);
    }

    @Test
    void constructorSetsExpectedValues() {
        assertEquals(25f, weapon.width());
        assertEquals(10f, weapon.height());
        assertEquals(WeaponType.PUMPACTION, weapon.getWeaponType());
        assertEquals(5, weapon.getMaxAmmo());
        assertEquals(5, weapon.getCurrentAmmo());
        assertEquals(8f, weapon.getFiringDelay());
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
        when(character.x()).thenReturn(100f);
        when(character.y()).thenReturn(200f);

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