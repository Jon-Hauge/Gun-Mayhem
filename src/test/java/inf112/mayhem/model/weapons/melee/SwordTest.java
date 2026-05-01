package inf112.mayhem.model.weapons.melee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.WeaponType;

class SwordTest {

    @Test
    void constructorUsesSwordStats() {
        Sword sword = new Sword(10f, 11f);

        assertEquals(10f, sword.x());
        assertEquals(11f, sword.y());
        assertEquals(25f, sword.width());
        assertEquals(15f, sword.height());
        assertEquals(EntityType.WEAPON, sword.type());
        assertEquals(EntityState.IN_USE, sword.state());
        assertEquals(WeaponType.SWORD, sword.getWeaponType());
        assertEquals(-1, sword.getCurrentAmmo());
        assertEquals(-1, sword.getMaxAmmo());
        assertEquals(0.5f, sword.getFiringDelay());
        assertEquals(200f, sword.getKnockback());
    }
}
