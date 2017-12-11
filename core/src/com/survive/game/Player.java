package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

class Player {

	private static final int GYROSCOPE_SENSITIVITY = 5;
	private static final int SPEED_SENSITIVITY = 10;
	private static final int MAX_SPEED = 500;

	float x;
	float y;
	float height;
	float width;
	private double rotation;
	Array<Line> hitbox;

	private float offset_x = 0;
	private float offset_y = 0;

	private Sprite sprite;

	Player(Sprite sprite) {

		x = MAP_WIDTH/2;
		y = MAP_HEIGHT/2;
		height = sprite.getHeight();
		width = sprite.getWidth();
		rotation = 0;
		hitbox = new Array<Line>();
		hitbox.add(new Line(0, 0, 0 ,0));

		sprite.setOrigin(width/2, height/2);
		this.sprite = sprite;
	}

	void updateOffset(Vector2 cursor_position) {

		// For Android phones (tilting sensor)
		// TODO: Use RotationVector Sensor
		if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {

			offset_x -= Gdx.input.getGyroscopeX() * GYROSCOPE_SENSITIVITY;
			offset_y += Gdx.input.getGyroscopeY() * GYROSCOPE_SENSITIVITY;

		} else {

			// Find cursor-player distance and angle from x-axis
			offset_x = x - cursor_position.x;
			offset_y = cursor_position.y - y;
		}
	}

	void update(float delta) {

		rotation = Math.atan2(offset_x, offset_y);
		double offset_distance = Math.sqrt(Math.pow(offset_x, 2) + Math.pow(offset_y, 2));

		if (offset_distance > height/2) {

			// Determine current speed, varies on cursor-player distance
			double speed = (offset_distance - height/2) * SPEED_SENSITIVITY;

			if (speed > MAX_SPEED)
				speed = MAX_SPEED;

			// Add displacement moved in one frame (x and y axis)
			x -= Math.sin(rotation) * speed * delta;
			y += Math.cos(rotation) * speed * delta;
		}

		// Player boundaries
		if (x < height/2)
			x = height/2;

		if (x > MAP_WIDTH - height/2)
			x = MAP_WIDTH - height/2;

		if (y < height/2)
			y = height/2;

		if (y > MAP_HEIGHT - height/2)
			y = MAP_HEIGHT - height/2;

		// Update player hitbox
		float x1 = x - (float) Math.sin(rotation) * height/2;
		float y1 = y + (float) Math.cos(rotation) * height/2;
		float x2 = x + (float) Math.sin(rotation) * height/4;
		float y2 = y - (float) Math.cos(rotation) * height/4;
		hitbox.get(0).set(x1, y1, x2, y2);

		// Set player sprite positions
		sprite.setPosition(x - width /2, y - height /2);
		sprite.setRotation((float) Math.toDegrees(rotation));
	}

	void render(SpriteBatch batch) {

		sprite.draw(batch);
	}
}
