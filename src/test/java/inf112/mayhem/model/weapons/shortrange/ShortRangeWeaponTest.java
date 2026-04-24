package inf112.mayhem.model.weapons.shortrange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.weapons.WeaponType;

class ShortRangeWeaponTest {

    private AbstractCharacter character;
    private AppWorld world;
    private TestShortRangeWeapon weapon;

    private static class TestShortRangeWeapon extends ShortRangeWeapon {
        public TestShortRangeWeapon(float startX, float startY, AbstractCharacter character,
                float width, float height, WeaponType weaponType,
                EntityState startingState, int startingAmmo, float firingDelay) {
            super(startX, startY, character, width, height, weaponType, startingState, startingAmmo, firingDelay);
        }

        public void exposeSetKnockback(float value) {
            setKnockback(value);
        }

        public void exposeSetAttackBounds(Rectangle rect) {
            setAttackBounds(rect);
        }

        public float exposeGetKnockBack() {
            return getKnockBack();
        }

        public void exposeUpdateWeaponBounds() {
            updateWeaponBounds();
        }
    }

    @BeforeEach
    void setUp() {
        character = mock(AbstractCharacter.class);
        world = mock(AppWorld.class);

        weapon = new TestShortRangeWeapon(
                1f, 2f, character,
                25f, 15f,
                WeaponType.SWORD,
                EntityState.IN_USE,
                3,
                0.8f
        );
    }

    @Test
    void constructorStartsWithEmptyRectangle() {
        Rectangle bounds = weapon.getAttackBounds();

        assertEquals(0f, bounds.x);
        assertEquals(0f, bounds.y);
        assertEquals(0f, bounds.width);
        assertEquals(0f, bounds.height);
    }

    @Test
    void defaultKnockbackIsZero() {
        assertEquals(0f, weapon.exposeGetKnockBack());
    }

    @Test
    void setKnockbackUpdatesKnockback() {
        weapon.exposeSetKnockback(123f);

        assertEquals(123f, weapon.exposeGetKnockBack());
    }

    @Test
    void setAttackBoundsReplacesAttackBounds() {
        Rectangle rect = new Rectangle(4f, 5f, 6f, 7f);

        weapon.exposeSetAttackBounds(rect);

        assertSame(rect, weapon.getAttackBounds());
    }

    @Test
    void updateWeaponBoundsUsesPlayerPositionAndWeaponSize() {
        when(character.x()).thenReturn(50f);
        when(character.y()).thenReturn(60f);

        weapon.exposeUpdateWeaponBounds();

        Rectangle bounds = weapon.getAttackBounds();
        assertEquals(50f, bounds.x);
        assertEquals(60f, bounds.y);
        assertEquals(25f, bounds.width);
        assertEquals(15f, bounds.height);
    }

    @Test
    void updateCallsUpdateWeaponBounds() {
        when(character.x()).thenReturn(70f);
        when(character.y()).thenReturn(80f);

        weapon.update(world, 0.016f);

        Rectangle bounds = weapon.getAttackBounds();
        assertEquals(70f, bounds.x);
        assertEquals(80f, bounds.y);
        assertEquals(25f, bounds.width);
        assertEquals(15f, bounds.height);
    }

    @Test
    void shortRangeWeaponHasZeroBulletSpeedAndKnockbackMultiplierFromSuperConstructor() {
        assertEquals(0f, weapon.getBulletSpeed());
        assertEquals(0f, weapon.getKnockBackMultiplier());
    }
}