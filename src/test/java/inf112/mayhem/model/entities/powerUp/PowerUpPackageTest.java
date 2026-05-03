package inf112.mayhem.model.entities.powerUp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.collision.MapCollisionHandler;
import inf112.mayhem.model.entities.characters.AbstractCharacter;
import inf112.mayhem.model.projectile.AbstractProjectile;

class PowerUpPackageTest {

    static class TestCharacter extends AbstractCharacter {
        TestCharacter(float x, float y) {
            super(x, y, 32, 32, 3, new TiledMap());
        }

        @Override
        public void jump() {
        }

        @Override
        public void drop() {
        }

        @Override
        public void hit(AbstractProjectile obj) {
        }

        @Override
        public void update(AppWorld world, float delta) {
        }

        float getJumpBoostMultiplier() {
            return jumpBoostMultiplier;
        }

        float getGravityMultiplier() {
            return gravityMultiplier;
        }

        float getSpeedMultiplier() {
            return speedMultiplier;
        }

		@Override
		public float getDamagePercentage() {
			return damagePercent;
		}

        @Override
        public void jumpSoundMade() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'jumpSoundMade'");
        }

        @Override
        public boolean hasJumped() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasJumped'");
        }
    }

    @Test
    void jumpBoostPowerUpAppliesAndRemovesJumpMultiplier() {
        TestCharacter character = new TestCharacter(0, 0);
        JumpBoostPowerUp powerUp = new JumpBoostPowerUp();

        powerUp.applyPowerUp(character);
        assertEquals(1.5f, character.getJumpBoostMultiplier());

        powerUp.removePowerUp(character);
        assertEquals(1f, character.getJumpBoostMultiplier());
    }

    @Test
    void lowGravityPowerUpAppliesAndRemovesGravityMultiplier() {
        TestCharacter character = new TestCharacter(0, 0);
        LowGravityPowerUp powerUp = new LowGravityPowerUp();

        powerUp.applyPowerUp(character);
        assertEquals(0.5f, character.getGravityMultiplier());

        powerUp.removePowerUp(character);
        assertEquals(1f, character.getGravityMultiplier());
    }

    @Test
    void speedBoostPowerUpAppliesAndRemovesSpeedMultiplier() {
        TestCharacter character = new TestCharacter(0, 0);
        SpeedBoostPowerUp powerUp = new SpeedBoostPowerUp();

        powerUp.applyPowerUp(character);
        assertEquals(1.5f, character.getSpeedMultiplier());

        powerUp.removePowerUp(character);
        assertEquals(1f, character.getSpeedMultiplier());
    }

    @Test
    void crateOpenAddsPowerUpAndShowsPopupOnlyOnce() {
        AppWorld world = mock(AppWorld.class);
        AbstractCharacter character = mock(AbstractCharacter.class);
        Crate crate = new Crate(0, 0, 30, 30, new SpeedBoostPowerUp());

        crate.open(world, character);
        verify(character).addPowerUp(any(PowerUp.class), eq(15f));
        verify(world).showPopup("Speed Boost");

        crate.open(world, character);
        verify(world).showPopup("Speed Boost");
    }

    @Test
    void getPowerUpNameReturnsCorrectNames() {
        assertEquals("Jump Boost", new Crate(0, 0, 30, 30, new JumpBoostPowerUp()).getPowerUpName());
        assertEquals("Low Gravity", new Crate(0, 0, 30, 30, new LowGravityPowerUp()).getPowerUpName());
        assertEquals("Speed Boost", new Crate(0, 0, 30, 30, new SpeedBoostPowerUp()).getPowerUpName());
    }

    @Test
    void crateIsActiveInitiallyAndBecomesInactiveWhenOpened() {
        AppWorld world = mock(AppWorld.class);
        AbstractCharacter character = mock(AbstractCharacter.class);
        Crate crate = new Crate(0, 0, 30, 30, new SpeedBoostPowerUp());

        assertTrue(crate.isActive());
        crate.open(world, character);
        crate.updateState();
        assertFalse(crate.isActive());
        assertEquals(inf112.mayhem.model.entities.EntityState.OPENED_WEAPON_CRATE, crate.state());
    }

    @Test
    void crateUpdateFallingWithNoGroundingMovesCrateDown() {
        AppWorld world = mock(AppWorld.class);
        MapCollisionHandler handler = mock(MapCollisionHandler.class);
        Crate crate = new Crate(0, 100, 30, 30, new SpeedBoostPowerUp());
        when(world.getMapCollisionHandler()).thenReturn(handler);
        when(handler.isGrounded(crate.bounds(), crate.y(), false)).thenReturn(false);

        float initialY = crate.y();

        crate.update(world, 1f);

        assertTrue(crate.y() < initialY);
    }

    @Test
    void crateBecomesInactiveAfterLifespanExpires() {
        AppWorld world = mock(AppWorld.class);
        MapCollisionHandler handler = mock(MapCollisionHandler.class);
        Crate crate = new Crate(0, 100, 30, 30, new SpeedBoostPowerUp());
        when(world.getMapCollisionHandler()).thenReturn(handler);
        when(handler.isGrounded(crate.bounds(), crate.y(), false)).thenReturn(false);

        crate.update(world, 21f);

        assertFalse(crate.isActive());
    }
}
