package inf112.mayhem.view.screens.activeGameView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    private float bodyOffsetX = 0f;
    private float bodyOffsetY = 170f;
    private float faceOffsetX = 65f;
    private float faceOffsetY = 300f;
    private float leftFootOffsetX = -60f;
    private float leftFootOffsetY = 0f;
    private float rightFootOffsetX = 60f;
    private float rightFootOffsetY = 0f;
    private float gunOffsetX = 40f;
    private float gunoffsetY = 160f;
    private float leftHandOffsetX = -10f;
    private float leftHandOffsetY = 135f;
    private float rightHandOffsetX = 85f;
    private float rightHandOffsetY = 125f;
    private float headOffsetX = 50f;
    private float headOffsetY = 300f;


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
        body = new BodyPart(bodyTex, bodyOffsetX * scale, bodyOffsetY * scale);
        face = new BodyPart(faceTex, faceOffsetX * scale, faceOffsetY * scale);
        leftFoot = new BodyPart(leftFootTex, leftFootOffsetX * scale, leftFootOffsetY * scale);
        rightFoot = new BodyPart(rightFootTex, rightFootOffsetX * scale, rightFootOffsetY * scale);
        gun = new BodyPart(gunTex, gunOffsetX * scale, gunoffsetY * scale);
        leftHand = new BodyPart(leftHandTex, leftHandOffsetX * scale, leftHandOffsetY * scale);
        rightHand = new BodyPart(rightHandTex, rightHandOffsetX * scale, rightHandOffsetY * scale);
        head = new BodyPart(headTex, headOffsetX * scale, headOffsetY * scale);

        setScale(scale);
    }

    public void setWeapon(WeaponView weaponView) {
        this.gun = weaponView.getCurrentWeapon();
        this.gun.setScale(scale);
        this.gun.setOffset(gunOffsetX * scale, gunoffsetY * scale);

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
        body.setOffset(bodyOffsetX * scale, bodyOffsetY * scale);
        face.setOffset(faceOffsetX * scale, faceOffsetY * scale);
        leftFoot.setOffset(leftFootOffsetX * scale, leftFootOffsetY * scale);
        rightFoot.setOffset(rightFootOffsetX * scale, rightFootOffsetY * scale);
        gun.setOffset(gunOffsetX * scale, gunoffsetY * scale);
        leftHand.setOffset(leftHandOffsetX * scale, leftHandOffsetY * scale);
        rightHand.setOffset(rightHandOffsetX * scale, rightHandOffsetY * scale);
        head.setOffset(headOffsetX * scale, headOffsetY * scale);
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
    public void draw(SpriteBatch batch, float x, float y, boolean mirrored) {

        float feetHeight = leftFoot.getHeight();

        float baseY = y + feetHeight / 2f;

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
