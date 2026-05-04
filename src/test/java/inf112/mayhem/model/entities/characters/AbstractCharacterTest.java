package inf112.mayhem.model.entities.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.projectile.AbstractProjectile;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;

class AbstractCharacterTest {

    static class TestCharacter extends AbstractCharacter {
        TestCharacter(float x, float y) {
            super(x, y, 32, 32, 3, new TiledMap());
            this.type = EntityType.PLAYER;
            this.state = EntityState.IDLE;
        }

        @Override
        public void jump() {
            velocity.y = 100;
        }

        @Override
        public void drop() {
            velocity.y = -100;
        }

        @Override
        public void hit(AbstractProjectile obj) {
            lives--;
        }

        @Override
        public void updateState() {
            state = EntityState.WALKING;
        }

        @Override
        public void update(AppWorld world, float delta) {
            position.x += velocity.x * delta;
            position.y += velocity.y * delta;
            updateBounds();
        }

		@Override
		public float getDamagePercentage() {
			return damagePercent;
		}

        @Override
        public boolean hasJumped() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void jumpSoundMade() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    static class BaseTestCharacter extends AbstractCharacter {
        BaseTestCharacter(float x, float y) {
            super(x, y, 32, 32, 3, new TiledMap());
            this.type = EntityType.PLAYER;
            this.state = EntityState.IDLE;
        }

        @Override
        public void jump() {
            velocity.y = 100;
        }

        @Override
        public void drop() {
            velocity.y = -100;
        }

        @Override
        public void hit(AbstractProjectile obj) {
            lives--;
        }

        @Override
        public void update(AppWorld world, float delta) {
            super.update(world, delta);
        }

		@Override
		public float getDamagePercentage() {
			return damagePercent;
		}

        @Override
        public boolean hasJumped() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void jumpSoundMade() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Test
    void constructorStoresCharacterValues() {
        TestCharacter character = new TestCharacter(10, 20);

        assertEquals(10, character.x());
        assertEquals(20, character.y());
        assertEquals(3, character.lives());
        assertEquals(1, character.getDirection());
        assertTrue(character.isActive());
    }

    @Test
    void dieMakesCharacterInactive() {
        TestCharacter character = new TestCharacter(0, 0);

        character.die();

        assertFalse(character.isActive());
    }

    @Test
    void respawnMovesCharacterBackToStart() {
        TestCharacter character = new TestCharacter(10, 20);
        character.velocity.x = 50;

        character.update(mock(AppWorld.class), 1f);
        character.respawn();

        assertEquals(10, character.x());
        assertEquals(20, character.y());
        assertEquals(0, character.velocity.x);
    }

    @Test
    void setLivesChangesLives() {
        TestCharacter character = new TestCharacter(0, 0);

        character.setLives(8);

        assertEquals(8, character.lives());
    }

    @Test
    void setDirectionChangesDirection() {
        TestCharacter character = new TestCharacter(0, 0);

        character.setDirection(-1);

        assertEquals(-1, character.getDirection());
    }

    @Test
    void equipWeaponStoresWeapon() {
        TestCharacter character = new TestCharacter(0, 0);
        AbstractWeapon weapon = mock(AbstractWeapon.class);

        character.equipWeapon(weapon);

        assertSame(weapon, character.getEquippedWeapon());
    }

    @Test
    void outOfBoundsReturnsFalseWhenInsideWorld() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 100);
        when(world.height()).thenReturn((double) 100);

        TestCharacter character = new TestCharacter(10, 10);

        assertFalse(character.outOfBounds(world));
    }

    @Test
    void outOfBoundsReturnsTrueWhenOutsideWorld() {
        AppWorld world = mock(AppWorld.class);
        when(world.width()).thenReturn((double) 100);
        when(world.height()).thenReturn((double) 100);

        TestCharacter character = new TestCharacter(-50, 10);

        assertTrue(character.outOfBounds(world));
    }

    @Test
    void baseUpdateStateUsesEntityVelocityAndGrounding() {
        BaseTestCharacter character = new BaseTestCharacter(0, 0);

        character.isGrounded = false;
        character.velocity.y = 100;
        character.updateState();
        assertEquals(EntityState.JUMPING, character.state());

        character.velocity.y = -100;
        character.updateState();
        assertEquals(EntityState.FALLING, character.state());

        character.velocity.y = 0;
        character.velocity.x = 50;
        character.updateState();
        assertEquals(EntityState.WALKING, character.state());

        character.velocity.x = 0;
        character.isGrounded = true;
        character.updateState();
        assertEquals(EntityState.IDLE, character.state());
    }

    @Test
    void updateSwitchesBackToBaseWeaponWhenEquippedWeaponIsOutOfAmmo() {
        BaseTestCharacter character = new BaseTestCharacter(0, 0);
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        when(weapon.outOfAmmo()).thenReturn(true);

        character.equipWeapon(weapon);
        character.update(mock(AppWorld.class), 1f);

        assertSame(character.baseWeapon, character.getEquippedWeapon());
        verify(weapon).dropWeapon();
    }

    @Test
    void addPowerUpRemovesExpiredPowerUpOnUpdate() {
        BaseTestCharacter character = new BaseTestCharacter(0, 0);
        character.addPowerUp(new inf112.mayhem.model.entities.powerUp.JumpBoostPowerUp(), 0.5f);

        assertEquals(1.5f, character.jumpBoostMultiplier);

        character.update(mock(AppWorld.class), 1f);

        assertEquals(1f, character.jumpBoostMultiplier);
    }

    @Test
    void abstractMethods() {
        TestCharacter character = new TestCharacter(0, 0);

        character.jump();
        assertEquals(100, character.velocity.y);

        character.drop();
        assertEquals(-100, character.velocity.y);

        character.hit(mock(AbstractProjectile.class));
        assertEquals(2, character.lives());

        character.updateState();
        assertEquals(EntityState.WALKING, character.state());
    }
}