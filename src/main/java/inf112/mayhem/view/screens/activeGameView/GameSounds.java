package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSounds {
    private final Sound pistol = Gdx.audio.newSound(Gdx.files.internal("character_audio/pistol.wav"));
    private final Sound jump = Gdx.audio.newSound(Gdx.files.internal("character_audio/swoosh.wav"));
    private final Sound sniper = Gdx.audio.newSound(Gdx.files.internal("character_audio/sniper.wav"));
    private final Sound shotgun = Gdx.audio.newSound(Gdx.files.internal("character_audio/shotgun.wav"));

    public enum SoundType {
        PISTOL, JUMP, SNIPER, SHOTGUN
    }

    public void playSound(SoundType soundType) {
        switch (soundType){
            case PISTOL -> pistol.play();
            case JUMP -> jump.play();
            case SNIPER -> sniper.play();
            case SHOTGUN -> shotgun.play();
        }
    }

}
