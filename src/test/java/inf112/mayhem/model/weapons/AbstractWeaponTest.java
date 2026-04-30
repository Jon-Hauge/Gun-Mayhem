package inf112.mayhem.model.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.characters.AbstractCharacter;

class AbstractWeaponTest {

    private AppWorld world;
    private TestWeapon weapon;

    private static class TestWeapon extends AbstractWeapon {
        public TestWeapon(float startX, float startY,
                          int width, int height, int startingAmmo, float firingDelay) {
            super(startX, startY, width, height, startingAmmo, firingDelay);
            this.weapon = WeaponType.PISTOL;
        }

        @Override
        public WeaponType getWeaponType() {
            return this.weapon;
        }
    }

    @BeforeEach
    void setUp() {
        world = mock(AppWorld.class);
        weapon = new TestWeapon(10f, 20f, 30, 40, 6, 0.5f);
    }

    @Test
    void xReturnsStartX() { assertEquals(10f, weapon.x()); }

    @Test
    void yReturnsStartY() { assertEquals(20f, weapon.y()); }

    @Test
    void widthReturnsWidth() { assertEquals(30f, weapon.width()); }

    @Test
    void heightReturnsHeight() { assertEquals(40f, weapon.height()); }

    @Test
    void typeReturnsWeapon() { assertEquals(EntityType.WEAPON, weapon.type()); }

    @Test
    void isActiveAlwaysReturnsTrue() { assertTrue(weapon.isActive()); }

    @Test
    void dropWeaponSetsDroppedState() {
        weapon.dropWeapon();
        assertEquals(EntityState.DROPPED, weapon.state());
    }

    @Test
    void openCrateSetsOpenedWeaponCrateState() {
        weapon.openCrate();
        assertEquals(EntityState.OPENED_WEAPON_CRATE, weapon.state());
    }

    @Test
    void pickUpWeaponSetsOpenedWeaponCrateState() {
        weapon.pickUpWeapon();
        assertEquals(EntityState.OPENED_WEAPON_CRATE, weapon.state());
    }

    @Test
    void getWeaponTypeReturnsWeaponType() {
        assertEquals(WeaponType.PISTOL, weapon.getWeaponType());
    }

    @Test
    void shootReturnsFalseWhenAmmoIsZero() {
        TestWeapon emptyWeapon = new TestWeapon(0f, 0f, 10, 10, 0, 0.2f);
        assertFalse(emptyWeapon.shoot());
        assertEquals(0, emptyWeapon.getCurrentAmmo());
    }

    @Test
    void shootReturnsTrueAndReducesAmmoWhenAmmoIsPositive() {
        assertTrue(weapon.shoot());
        assertEquals(5, weapon.getCurrentAmmo());
    }

    @Test
    void shootReturnsTrueAndDoesNotReduceAmmoWhenAmmoIsNegative() {
        TestWeapon infiniteWeapon = new TestWeapon(0f, 0f, 10, 10, -1, 0.2f);
        assertTrue(infiniteWeapon.shoot());
        assertEquals(-1, infiniteWeapon.getCurrentAmmo());
    }

    @Test
    void pickUpAmmoResetsCurrentAmmoToMaxAmmo() {
        weapon.shoot();
        weapon.shoot();
        assertEquals(4, weapon.getCurrentAmmo());
        weapon.pickUpAmmo();
        assertEquals(6, weapon.getCurrentAmmo());
    }

    @Test
    void getFiringDelayReturnsFiringDelay() { assertEquals(0.5f, weapon.getFiringDelay()); }

    @Test
    void getCurrentAmmoReturnsCurrentAmmo() { assertEquals(6, weapon.getCurrentAmmo()); }

    @Test
    void getMaxAmmoReturnsMaxAmmo() {
        weapon.shoot();
        assertEquals(6, weapon.getMaxAmmo());
    }

}