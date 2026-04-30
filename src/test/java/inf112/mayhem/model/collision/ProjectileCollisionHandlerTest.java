package inf112.mayhem.model.collision;

import com.badlogic.gdx.math.Rectangle;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.WeaponCrate;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.entities.characters.CharacterModel;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.projectile.ProjectileModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class ProjectileCollisionHandlerTest {
    private AppWorld world;
    private ProjectileCollisionHandler handler;

    @BeforeEach
    void setUp() {
        world = mock(AppWorld.class);
        handler = new ProjectileCollisionHandler(world);
    }

    @Test
    void testCrateCharacterCollision() {
        WeaponCrate crate = mock(WeaponCrate.class);
        AbstractCharacter character = mock(AbstractCharacter.class);
        
        Rectangle crateBounds = new Rectangle(0, 0, 50, 50);
        Rectangle characterBounds = new Rectangle(10, 10, 30, 30);
        
        when(crate.bounds()).thenReturn(crateBounds);
        when(character.bounds()).thenReturn(characterBounds);
        
        when(world.iterator()).thenReturn(Arrays.asList((EntityModel) crate, (EntityModel) character).iterator());
        
        handler.checkCollisions();
        
        verify(crate).open(world);
    }

    @Test
    void testCrateCharacterNoCollision() {
        WeaponCrate crate = mock(WeaponCrate.class);
        AbstractCharacter character = mock(AbstractCharacter.class);
        
        Rectangle crateBounds = new Rectangle(0, 0, 50, 50);
        Rectangle characterBounds = new Rectangle(100, 100, 30, 30);
        
        when(crate.bounds()).thenReturn(crateBounds);
        when(character.bounds()).thenReturn(characterBounds);
        
        when(world.iterator()).thenReturn(Arrays.asList((EntityModel) crate, (EntityModel) character).iterator());
        
        handler.checkCollisions();
        
        verify(crate, never()).open(world);
    }

    @Test
    void testProjectileCharacterCollision() {
        AbstractProjectile projectile = mock(AbstractProjectile.class);
        AbstractCharacter character = mock(AbstractCharacter.class);

        Rectangle projectileBounds = new Rectangle(0, 0, 10, 7);
        Rectangle characterBounds = new Rectangle(5, 5, 30, 30);

        when(projectile.bounds()).thenReturn(projectileBounds);
        when(character.bounds()).thenReturn(characterBounds);

        when(world.iterator()).thenReturn(Arrays.asList((EntityModel) projectile, (EntityModel) character).iterator());

        handler.checkCollisions();

        verify(projectile).hit(character);
        verify(character).hit(projectile);
    }

    @Test
    void testProjectileProjectileCollision() {
        AbstractProjectile p1 = mock(AbstractProjectile.class);
        AbstractProjectile p2 = mock(AbstractProjectile.class);

        Rectangle p1Bounds = new Rectangle(0, 0, 10, 7);
        Rectangle p2Bounds = new Rectangle(5, 0, 10, 7);

        when(p1.bounds()).thenReturn(p1Bounds);
        when(p2.bounds()).thenReturn(p2Bounds);

        when(world.iterator()).thenReturn(Arrays.asList((EntityModel) p1, (EntityModel) p2).iterator());

        handler.checkCollisions();

        verify(p1).hit(p2);
        verify(p2).hit(p1);
    }

    @Test
    void testProjectileNoCollisionWithSelf() {
        AbstractProjectile projectile = mock(AbstractProjectile.class);
        Rectangle bounds = new Rectangle(0, 0, 10, 7);
        when(projectile.bounds()).thenReturn(bounds);

        when(world.iterator()).thenReturn(Collections.singletonList((EntityModel) projectile).iterator());

        handler.checkCollisions();

        verify(projectile, never()).hit(any());
    }
}
