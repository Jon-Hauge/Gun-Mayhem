package inf112.mayhem.model.weapons.shortrange;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import inf112.mayhem.model.entities.characters.AbstractCharacter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import com.badlogic.gdx.math.Rectangle;
//
//import inf112.mayhem.model.AppWorld;
//import inf112.mayhem.model.entities.characters.PlayerClass;
//import inf112.mayhem.model.weapons.WeaponType;
//
//class PumpActionTest {
//
//    private PumpAction weapon;
//
//    @BeforeEach
//    void setUp() {
//        weapon = new PumpAction(1f, 2f);
//    }
//
//    @Test
//    void constructorSetsExpectedValues() {
//        assertEquals(25f, weapon.width());
//        assertEquals(10f, weapon.height());
//        assertEquals(WeaponType.PUMPACTION, weapon.getWeaponType());
//        assertEquals(5, weapon.getMaxAmmo());
//        assertEquals(5, weapon.getCurrentAmmo());
//        assertEquals(8f, weapon.getFiringDelay());
//    }
//
//    @Test
//    void constructorSetsAttackBoundsFromPlayerPosition() {
//        Rectangle bounds = weapon.getAttackBounds();
//
//        assertEquals(11f, bounds.x);
//        assertEquals(22f, bounds.y);
//        assertEquals(25f, bounds.width);
//        assertEquals(10f, bounds.height);
//    }
//
//    @Test
//    void shootReducesAmmo() {
//        assertTrue(weapon.shoot());
//        assertEquals(4, weapon.getCurrentAmmo());
//    }
//}