package inf112.mayhem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppWorldLevelUnlockTest {

    @Test
    void levelShouldNotUnlockWhenGameIsActive() {
        AppWorld world = new AppWorld(1280, 768);
        int currentLevel = 1;
        int unlockedLevels = 1;

        // Simulate game being active
        int newUnlockedLevels = world.updateLevelCount(currentLevel, unlockedLevels);

        // It should NOT unlock level 2 yet
        assertEquals(1, newUnlockedLevels, "Level 2 should not be unlocked while game is still active");
    }

    @Test
    void levelShouldUnlockWhenGameIsWon() {
        AppWorld world = new AppWorld(1280, 768);
        int currentLevel = 1;
        int unlockedLevels = 1;

        // Manually set state to VICTORY
        world.setState(GameState.VICTORY);
        int newUnlockedLevels = world.updateLevelCount(currentLevel, unlockedLevels);
        
        assertEquals(2, newUnlockedLevels, "Level 2 should be unlocked when game is won");
    }

    @Test
    void levelShouldNotUnlockWhenGameIsLost() {
        AppWorld world = new AppWorld(1280, 768);
        int currentLevel = 1;
        int unlockedLevels = 1;

        // Manually set state to GAME_OVER
        world.setState(GameState.GAME_OVER);
        int newUnlockedLevels = world.updateLevelCount(currentLevel, unlockedLevels);

        assertEquals(1, newUnlockedLevels, "Level 2 should not be unlocked when game is lost");
    }
}
