package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import inf112.mayhem.model.weapons.WeaponType;

public class WeaponViewFactory {

    private final float scale = 0.15f;

    public WeaponView createWeaponView(WeaponType weapon) {
        switch (weapon) {
            case SHOTGUN:
                return new WeaponView(new Texture("weapons/shotgun.png"),
                        40f, 160f, scale);
            case AK:
                return new WeaponView(new Texture("weapons/assault_rifle.png"),
                        40f, 160f, scale);
            case SWORD:
                return new WeaponView(new Texture("weapons/sword.png"),
                        40f, 160f, scale);
            case SNIPER:
                return new WeaponView(new Texture("weapons/sniper.png"),
                        40f, 160f, scale);
            default:
                return new WeaponView(new Texture("weapons/pistol.png"),
                        40f, 160f, scale);
        }
    }
}
