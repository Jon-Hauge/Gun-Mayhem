package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.mayhem.model.entities.weapons.AbstractWeapon;

public class CharacterRig {
    public BodyPart body;
    public BodyPart face;
    public BodyPart leftFoot;
    public BodyPart rightFoot;
    public BodyPart gun;
    public BodyPart leftHand;
    public BodyPart rightHand;
    public BodyPart head;

    private float scale;

    private final float BODY_OFFSET_X = 0f;
    private final float BODY_OFFSET_Y = 170f;
    private final float FACE_OFFSET_X = 65f;
    private final float FACE_OFFSET_Y = 300f;
    private final float LEFT_FOOT_OFFSET_X = -60f;
    private final float LEFT_FOOT_OFFSET_Y = 0f;
    private final float RIGHT_FOOT_OFFSET_X = 60f;
    private final float RIGHT_FOOT_OFFSET_Y = 0f;
    private final float GUN_OFFSET_X = 60f;
    private final float GUN_OFFSET_Y = 140f;
    private final float LEFT_HAND_OFFSET_X = -10f;
    private final float LEFT_HAND_OFFSET_Y = 135f;
    private final float RIGHT_HAND_OFFSET_X = 85f;
    private final float RIGHT_HAND_OFFSET_Y = 125f;
    private final float HEAD_OFFSET_X = 50f;
    private final float HEAD_OFFSET_Y = 300f;


    /// Initiates a character composed of several bodypart textures.
    ///
    /// @param bodyTex Body of the character
    /// @param faceTex Face of the character
    /// @param leftFootTex Left foot of the character
    /// @param rightFootTex Right foot of the character
    /// @param gunTex Gun the character carries
    /// @param leftHandTex Left hand of the character
    /// @param rightHandTex Right hand of the character
    /// @param headTex Head of the character
    /// @param scale Scale of the character
    ///
    public CharacterRig(Texture bodyTex, Texture faceTex, Texture leftFootTex,
                        Texture rightFootTex, Texture gunTex, Texture leftHandTex,
                        Texture rightHandTex, Texture headTex, float scale) {
        this.scale = scale;
        body = new BodyPart(bodyTex, BODY_OFFSET_X * scale, BODY_OFFSET_Y * scale);
        face = new BodyPart(faceTex, FACE_OFFSET_X * scale, FACE_OFFSET_Y * scale);
        leftFoot = new BodyPart(leftFootTex, LEFT_FOOT_OFFSET_X * scale, LEFT_FOOT_OFFSET_Y * scale);
        rightFoot = new BodyPart(rightFootTex, RIGHT_FOOT_OFFSET_X * scale, RIGHT_FOOT_OFFSET_Y * scale);
        gun = new BodyPart(gunTex, GUN_OFFSET_X * scale, GUN_OFFSET_Y * scale);
        leftHand = new BodyPart(leftHandTex, LEFT_HAND_OFFSET_X * scale, LEFT_HAND_OFFSET_Y * scale);
        rightHand = new BodyPart(rightHandTex, RIGHT_HAND_OFFSET_X * scale, RIGHT_HAND_OFFSET_Y * scale);
        head = new BodyPart(headTex, HEAD_OFFSET_X * scale, HEAD_OFFSET_Y * scale);

        setScale(scale);
    }

    /// Sets weapon based on view
    ///
    /// @param weaponView weaponView to set weapon to
    ///
    public void setWeapon(WeaponView weaponView) {
        this.gun = weaponView.toBodyPart();
        this.gun.setOffset(GUN_OFFSET_X * scale, GUN_OFFSET_Y * scale);
        this.gun.setScale(scale);

    }

    private void setScale(float scale) {
        this.scale = scale;

        body.setScale(scale);
        face.setScale(scale);
        leftFoot.setScale(scale);
        rightFoot.setScale(scale);
        gun.setScale(scale);
        leftHand.setScale(scale);
        rightHand.setScale(scale);
        head.setScale(scale);

        resetOffsets();
    }

    /// Resets added offsets horizontally and vertically to defaults
    ///
    public void resetOffsets() {
        body.setOffset(BODY_OFFSET_X * scale, BODY_OFFSET_Y * scale);
        face.setOffset(FACE_OFFSET_X * scale, FACE_OFFSET_Y * scale);
        leftFoot.setOffset(LEFT_FOOT_OFFSET_X * scale, LEFT_FOOT_OFFSET_Y * scale);
        rightFoot.setOffset(RIGHT_FOOT_OFFSET_X * scale, RIGHT_FOOT_OFFSET_Y * scale);
        gun.setOffset(GUN_OFFSET_X * scale, GUN_OFFSET_Y * scale);
        leftHand.setOffset(LEFT_HAND_OFFSET_X * scale, LEFT_HAND_OFFSET_Y * scale);
        rightHand.setOffset(RIGHT_HAND_OFFSET_X * scale, RIGHT_HAND_OFFSET_Y * scale);
        head.setOffset(HEAD_OFFSET_X * scale, HEAD_OFFSET_Y * scale);
    }

    /// Draws the character on a canvas.
    /// Is called multiple times per second.
    /// Order determines what is pushed to the front,
    /// e.g. feet drawn before body makes feet appear behind body.
    ///
    /// @param batch Canvas to draw on
    /// @param x Where to draw character horizontally
    /// @param y Where to draw character vertically
    /// @param mirrored Whether character is mirrored on the x-axis
    ///
    public void draw(SpriteBatch batch, float x, float y, boolean mirrored, AbstractWeapon equippedWeapon) {

        float feetHeight = leftFoot.getHeight();
        float baseY = y + feetHeight / 2f;

        if (equippedWeapon != null) {
            setWeapon(new WeaponView(equippedWeapon));
        }

        rightFoot.draw(batch, x, baseY, mirrored);
        body.draw(batch, x, baseY, mirrored);
        leftFoot.draw(batch, x, baseY, mirrored);
        head.draw(batch, x, baseY, mirrored);
        face.draw(batch, x, baseY, mirrored);
        gun.draw(batch, x, baseY, mirrored);
        leftHand.draw(batch, x, baseY, mirrored);
        rightHand.draw(batch, x, baseY, mirrored);
    }
}
