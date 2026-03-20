package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;

public class WeaponView {

    private BodyPart weapon;

    public WeaponView (Texture texture, float offsetX, float offsetY, float scale) {
        this.weapon = new BodyPart(texture, offsetX, offsetY);
        this.weapon.setScale(scale);
    }

    public BodyPart getCurrentWeapon() {
        return weapon;
    }
}
