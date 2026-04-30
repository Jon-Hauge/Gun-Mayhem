package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.mayhem.model.weapons.AbstractWeapon;
import inf112.mayhem.model.weapons.WeaponType;

public class WeaponView {

    private Texture texture;
    private BodyPart weaponBodyPart;
    private AbstractWeapon weapon;
    private final float scale = 0.15f;

    ///
    /// Constructs a new WeaponView with a given weapon
    ///
    /// @param weapon Weapon to be viewed
    ///
    public WeaponView(AbstractWeapon weapon) {
        this.weapon = weapon;
        this.texture = getTexture(weapon.getWeaponType());
        this.weaponBodyPart = new BodyPart(texture, weapon.x(), weapon.y());
        this.weaponBodyPart.setScale(scale);
    }

    private Texture getTexture(WeaponType weaponType) {
        return switch (weaponType) {
            case PISTOL -> new Texture("weapons/pistol.png");
            case SNIPER -> new Texture("weapons/sniper.png");
            case PUMPACTION -> new Texture("weapons/shotgun.png");
            case SEMIACTIONSHOTGUN -> new Texture("weapons/shotgun.png");
            case SWORD -> new Texture("weapons/sword.png");
            default -> new Texture("weapons/pistol.png");
        };
    }

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
