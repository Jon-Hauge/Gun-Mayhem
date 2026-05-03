package inf112.mayhem.model.weapons.guns;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.WeaponType;

class PumpActionShotgunTest {

    @Test
    void constructorUsesPumpActionStats() {
        PumpActionShotgun weapon = new PumpActionShotgun(4f, 5f);

        assertEquals(4f, weapon.x());
        assertEquals(5f, weapon.y());
        assertEquals(25f, weapon.width());
        assertEquals(10f, weapon.height());
        assertEquals(EntityType.WEAPON, weapon.type());
        assertEquals(EntityState.DROPPED, weapon.state());
        assertEquals(WeaponType.PUMPACTION, weapon.getWeaponType());
        assertEquals(5, weapon.getCurrentAmmo());
        assertEquals(5, weapon.getMaxAmmo());
        assertEquals(2f, weapon.getFiringDelay());
    }
}
