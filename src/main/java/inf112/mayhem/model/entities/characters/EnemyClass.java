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

    private int aggression;
    private float moveSpeed;
    private float attackRange;
    private float shootingTimer;
    private float patrolSpeed;
    private float patrolDistance;
    private float patrolOrigin;
    private int patrolDirection;

    private float edgeWaitTimer = 0f;
    private static final float EDGE_WAIT_DURATION = 0.5f;

    private float spawnDelayTimer = 1.0f;
    private boolean hasJumped = false;
    private boolean hasLandedAfterSpawn = false;
    private boolean hasEdgeJumped = false;
    private boolean isCrossingGap = false;

    private float airJumpCheckTimer = 0f;
    private float jumpDecisionCooldown = 0f;

    private static final float AIR_JUMP_CHECK_INTERVAL = 1.2f;
    private static final float JUMP_DECISION_COOLDOWN = 1.2f;

    private static final float BASE_SPEED = 60f;
    private static final float BASE_ATTACK_RANGE = 40f;
    private static final float BASE_PATROL_SPEED = 30f;
    private static final float BASE_PATROL_DISTANCE = 80f;
    private static final int MAX_JUMPS = 4;

    /// Initializes a new enemy at the specified position with a given aggression level.
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
        this.shootingTimer = 0;
        this.hasLandedAfterSpawn = false;
        setAggression(aggression);
    }

    /// Sets the aggression level (1-5) and adjusts all AI parameters accordingly.
    ///
    /// @param aggression The desired aggression level.
    ///
    public void setAggression(int aggression) {
        this.aggression = Math.max(1, Math.min(5, aggression));
        float scale = this.aggression;

        this.moveSpeed = BASE_SPEED + (scale - 1) * 25f;
        this.attackRange = BASE_ATTACK_RANGE + (scale - 1) * 10f;
        this.patrolSpeed = BASE_PATROL_SPEED + (scale - 1) * 15f;
        this.patrolDistance = BASE_PATROL_DISTANCE + (scale - 1) * 30f;
    }

    ///
    /// @return The current aggression level.
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
            jump_counter = 1;
            hasJumped = true;
        } else if (jump_counter < MAX_JUMPS) {
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

        if (shootingTimer > 0) shootingTimer -= delta;
        if (edgeWaitTimer > 0) edgeWaitTimer -= delta;
        if (airJumpCheckTimer > 0) airJumpCheckTimer -= delta;
        if (jumpDecisionCooldown > 0) jumpDecisionCooldown -= delta;

        if (spawnDelayTimer > 0) {
            spawnDelayTimer -= delta;
            velocity.x = 0;
        }

        PlayerClass player = findClosestPlayer(world);

        if (player != null && player.isActive() && spawnDelayTimer <= 0) {
            float distX = player.bounds().x - bounds.x;
            float distY = player.bounds().y - bounds.y;
            float distance = (float) Math.sqrt(distX * distX + distY * distY);

            chasePlayer(world, player, distX, distY, distance, delta);
        } else if (spawnDelayTimer <= 0) {
            patrol(delta);
        }

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
            hasLandedAfterSpawn = true;

            ignorePlatforms = false;
            velocity.y = 0;
            position.y = collision.getGroundY(bounds, ignorePlatforms);

            jump_counter = 0;
            hasEdgeJumped = false;
            isCrossingGap = false;
            airJumpCheckTimer = 0f;
            jumpDecisionCooldown = 0f;

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

    /// Chases the player constantly using platform awareness.
    ///
    private void chasePlayer(AppWorld world, PlayerClass player, float distX, float distY, float distance, float delta) {
        direction = distX > 0 ? 1 : -1;

        handleShooting(world, player);

        if (Math.abs(distX) <= attackRange * 0.8f) {
            velocity.x = 0;
            return;
        }

        if (isGrounded && isAtEdge()) {
            velocity.x = direction * moveSpeed * speedMultiplier;

            if (!hasEdgeJumped && jumpDecisionCooldown <= 0) {
                jump();
                hasEdgeJumped = true;
                isCrossingGap = true;
                airJumpCheckTimer = AIR_JUMP_CHECK_INTERVAL;
                jumpDecisionCooldown = JUMP_DECISION_COOLDOWN;
            }

            return;
        }

        velocity.x = direction * moveSpeed * speedMultiplier;

        if (!isGrounded
                && hasLandedAfterSpawn
                && jump_counter < MAX_JUMPS
                && velocity.y <= 0
                && airJumpCheckTimer <= 0
                && jumpDecisionCooldown <= 0) {

            boolean noGroundBelow = !hasGroundAnywhereBelow(world);

            if (noGroundBelow) {
                isCrossingGap = true;
                emergencyJump(distX);
                airJumpCheckTimer = AIR_JUMP_CHECK_INTERVAL;
                jumpDecisionCooldown = JUMP_DECISION_COOLDOWN;
            }
        }

        if (isGrounded && distY > HEIGHT && !isAtEdge()) {
            smartJump(distY);
        }

        if (distY < -HEIGHT && isGrounded) {
            drop();
        }
    }

    /// Performs a smart jump only when the enemy is safely on a platform.
    ///
    /// @param distY Vertical distance to the player.
    ///
    private void smartJump(float distY) {
        if (!isGrounded || isAtEdge()) {
            return;
        }

        jump();
    }

    /// Scans all space below the enemy down to the bottom of the world.
    ///
    /// @param world The current game world.
    /// @return true if there is ground anywhere below the enemy.
    ///
    private boolean hasGroundAnywhereBelow(AppWorld world) {
        float bottomOfWorld = 0f;
        float scanHeight = bounds.y - bottomOfWorld;

        if (scanHeight <= 0) {
            return false;
        }

        Rectangle sensor = new Rectangle(
                bounds.x,
                bottomOfWorld,
                bounds.width,
                scanHeight
        );

        return collision.isGrounded(sensor, bounds.y, ignorePlatforms);
    }

    /// Performs an emergency jump toward the player when crossing a gap.
    ///
    /// @param distX Horizontal distance to the player.
    ///
    private void emergencyJump(float distX) {
        if (!hasLandedAfterSpawn || spawnDelayTimer > 0) {
            return;
        }

        if (aggression < 2 || jump_counter >= MAX_JUMPS) {
            return;
        }

        direction = distX > 0 ? 1 : -1;
        velocity.x = direction * moveSpeed * speedMultiplier;

        jump();

        airJumpCheckTimer = AIR_JUMP_CHECK_INTERVAL;
    }

    /// Handles shooting logic when chasing the player.
    ///
    /// @param world The current game world.
    /// @param player The player being targeted.
    ///
    private void handleShooting(AppWorld world, PlayerClass player) {
        if (shootingTimer > 0 || getEquippedWeapon() == null || player == null) {
            return;
        }

        float distX = player.bounds().x - bounds.x;
        float distY = Math.abs(player.bounds().y - bounds.y);

        float shootingRange = attackRange * 2f;
        float sameXAxisThreshold = HEIGHT * 0.5f;

        boolean playerInFront = Math.signum(distX) == direction;
        boolean inRange = Math.abs(distX) <= shootingRange;
        boolean sameXAxis = distY <= sameXAxisThreshold;

        if (playerInFront && inRange && sameXAxis) {
            if (getEquippedWeapon().shoot()) {
                float spawnX = getDirection() == 1 ? x() + width() : x() - 10;
                float spawnY = y() + height() / 2;

                AbstractProjectile projectile = ProjectileFactory.create(
                        spawnX,
                        spawnY,
                        getDirection(),
                        getEquippedWeapon().getWeaponType()
                );

                if (projectile != null) {
                    world.add(projectile);
                }

                shootingTimer = getEquippedWeapon().getFiringDelay();
                shootingTimer *= 1.2f - aggression * 0.1f;
            }
        }
    }

    /// Checks if the enemy is about to walk off the platform it is standing on.
    ///
    /// @return true if there is no ground in front of the enemy.
    ///
    private boolean isAtEdge() {
        if (!isGrounded) {
            return false;
        }

        float sensorX = direction == 1
                ? bounds.x + bounds.width + 2
                : bounds.x - 6;

        Rectangle edgeSensor = new Rectangle(
                sensorX,
                bounds.y - 8,
                6,
                12
        );

        return !collision.isGrounded(edgeSensor, bounds.y, ignorePlatforms);
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

    @Override
    public void respawn() {
        super.respawn();
        updateBounds();

        patrolOrigin = position.x;
        patrolDirection = 1;
        direction = 1;

        velocity.x = 0;
        velocity.y = 0;

        edgeWaitTimer = EDGE_WAIT_DURATION;
        spawnDelayTimer = 1.0f;

        hasJumped = false;
        hasEdgeJumped = false;
        hasLandedAfterSpawn = false;
        isCrossingGap = false;

        airJumpCheckTimer = 0f;
        jumpDecisionCooldown = 0f;
        jump_counter = 0;

        updateBounds();
    }
}