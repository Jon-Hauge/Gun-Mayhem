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

    private float bodyOffsetX = 0;
    private float bodyOffsetY = -70;
    private float faceOffsetX = -50;
    private float faceOffsetY = 330;
    private float leftFootOffsetX = -60;
    private float leftFootOffsetY = 150;
    private float rightFootOffsetX = 60;
    private float rightFootOffsetY = 150;
    private float gunOffsetX = -25;
    private float gunoffsetY = 375;
    private float leftHandOffsetX = -100;
    private float leftHandOffsetY = 200;
    private float rightHandOffsetX = 55;
    private float rightHandOffsetY = 200;
    private float headOffsetX = -50;
    private float headOffsetY = 380;


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
        body = new BodyPart(bodyTex, bodyOffsetX, bodyOffsetY);
        face = new BodyPart(faceTex, faceOffsetX, faceOffsetY * scale);
        leftFoot = new BodyPart(leftFootTex, leftFootOffsetX * scale, leftFootOffsetY * scale);
        rightFoot = new BodyPart(rightFootTex, rightFootOffsetX * scale, rightFootOffsetY * scale);
        gun = new BodyPart(gunTex, gunOffsetX * scale, gunoffsetY * scale);
        leftHand = new BodyPart(leftHandTex, leftHandOffsetX * scale, leftHandOffsetY * scale);
        rightHand = new BodyPart(rightHandTex, rightHandOffsetX * scale, rightHandOffsetY * scale);
        head = new BodyPart(headTex, headOffsetX * scale, headOffsetY * scale);

        setScale(scale);
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
    /// Order determines what is pushed to the front.
    ///
    /// @param batch Canvas to draw on
    /// @param x Where to draw character horizontally
    /// @param y Where to draw character vertically
    /// @param mirrored Whether character is mirrored on the x-axis
    ///
    public void draw(SpriteBatch batch, float x, float y, boolean mirrored) {
        leftFoot.draw(batch, x, y, mirrored);
        rightFoot.draw(batch, x, y, mirrored);
        body.draw(batch, x, y, mirrored);
        head.draw(batch, x, y, mirrored);
        face.draw(batch, x, y, mirrored);
        leftHand.draw(batch, x, y, mirrored);
        rightHand.draw(batch, x, y, mirrored);
        gun.draw(batch, x, y, mirrored);
    }
}
