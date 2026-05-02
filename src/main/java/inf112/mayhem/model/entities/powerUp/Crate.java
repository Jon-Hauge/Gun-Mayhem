package inf112.mayhem.model.entities.powerUp;

import com.badlogic.gdx.math.Rectangle;
import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.AbstractEntity;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.entities.characters.AbstractCharacter;

public class Crate extends AbstractEntity {
    private boolean opened = false;
    private boolean active = true;
    private boolean grounded = false;
    protected float velocityY = 0;
    protected float gravity = -10f;
    private final PowerUp powerUp;
    private final static float powerUpDuration = 15f;
    private float timer = 0f;
    private final float lifespan = 20f;


    /// Initiates a crate entity containing powerUps.
    ///
    /// @param x Horizontal coordinate of crate
    /// @param y Vertical coordinate of crate
    /// @param width Width of crate
    /// @param height Height of crate
    /// @param powerUp PowerUp contained within crate
    ///
    public Crate(float x, float y, float width, float height, PowerUp powerUp) {
        super(x, y, width, height);
        this.type = EntityType.CRATE;
        this.state = EntityState.UNOPENED_WEAPON_CRATE;
        this.powerUp = powerUp;
    }

    /// Opens crate revealing powerUp inside.
    ///
    /// @param world Gameworld crate is opened in
    /// @param character Character who opens crate
    ///
    public void open(AppWorld world, AbstractCharacter character) {
        if (!opened) {
            opened = true;
            state = EntityState.OPENED_WEAPON_CRATE;
            character.addPowerUp(powerUp, powerUpDuration);
            world.showPopup(getPowerUpName());
        }
    }

    /// Returns the name of the powerUp inside the crate.
    /// Used for displaying powerUp-names in views.
    ///
    /// @return name of powerUp inside crate
    ///
    public String getPowerUpName() {
        if (powerUp instanceof JumpBoostPowerUp) {
            return "Jump Boost";
        } else if (powerUp instanceof LowGravityPowerUp)  {
            return "Low Gravity";
        } else {
            return "Speed Boost";
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void update(AppWorld world, float delta) {

        if (state == EntityState.UNOPENED_WEAPON_CRATE) {
            timer += delta;
        }

        updateBounds();
        updateState();

        if (state == EntityState.UNOPENED_WEAPON_CRATE && !grounded) {
            velocityY += gravity * delta;
            float previousY = position.y;
            position.y += velocityY * delta;

            Rectangle bounds = bounds();
            bounds.setPosition(position.x, position.y);

            if (world.getMapCollisionHandler().isGrounded(bounds, previousY, false)) {
                grounded = true;
                velocityY = 0;
                position.y = world.getMapCollisionHandler().getGroundY(bounds, false);
            }
        }
    }

    @Override
    public void updateState() {
        if (position.y < -100f) {
            active = false;
        }
        if (timer > lifespan) {
            active = false;
        }
        if (opened) {
            active = false;
            state = EntityState.OPENED_WEAPON_CRATE;
        }
    }
}
