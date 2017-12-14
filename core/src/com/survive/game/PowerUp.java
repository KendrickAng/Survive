package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private float animation_time;
    private Sprite sprite;
    private Animation<TextureRegion> animation;

	boolean triggered;
	Circle hit_box;

	PowerUp(Sprite sprite, Animation<TextureRegion> animation) {

        // Spawn within map boundaries
        x = SPAWN_PADDING + (float) Math.random() * (MAP_WIDTH - SPAWN_PADDING * 2);
        y = SPAWN_PADDING + (float) Math.random() * (MAP_HEIGHT - SPAWN_PADDING * 2);
		radius = sprite.getWidth()/2;

        // Init Speed & Rotation
        double theta = Math.random() * 2 * Math.PI;
        double speed = Math.random() * SPEED;
        rotation = theta;
        rotation_speed = Math.random() * ROTATION_SPEED;
        x_speed = Math.sin(theta) * speed;
        y_speed = Math.cos(theta) * speed;

        animation_time = 0;
        triggered = false;
		hit_box = new Circle(0, 0, 0);

        this.sprite = sprite;
        this.animation = animation;
    }

    void update(float delta) {

    	if (triggered) {

			animation_time += delta;
			radius = animation.getKeyFrame(animation_time, false).getRegionWidth() / 2;

		} else {

			x -= x_speed * delta;
			y += y_speed * delta;
			rotation += rotation_speed * delta;

			// Bounce on map boundaries
			if (x < radius || x > MAP_WIDTH - radius)
				x_speed = -x_speed;

			else if (y < radius || y > MAP_HEIGHT - radius)
				y_speed = -y_speed;
		}

		hit_box.set(x, y, radius);
    }

	void playerHitTest(Player player) {

		if (!triggered && player.hit_box.intersectCircle(hit_box))
			triggered = true;
	}

    void render(SpriteBatch batch)  {

    	if (triggered) {

    		batch.draw(animation.getKeyFrame(animation_time, false), x - radius, y - radius);

		} else {

    		sprite.setRotation((float) Math.toDegrees(rotation));
			sprite.setPosition(x - radius, y - radius);
			sprite.draw(batch);
    	}
    }

    void dispose(Array<PowerUp> power_up_array) {

		if (animation_time > animation.getAnimationDuration())
			power_up_array.removeValue(this, true);
	}
}
