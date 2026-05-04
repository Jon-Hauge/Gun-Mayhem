package inf112.mayhem.view.screens.activeGameView.animations;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import inf112.mayhem.view.screens.activeGameView.CharacterRig;

public class DeathAnimation extends AbstractAnimation {
    private final float ANIMATION_DURATION = 1.5f;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Override
    protected void animate(CharacterRig character, float x, float y, float elapsedTime, Matrix4 projection) {
        if (elapsedTime > ANIMATION_DURATION) {
            reset();
        }
        float duration = 0.5f;
        float progress = Math.min(elapsedTime / duration, 1f);
        float easeOut = 1 - (float) Math.pow(1 - progress, 2);

        float centerX = x;
        float centerY = y + character.body.getHeight() * 0.5f;

        shapeRenderer.setProjectionMatrix(projection);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float alpha = 1f - progress;
        shapeRenderer.setColor(255f, 127f, 0f, alpha);

        float baseRadius = 40f;
        float maxRadius = 80f;
        float radius = baseRadius + (maxRadius - baseRadius) * easeOut;
        shapeRenderer.circle(centerX, centerY, radius);

        int particles = 8;
        float particleDistance = 40f * easeOut;

        for (int i = 0; i < particles; i++) {
            float angle = (float) (i * (2 * Math.PI / particles));

            float px = centerX + (float) Math.cos(angle) * particleDistance;
            float py = centerY + (float) Math.sin(angle) * particleDistance;

            shapeRenderer.circle(px, py, 4f * (1 - progress));
        }
        shapeRenderer.end();
    }
}
