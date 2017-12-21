package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;
import static com.survive.game.Screen.GAME_OVER_SCREEN;
import static com.survive.game.Screen.getDelta;
import static com.survive.game.Survive.getCursor;
import static com.survive.game.Survive.getSpriteBatch;

public class Player {

	private static final int GYROSCOPE_SENSITIVITY = 4;
	private static final int SPEED_SENSITIVITY = 10;
	private static final int MAX_SPEED = 500;
	private static final int KILLS_MULTIPLIER = 10;

	private float x;
	private float y;
	private float height;
	private float width;
	private double rotation;
	private float offset_x = 0;
	private float offset_y = 0;
	private int score;
	private int kills;
	private float time_alive;
	private Sprite sprite;
	private boolean dead;
	private Line hit_box;

	Player(Sprite sprite) {

		x = MAP_WIDTH/2;
		y = MAP_HEIGHT/2;
		height = sprite.getHeight();
		width = sprite.getWidth();
		rotation = 0;
		score = 0;
		kills = 0;
		time_alive = 0;
		dead = false;

		// Add hit box
		hit_box = new Line(0, 0, 0, 0);

		sprite.setOrigin(width/2, height/2);
		this.sprite = sprite;
	}

	void update() {

		time_alive += getDelta();
		score = kills * KILLS_MULTIPLIER + (int) time_alive;

		if (dead)
			Screen.setScreen(GAME_OVER_SCREEN);

		// For Android phones (gyroscope sensor)
		if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {

			offset_x -= Gdx.input.getGyroscopeX() * GYROSCOPE_SENSITIVITY;
			offset_y += Gdx.input.getGyroscopeY() * GYROSCOPE_SENSITIVITY;

		} else {

			// Find cursor-player distance and angle from x-axis
			Cursor cursor = getCursor();
			offset_x = x - cursor.position.x;
			offset_y = cursor.position.y - y;
		}

		// Re-calculate arc-tangent from north, moving counter-clockwise
		rotation = Math.atan2(offset_x, offset_y);
		double offset_distance = Math.sqrt(Math.pow(offset_x, 2) + Math.pow(offset_y, 2));

		if (offset_distance > height/2) {

			// Determine current speed, varies on cursor-player distance
			double speed = (offset_distance - height/2) * SPEED_SENSITIVITY;

			if (speed > MAX_SPEED)
				speed = MAX_SPEED;

			// Add displacement moved in one frame (x and y axis)
			x -= Math.sin(rotation) * speed * getDelta();
			y += Math.cos(rotation) * speed * getDelta();
		}

		// Update player hit_box
		float x1 = x - (float) Math.sin(rotation) * height/2;
		float y1 = y + (float) Math.cos(rotation) * height/2;
		float x2 = x + (float) Math.sin(rotation) * height/4;
		float y2 = y - (float) Math.cos(rotation) * height/4;
		hit_box.set(x1, y1, x2, y2);

		// Player boundaries based on hit_box
		if (x < Math.max(x - x1, x - x2))
			x = Math.max(x - x1, x - x2);

		if (x > MAP_WIDTH - Math.max(x1 - x, x2 - x))
			x = MAP_WIDTH - Math.max(x1 - x, x2 - x);

		if (y < Math.max(y - y1, y - y2))
			y = Math.max(y - y1, y - y2);

		if (y > MAP_HEIGHT - Math.max(y1 - y, y2 - y))
			y = MAP_HEIGHT - Math.max(y1 - y, y2 - y);
	}

	void render() {

		// Set player sprite positions
		sprite.setPosition(x - width /2, y - height /2);
		sprite.setRotation((float) Math.toDegrees(rotation));
		sprite.draw(getSpriteBatch());
	}

	public void addKills() { kills ++; }
	public int getScore() { return score; }
	public int getKills() { return kills; }
	public float getTimeAlive() { return time_alive; }
	public float getX() { return x; }
	public float getY() { return y; }
	public void setDead() { dead = true; }
	public Line getHitBox() { return hit_box; }
}
