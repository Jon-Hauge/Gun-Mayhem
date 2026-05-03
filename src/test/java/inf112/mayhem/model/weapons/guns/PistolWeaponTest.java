package inf112.mayhem.model.weapons.guns;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.weapons.WeaponType;

class PistolWeaponTest {

    @Test
    void constructorUsesPistolStats() {
        PistolWeapon weapon = new PistolWeapon(2f, 3f);

        assertEquals(2f, weapon.x());
        assertEquals(3f, weapon.y());
        assertEquals(10f, weapon.width());
        assertEquals(10f, weapon.height());
        assertEquals(EntityType.WEAPON, weapon.type());
        assertEquals(EntityState.IN_USE, weapon.state());
        assertEquals(WeaponType.PISTOL, weapon.getWeaponType());
        assertEquals(-1, weapon.getCurrentAmmo());
        assertEquals(-1, weapon.getMaxAmmo());
        assertEquals(0.5f, weapon.getFiringDelay());
    }
}
