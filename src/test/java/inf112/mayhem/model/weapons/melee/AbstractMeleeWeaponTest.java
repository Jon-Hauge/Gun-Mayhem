package inf112.mayhem.model.weapons.melee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AbstractMeleeWeaponTest {

    private static class TestMeleeWeapon extends AbstractMeleeWeapon {
        TestMeleeWeapon(float x, float y, float width, float height, int ammo, float firingDelay, float knockback) {
            super(x, y, width, height, ammo, firingDelay);
            this.KNOCKBACK = knockback;
        }

        @Override
        public float getFiringDelay() {
            return 0.25f;
        }

        void updateBounds(float holderX, float holderY) {
            updateWeaponBounds(holderX, holderY);
        }
    }

    @Test
    void getKnockbackReturnsSetKnockbackValue() {
        TestMeleeWeapon weapon = new TestMeleeWeapon(1f, 2f, 11f, 12f, -1, 0.25f, 321f);

        assertEquals(321f, weapon.getKnockback());
    }

    @Test
    void updateWeaponBoundsUsesHolderPositionAndWeaponSize() {
        TestMeleeWeapon weapon = new TestMeleeWeapon(1f, 2f, 11f, 12f, -1, 0.25f, 321f);

        weapon.updateBounds(100f, 200f);

        assertEquals(100f, weapon.getAttackBounds().x);
        assertEquals(200f, weapon.getAttackBounds().y);
        assertEquals(11f, weapon.getAttackBounds().width);
        assertEquals(12f, weapon.getAttackBounds().height);
    }
}
