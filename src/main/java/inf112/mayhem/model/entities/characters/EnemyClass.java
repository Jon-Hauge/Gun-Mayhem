package inf112.mayhem.model.entities.characters;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import inf112.mayhem.model.AppWorld;
import inf112.mayhem.model.entities.EntityModel;
import inf112.mayhem.model.entities.EntityState;
import inf112.mayhem.model.entities.EntityType;
import inf112.mayhem.model.projectile.AbstractProjectile;
import inf112.mayhem.model.projectile.ProjectileFactory;

public class EnemyClass extends AbstractCharacter {

    private static final float WIDTH = 32f;
    private static final float HEIGHT = 32f;

    private int aggression; // 1-5
    private float moveSpeed;
    private float detectionRange;
    private float attackRange;
    private float reactionTime;
    private float reactionTimer;
    private float shootingTimer;
    private float patrolSpeed;
    private float patrolDistance;
    private float patrolOrigin;
    private int patrolDirection;
    private boolean playerDetected;
    private AppWorld world;

    private float edgeWaitTimer = 0f;
    private static final float EDGE_WAIT_DURATION = 0.5f;
    private float spawnDelayTimer = 1.0f;
    private boolean hasJumped = false;

    private static final float BASE_SPEED = 60f;
    private static final float BASE_DETECTION_RANGE = 100f;
    private static final float BASE_ATTACK_RANGE = 40f;
    private static final float BASE_REACTION_TIME = 1.5f;
    private static final float BASE_PATROL_SPEED = 30f;
    private static final float BASE_PATROL_DISTANCE = 80f;

    /// Initializes a new enemy at the specified position with a given aggression level.
    /// Enemy represents an AI-controlled character in the game with different aggression
    /// levels that affect its movement speed, detection range, attack range, and reaction time.
    ///
    /// @param startX The starting vertical position
    /// @param startY The starting horizontal position
    /// @param map The tiled map for collision detection
    /// @param aggression The aggression level (1-5)
    ///
    public EnemyClass(float startX, float startY, TiledMap map, int aggression) {
        super(startX, startY, WIDTH, HEIGHT, 3, map);
        this.type = EntityType.ENEMY;
        this.state = EntityState.IDLE;
        this.patrolOrigin = startX;
        this.patrolDirection = 1;
        this.playerDetected = false;
        this.reactionTimer = 0;
        this.shootingTimer = 0;
        setAggression(aggression);
    }

    /// Sets the aggression level (1-5) and adjusts all AI parameters accordingly.
    ///
    /// Level 1: Passive - Slow, short range, slow reaction
    /// Level 2: Cautious
    /// Level 3: Normal
    /// Level 4: Aggressive
    /// Level 5: Furious - Fast, long range, immediate reaction
    ///
    /// @param aggression The desired aggression level.
    ///
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

    ///
    /// Returns the current aggression level of the enemy.
    ///
    /// @return The aggression level (1-5).
    ///
    public int getAggression() {
        return aggression;
    }

    @Override
    public float getDamagePercentage() {
        return damagePercent;
    }

    @Override
    public void jump() {
        if (isGrounded) {
            velocity.y = JUMP_FORCE * jumpBoostMultiplier;
            isGrounded = false;
            jump_counter++;
            hasJumped = true;
        } else if (jump_counter < 2 && aggression >= 3) {
            velocity.y = DOUBLE_JUMP_FORCE * jumpBoostMultiplier;
            jump_counter++;
            hasJumped = true;
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
    public void hit(AbstractProjectile obj) {
        super.hit(obj);
        float knockbackResistance = 1.0f - (aggression - 1) * 0.15f;
        applyKnockback(obj.getKnockBack() * knockbackResistance, obj.getDirection());
    }

    @Override
    public void update(AppWorld world, float delta) {
        super.update(world, delta);
        if (waitingForRespawn) {
            return;
        }

        this.world = world;
        if (shootingTimer > 0) shootingTimer -= delta;
        if (edgeWaitTimer > 0) edgeWaitTimer -= delta;
        if (spawnDelayTimer > 0) {
            spawnDelayTimer -= delta;
            velocity.x = 0;
        }

        // AI logic
        PlayerClass player = findClosestPlayer(world);
        if (player != null && player.isActive() && spawnDelayTimer <= 0) {
            float distX = player.bounds().x - bounds.x;
            float distY = player.bounds().y - bounds.y;
            float distance = (float) Math.sqrt(distX * distX + distY * distY);

            if (distance <= detectionRange) {
                reactionTimer += delta;
                if (reactionTimer >= reactionTime) {
                    playerDetected = true;
                }
            } else {
                playerDetected = false;
                reactionTimer = 0;
            }

            if (playerDetected && spawnDelayTimer <= 0) {
                chasePlayer(distX, distY, distance, delta);
            } else if (spawnDelayTimer <= 0) {
                patrol(delta);
            }
        } else if (spawnDelayTimer <= 0) {
            patrol(delta);
        }

        // Physics
        float previousY = position.y;

        velocity.y -= GRAVITY * delta * gravityMultiplier;

        position.x += velocity.x * delta * speedMultiplier;

        position.x += knockbackVelocity * delta;
        knockbackVelocity *= Math.max(0, 1 - KNOCKBACK_DECAY * delta);

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
                waitingForRespawn = true;
                respawnTimer = 0f;
                velocity.y = 0;
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

    /// Chases the player using smart jumping and platform awareness.
    /// Higher aggression levels jump more willingly and take more risks in the air.
    ///
    private void chasePlayer(float distX, float distY, float distance, float delta) {
        int targetDirection = distX > 0 ? 1 : -1;

        if (edgeWaitTimer <= 0) {
            direction = targetDirection;
        }

        handleShooting();

        boolean atEdge = isAtEdge();

        if (Math.abs(distX) > attackRange * 0.8f) {
        	if (!isGrounded) {
        	    System.out.println("In air — platformBelow: " + hasPlatformBelow() + " jumpCounter: " + jump_counter);
        	    if (!hasPlatformBelow()) {
        	        emergencyJump(distX);
        	    }
        	    velocity.x = direction * moveSpeed * speedMultiplier;
            } else if (!atEdge) {
                velocity.x = direction * moveSpeed * speedMultiplier;
            } else {
                // Ved kant — hopp over istedet for å stoppe
                velocity.x = direction * moveSpeed * speedMultiplier;
                smartJump(distY);
                edgeWaitTimer = EDGE_WAIT_DURATION;
            }
        } else {
            velocity.x = 0;
        }

        // Hopp opp mot spiller hvis de er høyere
        if (distY > HEIGHT) {
            smartJump(distY);
        }

        // Drop ned hvis spiller er lavere
        if (distY < -HEIGHT && isGrounded) {
            drop();
        }
    }

    /// Performs a smart jump that uses double jump only when needed and beneficial.
    /// Higher aggression levels double jump more aggressively.
    ///
    /// @param distY Vertical distance to the player (positive = player is above)
    ///
    private void smartJump(float distY) {
        if (isGrounded) {
            jump();
        } else if (jump_counter < 2 && aggression >= 3) {
            // Dobbelthopp hvis spilleren er høyt oppe, skalert med aggresjon
            float doubleJumpThreshold = HEIGHT * (3 - aggression * 0.4f);
            if (distY > doubleJumpThreshold) {
                jump();
            }
        }
    }

    /// Checks whether there is a platform within falling range below the enemy.
    /// Uses a sensor rectangle that scales with aggression — more aggressive enemies
    /// look further down before panicking.
    ///
    /// @return true if a platform is detected below, false otherwise
    ///
    private boolean hasPlatformBelow() {
        float lookDistance = 64f + aggression * 20f;
        Rectangle sensor = new Rectangle(bounds.x, bounds.y - lookDistance, bounds.width, lookDistance);
        boolean result = collision.isGrounded(sensor, bounds.y, ignorePlatforms);
        System.out.println("hasPlatformBelow: " + result + " | inAir: " + !isGrounded + " | jumps: " + jump_counter);
        return result;
    }

    /// Performs an emergency double jump when no platform is detected below.
    /// The enemy jumps toward the side the player is on to land on a platform.
    /// Only triggers for aggression level 2 and above.
    ///
    /// @param distX Horizontal distance to the player (positive = player is to the right)
    ///
    private void emergencyJump(float distX) {
        if (aggression < 2 || jump_counter >= 2) return;
        direction = distX > 0 ? 1 : -1;
        jump();
    }

    /// Handles shooting logic when chasing the player.
    /// Firing rate scales with aggression level.
    ///
    private void handleShooting() {
        if (shootingTimer > 0 || getEquippedWeapon() == null) return;

        if (getEquippedWeapon().shoot()) {
            float spawnX = getDirection() == 1 ? x() + width() : x() - 10;
            float spawnY = y() + (height() / 2);

            AbstractProjectile projectile = ProjectileFactory.create(
                spawnX, spawnY,
                getDirection(),
                getEquippedWeapon().getWeaponType()
            );
            if (projectile != null) world.add(projectile);

            shootingTimer = getEquippedWeapon().getFiringDelay();
            shootingTimer *= (1.2f - (aggression * 0.1f));
        }
    }

    private boolean isAtEdge() {
        if (!isGrounded) return false;

        float sensorX = direction == 1 ? bounds.x + bounds.width : bounds.x - 5;
        Rectangle edgeSensor = new Rectangle(sensorX, bounds.y - 1, 5, bounds.height);

        boolean groundBelow = collision.isGrounded(edgeSensor, bounds.y, ignorePlatforms);

        if (!groundBelow) {
            groundBelow = collision.isGrounded(edgeSensor, bounds.y - 64, ignorePlatforms);
        }

        return !groundBelow;
    }

    private void patrol(float delta) {
        if (edgeWaitTimer <= 0) {
            direction = patrolDirection;
            if (isAtEdge()) {
                patrolDirection *= -1;
                edgeWaitTimer = EDGE_WAIT_DURATION;
            }
        }

        velocity.x = patrolDirection * patrolSpeed * speedMultiplier;

        if (Math.abs(position.x - patrolOrigin) >= patrolDistance) {
            if (edgeWaitTimer <= 0) {
                patrolDirection *= -1;
                edgeWaitTimer = EDGE_WAIT_DURATION;
            }
        }
    }

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

    @Override
    public boolean hasJumped() {
        return hasJumped;
    }

    @Override
    public void jumpSoundMade() {
        hasJumped = false;
    }
}