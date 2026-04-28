package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class WeaponView {

    private Texture texture;
    private float offsetX;
    private float offsetY;
    private float scale;
    private BodyPart weaponBodyPart;
    private AbstractWeapon weapon;

    ///
    /// Constructs a new HudView with the given world and controller.
    ///
    /// @param texture Texture of weapon
    /// @param offsetX Horizontal offset
    /// @param offsetY Vertical offset
    /// @param scale   Scale of texture
    ///
    public WeaponView(Texture texture, float offsetX, float offsetY, float scale, AbstractWeapon weapon) {
        this.texture = texture;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.scale = scale;
        this.weaponBodyPart = new BodyPart(texture, offsetX, offsetY);
        this.weaponBodyPart.setScale(scale);
        this.weapon = weapon;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture,
                offsetX,
                offsetY,
                weaponBodyPart.getWidth(),
                weaponBodyPart.getHeight());
    }

    /// Turns a view to a bodypart
    ///
    /// @return Bodypart version of view
    ///
    public BodyPart toBodyPart() {
        BodyPart viewToBodyPart = new BodyPart(texture, offsetX, offsetY);
        viewToBodyPart.setScale(scale);
        return viewToBodyPart;
    }
}
