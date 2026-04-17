package inf112.mayhem.model;

import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.mayhem.model.abstracts.AbstractCharacter;
import inf112.mayhem.model.abstracts.AbstractProjectile;
import inf112.mayhem.model.enums.EntityState;
import inf112.mayhem.model.enums.EntityType;
import inf112.mayhem.model.interfaces.EntityModel;

public class EnemyClass extends AbstractCharacter {

    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    // AI-parametere
    private int aggression; // 1-5
    private float moveSpeed;
    private float detectionRange;
    private float attackRange;
    private float reactionTime;
    private float reactionTimer;
    private float patrolSpeed;
    private float patrolDistance;
    private float patrolOrigin;
    private int patrolDirection;
    private boolean playerDetected;

    // Aggresjon-baserte konstanter
    private static final float BASE_SPEED = 60f;
    private static final float BASE_DETECTION_RANGE = 100f;
    private static final float BASE_ATTACK_RANGE = 40f;
    private static final float BASE_REACTION_TIME = 1.5f;
    private static final float BASE_PATROL_SPEED = 30f;
    private static final float BASE_PATROL_DISTANCE = 80f;

    public EnemyClass(float startX, float startY, TiledMap map, int aggression) {
        super(startX, startY, WIDTH, HEIGHT, 3, map);
        this.type = EntityType.ENEMY;
        this.state = EntityState.IDLE;
        this.patrolOrigin = startX;
        this.patrolDirection = 1;
        this.playerDetected = false;
        this.reactionTimer = 0;
        setAggression(aggression);
    }

    /**
     * Setter aggresjonsnivå (1-5) og justerer alle AI-parametere.
     *
     * Nivå 1: Passiv — sakte, kort rekkevidde, treg reaksjon
     * Nivå 2: Forsiktig
     * Nivå 3: Normal
     * Nivå 4: Aggressiv
     * Nivå 5: Rasende — rask, lang rekkevidde, umiddelbar reaksjon
     */
    public void setAggression(int aggression) {
        this.aggression = Math.max(1, Math.min(5, aggression));
        float scale = this.aggression;

        this.moveSpeed = BASE_SPEED + (scale - 1) * 25f;
        this.detectionRange = BASE_DETECTION_RANGE + (scale - 1) * 50f;
        this.attackRange = BASE_ATTACK_RANGE + (scale - 1) * 10f;
        this.reactionTime = Math.max(0.1f, BASE_REACTION_TIME - (scale - 1) * 0.3f);
        this.patrolSpeed = BASE_PATROL_SPEED + (scale - 1) * 15f;
        this.patrolDistance = BASE_PATROL_DISTANCE + (scale - 1) * 30f;
    }

    public int getAggression() {
        return aggression;
    }

    @Override
    public void jump() {
        if (isGrounded) {
            velocity.y = JUMP_FORCE;
            isGrounded = false;
            jump_counter++;
        } else if (jump_counter < 2 && aggression >= 3) {
            // Bare fiender med aggresjon >= 3 kan dobbelthoppe
            velocity.y = DOUBLE_JUMP_FORCE;
            jump_counter++;
        }
    }

    @Override
    public void drop() {
        if (isGrounded) {
            velocity.y -= GRAVITY - 230f;
            isGrounded = false;
            ignorePlatforms = true;
            dropDuration = DROP_DURATION;
        }
    }

    @Override
    public void hit(EntityModel obj) {
        AbstractProjectile projectile = (AbstractProjectile) obj;
        // Lavere aggresjon = mer knockback (lettere å slå tilbake)
        float knockbackResistance = 1.0f - (aggression - 1) * 0.15f;
        velocity.x += projectile.getKnockBackMultiplier()
                    * projectile.getDirection()
                    * knockbackResistance;
    }

    @Override
    public void updateState() {
        if (!isGrounded && velocity.y > 0) {
            state = EntityState.JUMPING;
        } else if (!isGrounded && velocity.y < 0) {
            state = EntityState.FALLING;
        } else if (velocity.x != 0) {
            state = EntityState.WALKING;
        } else {
            state = EntityState.IDLE;
        }
    }

    @Override
    public void update(AppWorld world, float delta) {
    	
        // --- AI-logikk ---
        PlayerClass player = findClosestPlayer(world);
        if (player != null && player.isActive()) {
            float distX = player.bounds().x - bounds.x;
            float distY = player.bounds().y - bounds.y;
            float distance = (float) Math.sqrt(distX * distX + distY * distY);
            getEquippedWeapon().shoot();

            if (distance <= detectionRange) {
                reactionTimer += delta;
                if (reactionTimer >= reactionTime) {
                    playerDetected = true;
                }
            } else {
                playerDetected = false;
                reactionTimer = 0;
            }

            if (playerDetected) {
                chasePlayer(distX, distY, distance, delta);
            } else {
                patrol(delta);
            }
        } else {
            patrol(delta);
        }

        // --- Fysikk (som PlayerClass) ---
        float previousY = position.y;

        velocity.y -= GRAVITY * delta;

        position.x += velocity.x * delta;
        updateBounds();

        position.y += velocity.y * delta;
        updateBounds();

        if (velocity.y <= 0 && collision.isGrounded(bounds, previousY, ignorePlatforms)) {
            isGrounded = true;
            ignorePlatforms = false;
            velocity.y = 0;
            position.y = collision.getGroundY(bounds, ignorePlatforms);
            jump_counter = 0;
            updateBounds();
        } else if (outOfBounds(world)) {
            lives--;
            if (lives <= 0) {
                die();
            } else {
                respawn();
                updateBounds();
            }
        } else {
            isGrounded = false;
        }

        if (ignorePlatforms) {
            dropDuration -= delta;
            if (dropDuration <= 0) ignorePlatforms = false;
        }

        position.x = Math.max(-WORLD_PADDING,
                Math.min(position.x, (float) world.width() + WORLD_PADDING));
        position.y = Math.max(-WORLD_PADDING,
                Math.min(position.y, (float) world.height() + WORLD_PADDING));

        updateBounds();
        updateState();
    }

    /**
     * Jager spilleren — beveger seg mot dem, hopper hvis de er over,
     * og dropper hvis de er under.
     */
    private void chasePlayer(float distX, float distY, float distance, float delta) {
        // Beveg mot spiller horisontalt
        if (Math.abs(distX) > attackRange) {
            direction = distX > 0 ? 1 : -1;
            velocity.x = direction * moveSpeed;
        } else {
            velocity.x = 0;
            // Innenfor angrepsrekkevidde — angrip
            if (getEquippedWeapon() != null) {
                getEquippedWeapon().shoot();
            }
        }

        // Hopp hvis spilleren er høyere opp
        if (distY > HEIGHT && isGrounded) {
            jump();
        }

        // Drop ned hvis spilleren er under
        if (distY < -HEIGHT * 2) {
            drop();
        }
    }

    /**
     * Patruljerer frem og tilbake rundt startposisjonen.
     */
    private void patrol(float delta) {
        velocity.x = patrolDirection * patrolSpeed;
        direction = patrolDirection;

        if (Math.abs(position.x - patrolOrigin) >= patrolDistance) {
            patrolDirection *= -1;
        }
    }

    /**
     * Finner nærmeste aktive spiller i verden.
     * Krever at AppWorld har en metode for å hente spillere.
     */
    private PlayerClass findClosestPlayer(AppWorld world) {
        PlayerClass closest = null;
        float closestDist = Float.MAX_VALUE;

        for (EntityModel obj : world) {
            if (obj instanceof PlayerClass player && player.isActive()) {
                float dx = player.bounds().x - bounds.x;
                float dy = player.bounds().y - bounds.y;
                float dist = dx * dx + dy * dy;
                if (dist < closestDist) {
                    closestDist = dist;
                    closest = player;
                }
            }
        }
        return closest;
    }

}