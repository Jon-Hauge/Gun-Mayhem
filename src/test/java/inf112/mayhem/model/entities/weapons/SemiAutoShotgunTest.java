package inf112.mayhem.model.entities.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import inf112.mayhem.model.entities.weapons.SemiAutoShotgun;
import org.junit.jupiter.api.Test;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.weapons.WeaponType;

class SemiAutoShotgunTest {

    @Test
    void constructorUsesSemiAutoStats() {
        SemiAutoShotgun weapon = new SemiAutoShotgun(6f, 7f);

        assertEquals(6f, weapon.x());
        assertEquals(7f, weapon.y());
        assertEquals(25f, weapon.width());
        assertEquals(15f, weapon.height());
        assertEquals(EntityType.WEAPON, weapon.type());
        assertEquals(EntityState.DROPPED, weapon.state());
        assertEquals(WeaponType.SEMIAUTOSHOTGUN, weapon.getWeaponType());
        assertEquals(8, weapon.getCurrentAmmo());
        assertEquals(8, weapon.getMaxAmmo());
        assertEquals(1f, weapon.getFiringDelay());
    }
}
