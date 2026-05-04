package inf112.mayhem.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.characters.EnemyClass;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.entities.powerUp.Crate;
import inf112.mayhem.model.entities.projectile.AbstractProjectile;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;
import inf112.mayhem.model.entities.weapons.WeaponType;

class EntityFactoryTest {

    private AppWorld world;
    private TiledMap map;
    private EntityFactory factory;

    @BeforeEach
    void setUp() {
        world = mock(AppWorld.class);
        map = new TiledMap();
        factory = new EntityFactory(world, map);
    }

    // --- spawnPlayer ---

    @Test
    void spawnPlayerCreatesPlayerAndAddsToWorld() {
        PlayerClass player = factory.spawnPlayer(10, 20);

        assertEquals(EntityType.PLAYER, player.type());
        assertEquals(10, player.x());
        assertEquals(20, player.y());
        verify(world).add(player);
    }

    @Test
    void spawnPlayer_returnedPlayerIsNotNull() {
        PlayerClass player = factory.spawnPlayer(0, 0);

        assertNotNull(player);
    }

    @Test
    void spawnPlayer_positionIsCorrectAtOrigin() {
        PlayerClass player = factory.spawnPlayer(0, 0);

        assertEquals(0, player.x());
        assertEquals(0, player.y());
        verify(world).add(player);
    }

    @Test
    void spawnPlayer_positionIsCorrectAtLargeCoordinates() {
        PlayerClass player = factory.spawnPlayer(999, 888);

        assertEquals(999, player.x());
        assertEquals(888, player.y());
        verify(world).add(player);
    }

    // --- spawnEnemy ---

    @Test
    void spawnEnemyCreatesEnemyAndAddsToWorld() {
        EnemyClass enemy = factory.spawnEnemy(30, 40);

        assertEquals(EntityType.ENEMY, enemy.type());
        assertEquals(30, enemy.x());
        assertEquals(40, enemy.y());
        assertEquals(3, enemy.getAggression());
        verify(world).add(enemy);
    }

    @Test
    void spawnEnemy_returnedEnemyIsNotNull() {
        EnemyClass enemy = factory.spawnEnemy(0, 0);

        assertNotNull(enemy);
    }

    @Test
    void spawnEnemy_positionIsCorrectAtOrigin() {
        EnemyClass enemy = factory.spawnEnemy(0, 0);

        assertEquals(0, enemy.x());
        assertEquals(0, enemy.y());
        verify(world).add(enemy);
    }

    @Test
    void spawnEnemy_defaultAggressionIsThree() {
        EnemyClass enemy = factory.spawnEnemy(0, 0);

        assertEquals(3, enemy.getAggression());
    }

    @Test
    void spawnEnemy_positionIsCorrectAtLargeCoordinates() {
        EnemyClass enemy = factory.spawnEnemy(500, 300);

        assertEquals(500, enemy.x());
        assertEquals(300, enemy.y());
        verify(world).add(enemy);
    }

    @Test
    void spawnPlayer_multiplePlayersAllAddedToWorld() {
        PlayerClass p1 = factory.spawnPlayer(0, 0);
        PlayerClass p2 = factory.spawnPlayer(100, 100);

        verify(world).add(p1);
        verify(world).add(p2);
    }

    @Test
    void spawnEnemy_multipleEnemiesAllAddedToWorld() {
        EnemyClass e1 = factory.spawnEnemy(0, 0);
        EnemyClass e2 = factory.spawnEnemy(200, 200);

        verify(world).add(e1);
        verify(world).add(e2);
    }

    // --- spawnProjectile ---

    @Test
    void spawnProjectile_facingRight_spawnXIsRightEdgeOfCharacter() {
        AbstractCharacter character = mock(AbstractCharacter.class);
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        when(character.getDirection()).thenReturn(1);
        when(character.x()).thenReturn(100f);
        when(character.width()).thenReturn(40f);
        when(character.y()).thenReturn(200f);
        when(character.height()).thenReturn(60f);
        when(character.getEquippedWeapon()).thenReturn(weapon);
        when(weapon.getWeaponType()).thenReturn(WeaponType.PISTOL);

        factory.spawnProjectile(character);

        ArgumentCaptor<AbstractProjectile> captor = forClass(AbstractProjectile.class);
        verify(world).add(captor.capture());
        assertEquals(140f, captor.getValue().x()); // 100 + 40
    }

    @Test
    void spawnProjectile_facingLeft_spawnXIsTenBeforeCharacter() {
        AbstractCharacter character = mock(AbstractCharacter.class);
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        when(character.getDirection()).thenReturn(-1);
        when(character.x()).thenReturn(100f);
        when(character.y()).thenReturn(200f);
        when(character.height()).thenReturn(60f);
        when(character.getEquippedWeapon()).thenReturn(weapon);
        when(weapon.getWeaponType()).thenReturn(WeaponType.PISTOL);

        factory.spawnProjectile(character);

        ArgumentCaptor<AbstractProjectile> captor = forClass(AbstractProjectile.class);
        verify(world).add(captor.capture());
        assertEquals(90f, captor.getValue().x()); // 100 - 10
    }

    @Test
    void spawnProjectile_semiAutoShotgun_spawnYIsOffset15Down() {
        AbstractCharacter character = mock(AbstractCharacter.class);
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        when(character.getDirection()).thenReturn(1);
        when(character.x()).thenReturn(0f);
        when(character.width()).thenReturn(0f);
        when(character.y()).thenReturn(100f);
        when(character.height()).thenReturn(60f);
        when(character.getEquippedWeapon()).thenReturn(weapon);
        when(weapon.getWeaponType()).thenReturn(WeaponType.SEMIAUTOSHOTGUN);

        factory.spawnProjectile(character);

        ArgumentCaptor<AbstractProjectile> captor = forClass(AbstractProjectile.class);
        verify(world).add(captor.capture());
        assertEquals(115f, captor.getValue().y()); // 100 + 30 - 15
    }

    @Test
    void spawnProjectile_pumpAction_spawnYIsOffset15Down() {
        AbstractCharacter character = mock(AbstractCharacter.class);
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        when(character.getDirection()).thenReturn(1);
        when(character.x()).thenReturn(0f);
        when(character.width()).thenReturn(0f);
        when(character.y()).thenReturn(100f);
        when(character.height()).thenReturn(60f);
        when(character.getEquippedWeapon()).thenReturn(weapon);
        when(weapon.getWeaponType()).thenReturn(WeaponType.PUMPACTION);

        factory.spawnProjectile(character);

        ArgumentCaptor<AbstractProjectile> captor = forClass(AbstractProjectile.class);
        verify(world).add(captor.capture());
        assertEquals(115f, captor.getValue().y());
    }

   

    // --- spawnCrate ---

    @Test
    void spawnCrate_crateAddedToWorld() {
        when(world.width()).thenReturn(1000.0);
        when(world.height()).thenReturn(600.0);

        factory.spawnCrate();

        verify(world).add(any(Crate.class));
    }

    @Test
    void spawnCrate_yIsBelowWorldBottom() {
        when(world.width()).thenReturn(1000.0);
        when(world.height()).thenReturn(600.0);

        factory.spawnCrate();

        ArgumentCaptor<Crate> captor = forClass(Crate.class);
        verify(world).add(captor.capture());
        assertEquals(700f, captor.getValue().y()); // 600 + 100
    }

    @Test
    void spawnCrate_xIsWithinExpectedBounds() {
        when(world.width()).thenReturn(1000.0);
        when(world.height()).thenReturn(600.0);

        for (int i = 0; i < 50; i++) {
            factory.spawnCrate();
        }

        ArgumentCaptor<Crate> captor = forClass(Crate.class);
        verify(world, times(50)).add(captor.capture());
        for (Crate crate : captor.getAllValues()) {
            assertTrue(crate.x() >= 300f);
            assertTrue(crate.x() <= 700f);
        }
    }

    

    // --- spawnWeapon ---

    @Test
    void spawnWeapon_weaponAddedToWorld() {
        when(world.width()).thenReturn(1000.0);
        when(world.height()).thenReturn(600.0);

        factory.spawnWeapon();

        verify(world).add(any(AbstractWeapon.class));
    }

    

    @Test
    void spawnWeapon_yIsBelowWorldBottom() {
        when(world.width()).thenReturn(1000.0);
        when(world.height()).thenReturn(600.0);

        factory.spawnWeapon();

        ArgumentCaptor<AbstractWeapon> captor = forClass(AbstractWeapon.class);
        verify(world).add(captor.capture());
        assertEquals(700f, captor.getValue().y()); // 600 + 100
    }

    @Test
    void spawnWeapon_xIsWithinExpectedBounds() {
        when(world.width()).thenReturn(1000.0);
        when(world.height()).thenReturn(600.0);

        for (int i = 0; i < 50; i++) {
            factory.spawnWeapon();
        }

        ArgumentCaptor<AbstractWeapon> captor = forClass(AbstractWeapon.class);
        verify(world, times(50)).add(captor.capture());
        for (AbstractWeapon weapon : captor.getAllValues()) {
            assertTrue(weapon.x() >= 300f);
            assertTrue(weapon.x() <= 700f);
        }
    }
}