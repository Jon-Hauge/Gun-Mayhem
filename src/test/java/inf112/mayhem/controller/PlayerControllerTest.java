package inf112.mayhem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.characters.PlayerClass;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;
import java.lang.reflect.Field;

public class PlayerControllerTest {

    private PlayerController playerController;
    private PlayerClass player;
    private AppWorld world;
    private Input input;

    @BeforeEach
    void setUp() {
        player = mock(PlayerClass.class);
        world = mock(AppWorld.class);
        player.velocity = new Vector2(0, 0);
        playerController = new PlayerController(player, world);
        
        input = mock(Input.class);
        Gdx.input = input;
    }

    @Test
    void updateMovesLeftWhenLeftOrAPressed() {
        when(input.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);
        
        playerController.update(0.1f);
        
        verify(player).setDirection(-1);
        assertEquals(-150f, player.velocity.x);
        verify(player).updateState();
    }

    @Test
    void updateMovesRightWhenRightOrDPressed() {
        when(input.isKeyPressed(Input.Keys.D)).thenReturn(true);
        
        playerController.update(0.1f);
        
        verify(player).setDirection(1);
        assertEquals(150f, player.velocity.x);
        verify(player).updateState();
    }

    @Test
    void updateJumpsWhenUpOrWJustPressed() {
        when(input.isKeyJustPressed(Input.Keys.W)).thenReturn(true);
        
        playerController.update(0.1f);
        
        verify(player).jump();
    }

    @Test
    void updateDropsWhenDownOrSJustPressed() {
        when(input.isKeyJustPressed(Input.Keys.S)).thenReturn(true);
        
        playerController.update(0.1f);
        
        verify(player).drop();
    }

    @Test
    void pickUpWeaponEquipsWeaponOnPlayer() {
        AbstractWeapon weapon = mock(AbstractWeapon.class);
        
        playerController.pickUpWeapon(weapon);
        
        verify(player).equipWeapon(weapon);
    }

    @Test
    void updateDecrementsCooldownTimer() throws Exception {
        Field cooldownField = PlayerController.class.getDeclaredField("COOLDOWN_TIMER");
        cooldownField.setAccessible(true);
        cooldownField.set(playerController, 0.5f);

        playerController.update(0.1f);

        float newValue = (float) cooldownField.get(playerController);
        assertEquals(0.4f, newValue, 0.001f);
    }

    @Test
    void updateSetsVelocityToZeroWhenNoMovementKeysPressed() {
        when(input.isKeyPressed(Input.Keys.LEFT)).thenReturn(false);
        when(input.isKeyPressed(Input.Keys.A)).thenReturn(false);
        when(input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(false);
        when(input.isKeyPressed(Input.Keys.D)).thenReturn(false);
        
        player.velocity.x = 100f;
        
        playerController.update(0.1f);
        
        assertEquals(0f, player.velocity.x);
    }
}
