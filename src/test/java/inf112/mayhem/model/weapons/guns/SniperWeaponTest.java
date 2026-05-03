package inf112.mayhem.model.weapons.guns;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.WeaponType;

class SniperWeaponTest {

    @Test
    void constructorUsesSniperStats() {
        SniperWeapon weapon = new SniperWeapon(8f, 9f);

        assertEquals(8f, weapon.x());
        assertEquals(9f, weapon.y());
        assertEquals(25f, weapon.width());
        assertEquals(15f, weapon.height());
        assertEquals(EntityType.WEAPON, weapon.type());
        assertEquals(EntityState.DROPPED, weapon.state());
        assertEquals(WeaponType.SNIPER, weapon.getWeaponType());
        assertEquals(6, weapon.getCurrentAmmo());
        assertEquals(6, weapon.getMaxAmmo());
        assertEquals(3f, weapon.getFiringDelay());
    }
}
