package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameSounds {

    private final Sound pistol = Gdx.audio.newSound(Gdx.files.internal("character_audio/pistol.wav"));
    private final Sound jump = Gdx.audio.newSound(Gdx.files.internal("character_audio/swoosh.wav"));
    private final Sound sniper = Gdx.audio.newSound(Gdx.files.internal("character_audio/sniper.wav"));
    private final Sound pumpAction = Gdx.audio.newSound(Gdx.files.internal("character_audio/shotgun.wav"));
    private final Sound semiAuto = Gdx.audio.newSound(Gdx.files.internal("character_audio/semiauto.wav"));

    private final Music mainMenu = Gdx.audio.newMusic(Gdx.files.internal("music/kaazoom-crazy-bad-2-min-edit-electronic-rock-game-music-414772.mp3"));
    private final Music level1 = Gdx.audio.newMusic(Gdx.files.internal("music/monume-background-music-519228.mp3"));
    private final Music level2 = Gdx.audio.newMusic(Gdx.files.internal("music/Zambolino - Machine (freetouse.com).mp3"));
    private final Music level3 = Gdx.audio.newMusic(Gdx.files.internal("music/music-for-videos-background-game-145867.mp3"));

    /// Initiates sounds to be used in the games and enables looping
    ///
    public GameSounds() {
        mainMenu.setLooping(true);
        level1.setLooping(true);
        level2.setLooping(true);
        level3.setLooping(true);
    }

    public enum SoundType {
        PISTOL, JUMP, SNIPER, PUMPACTION, SEMIAUTO,
    }

    public enum MusicType {
        MAIN_MENU, LEVEL1, LEVEL2, LEVEL3,
    }

    /// Plays a sound.
    ///
    /// @param soundType Type of sound determining what should be played
    ///
    public void playSound(SoundType soundType) {
        switch (soundType){
            case PISTOL -> pistol.play();
            case JUMP -> jump.play();
            case SNIPER -> sniper.play();
            case PUMPACTION -> pumpAction.play();
            case SEMIAUTO -> semiAuto.play();
        }
    }

    /// Plays music.
    ///
    /// @param musicType Type of music determining what should be played
    ///
    public void playMusic(MusicType musicType){
        stopAllMusic();
        switch (musicType) {
            case MAIN_MENU -> mainMenu.play();
            case LEVEL1 -> level1.play();
            case LEVEL2 -> level2.play();
            case LEVEL3 -> level3.play();
        }
    }

    private void stopAllMusic() {
        mainMenu.stop();
        level1.stop();
        level2.stop();
        level3.stop();
    }

    /// Disposes of all sounds
    ///
    public void dispose() {
        pistol.dispose();
        jump.dispose();
        sniper.dispose();
        pumpAction.dispose();
        semiAuto.dispose();

        mainMenu.dispose();
        level1.dispose();
        level2.dispose();
        level3.dispose();
    }
}
