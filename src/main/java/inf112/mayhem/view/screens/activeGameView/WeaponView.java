package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;
import inf112.mayhem.model.entities.weapons.WeaponType;

public class WeaponView {

    private final Texture texture;
    private final BodyPart weaponBodyPart;
    private final AbstractWeapon weapon;
    private final float SCALE = 0.15f;

    /// Constructs a WeaponView for a given weapon
    ///
    /// @param weapon Weapon to be viewed
    ///
    public WeaponView(AbstractWeapon weapon) {
        this.weapon = weapon;
        this.texture = getTexture(weapon.getWeaponType());
        this.weaponBodyPart = new BodyPart(texture, weapon.x(), weapon.y());
        this.weaponBodyPart.setScale(SCALE);
    }

    private Texture getTexture(WeaponType weaponType) {
        return switch (weaponType) {
            case PISTOL -> new Texture("weapons/pistol.png");
            case SNIPER -> new Texture("weapons/sniper.png");
            case PUMPACTION -> new Texture("weapons/pumpaction.png");
            case SEMIAUTOSHOTGUN -> new Texture("weapons/semiaction.png");
            default -> new Texture("weapons/pistol.png");
        };
    }

    /// Draws the weapon in the gameworld
    ///
    /// @param batch Batch to draw on
    ///
    public void draw(SpriteBatch batch) {
        batch.draw(texture,
                weapon.x(),
                weapon.y(),
                weaponBodyPart.getWidth(),
                weaponBodyPart.getHeight());
    }

    /// Turns a view to a bodypart
    ///
    /// @return Bodypart version of view
    ///
    public BodyPart toBodyPart() {
        return new BodyPart(texture, weapon.x(), weapon.y());
    }
}
