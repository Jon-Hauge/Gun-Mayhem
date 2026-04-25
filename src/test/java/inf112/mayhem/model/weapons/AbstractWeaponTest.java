package inf112.mayhem.model.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import inf112.mayhem.model.entities.characters.AbstractCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.view.screens.activeGameView.WeaponView;

class AbstractWeaponTest {

    private AbstractCharacter character;
    private AppWorld world;
    private WeaponView weaponView;
    private TestWeapon weapon;

    private static class TestWeapon extends AbstractWeapon {
        public TestWeapon(
                float startX,
                float startY,
                AbstractCharacter character,
                float width,
                float height,
                WeaponType weaponType,
                EntityState startingState,
                int startingAmmo,
                float firingDelay,
                float knockBackMultiplier,
                float bulletSpeed) {
            super(startX, startY, character, width, height, weaponType, startingState, startingAmmo,
                    firingDelay, knockBackMultiplier, bulletSpeed);
        }
    }

    @BeforeEach
    void setUp() {
        character = mock(AbstractCharacter.class);
        world = mock(AppWorld.class);
        weaponView = mock(WeaponView.class);

        weapon = new TestWeapon(
                10f,
                20f,
                character,
                30f,
                40f,
                WeaponType.PISTOL,
                EntityState.IN_USE,
                6,
                0.5f,
                2.0f,
                15.0f
        );
    }

    @Test
    void xReturnsStartX() {
        assertEquals(10f, weapon.x());
    }

    @Test
    void yReturnsStartY() {
        assertEquals(20f, weapon.y());
    }

    @Test
    void widthReturnsWidth() {
        assertEquals(30f, weapon.width());
    }

    @Test
    void heightReturnsHeight() {
        assertEquals(40f, weapon.height());
    }

    @Test
    void typeReturnsWeapon() {
        assertEquals(EntityType.WEAPON, weapon.type());
    }

    @Test
    void updateFollowsPlayerPosition() {
        when(character.x()).thenReturn(100f);
        when(character.y()).thenReturn(200f);

        weapon.update(world, 0.016f);

        assertEquals(100f, weapon.x());
        assertEquals(200f, weapon.y());
    }

    @Test
    void isActiveAlwaysReturnsTrue() {
        assertTrue(weapon.isActive());
    }


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
        TestWeapon emptyWeapon = new TestWeapon(
                0f, 0f, character, 10f, 10f,
                WeaponType.PISTOL, EntityState.IN_USE,
                0, 0.2f, 1f, 5f
        );

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
        TestWeapon infiniteWeapon = new TestWeapon(
                0f, 0f, character, 10f, 10f,
                WeaponType.PISTOL, EntityState.IN_USE,
                -1, 0.2f, 1f, 5f
        );

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
    void getFiringDelayReturnsFiringDelay() {
        assertEquals(0.5f, weapon.getFiringDelay());
    }

    @Test
    void getCurrentAmmoReturnsCurrentAmmo() {
        assertEquals(6, weapon.getCurrentAmmo());
    }

    @Test
    void getMaxAmmoReturnsMaxAmmo() {
        weapon.shoot();
        assertEquals(6, weapon.getMaxAmmo());
    }

    @Test
    void getPlayerReturnsPlayer() {
        assertSame(character, weapon.getCharacter());
    }

    @Test
    void weaponViewCanBeSetAndRetrieved() {
        assertNull(weapon.getWeaponView());

        weapon.setWeaponView(weaponView);

        assertSame(weaponView, weapon.getWeaponView());
    }

    @Test
    void getKnockBackMultiplierReturnsKnockBackMultiplier() {
        assertEquals(2.0f, weapon.getKnockBackMultiplier());
    }

    @Test
    void getBulletSpeedReturnsBulletSpeed() {
        assertEquals(15.0f, weapon.getBulletSpeed());
    }
}