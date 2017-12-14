package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class PowerUp {

    private static final float SPEED = 10;
    private static final float ROTATION_SPEED = 1;
    private static final float SPAWN_PADDING = 50;

    private float x;
    private float y;
    private float radius;
    private double rotation;
    private double rotation_speed;
    private double x_speed;
    private double y_speed;
    private Sprite sprite;

    PowerUp(Sprite sprite) {

        // Spawn within map boundaries
        x = SPAWN_PADDING + (float) Math.random() * (MAP_WIDTH - SPAWN_PADDING * 2);
        y = SPAWN_PADDING + (float) Math.random() * (MAP_HEIGHT - SPAWN_PADDING * 2);

        // Init Speed & Rotation
        double theta = Math.random() * 2 * Math.PI;
        double speed = Math.random() * SPEED;

        rotation = theta;
        rotation_speed = Math.random() * ROTATION_SPEED;

        x_speed = Math.sin(theta) * speed;
        y_speed = Math.cos(theta) * speed;

        this.sprite = sprite;
        this.radius = sprite.getHeight()/2;
    }

    void update(float delta) {

        x -= x_speed * delta;
        y += y_speed * delta;
        rotation += rotation_speed * delta;

        // Bounce on map boundaries
        if (x < radius || x > MAP_WIDTH - radius)
            x_speed = -x_speed;

        else if (y < radius || y > MAP_HEIGHT - radius)
            y_speed = -y_speed;
    }

	void playerHitTest(Player player, Array<PowerUp> power_up_array) {

		if (player.hit_box.get(0).intersectCircle(x, y, radius))
			power_up_array.removeValue(this, true);
	}

    void render(SpriteBatch batch)  {

		sprite.setRotation((float) Math.toDegrees(rotation));
		sprite.setPosition(x - radius, y - radius);
        sprite.draw(batch);
    }
}
