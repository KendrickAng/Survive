package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.GAME_HEIGHT;
import static com.survive.game.Survive.GAME_WIDTH;

public class GameScreen implements Screen {

	private static final int PLAYER_ACCELERATION = 10;
	private static final int PLAYER_MAX_SPEED = 30;
	private static final int GAME_DOCK_PADDING = 10;
	private static final int DOCK_HEIGHT = 14 + GAME_DOCK_PADDING * 2;

	static final int MAP_HEIGHT = GAME_HEIGHT - DOCK_HEIGHT;
	static final int MAP_WIDTH = GAME_WIDTH;

	private Viewport viewport;
	private Vector2 cursor_position;
	private Vector2 player_position;
	private SpriteBatch sprite_batch;
	private BitmapFont bitmap_font;
	private Sprite game_background;
	private Sprite game_dock;
	private Sprite cursor;
	private Sprite player;

	private float player_rotation;
	private float offset_x;
	private float offset_y;
	private int player_score = 12345;

	Enemy test;

	private PowerUps power_ups;

	// For collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();

	GameScreen(Survive game) {

		viewport = game.viewport;
		cursor_position = game.cursor_position;
		sprite_batch = game.sprite_batch;
		bitmap_font = game.bitmap_font;
		cursor = game.cursor;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#263238"));
        pixmap.drawPixel(0, 0);

		game_background = new Sprite(new Texture(pixmap));
		game_background.setSize(MAP_WIDTH, MAP_HEIGHT);

		pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.valueOf("#1A2226"));
		pixmap.drawPixel(0, 0);
		game_dock = new Sprite(new Texture(pixmap));
		game_dock.setSize(GAME_WIDTH, DOCK_HEIGHT);
		game_dock.setPosition(0, MAP_HEIGHT);

		pixmap.dispose();

		// Init player sprite
		player = new Sprite(new Texture("player.bmp"));
		player.setOrigin(player.getWidth()/2, player.getHeight()/2);
		player_position = new Vector2(MAP_WIDTH/2, MAP_HEIGHT/2);

		viewport.apply();
		bitmap_font.getData();
		power_ups = new PowerUps();

		// Don't restrict cursor to screen boundaries
		Gdx.input.setCursorCatched(true);

		test = new Enemy(100, 100);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

		float player_speed;

		// Keep tracking cursor position, transform screen to world coordinates
		cursor_position.set(Gdx.input.getX(), Gdx.input.getY());
		viewport.unproject(cursor_position);
		cursor.setPosition(cursor_position.x - cursor.getWidth()/2, cursor_position.y - cursor.getHeight()/2);

		// Set player sprite positions
		player.setPosition(player_position.x - player.getWidth()/2, player_position.y - player.getHeight()/2);
		player.setRotation(player_rotation);

		// For Android phones (tilting sensor)
		// TODO: Use RotationVector Sensor
		if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {

			offset_x -= Math.toDegrees(Gdx.input.getGyroscopeX())/10;
			offset_y += Math.toDegrees(Gdx.input.getGyroscopeY())/10;

		} else {

			// Find cursor-player distance and angle from x-axis
			offset_x = player_position.x - cursor_position.x;
			offset_y = cursor_position.y - player_position.y;
		}

		float offset_distance = (float) Math.sqrt(Math.pow(offset_x, 2) + Math.pow(offset_y, 2));
		player_rotation = (float) Math.toDegrees(Math.atan2(offset_x, offset_y));

		// Determine current speed, varies on cursor-player distance
		if (offset_distance > player.getHeight()/2)
			player_speed = offset_distance * PLAYER_ACCELERATION * delta;

		else
			player_speed = 0;

		if (player_speed > PLAYER_MAX_SPEED)
			player_speed = PLAYER_MAX_SPEED;

		// Add displacement moved in one frame (x and y axis)
		player_position.x -= Math.sin(Math.toRadians(player_rotation))* player_speed;
		player_position.y += Math.cos(Math.toRadians(player_rotation))* player_speed;

		// Implement screen boundaries
		if (player_position.x < player.getHeight()/2)
			player_position.x = player.getHeight()/2;

		if (player_position.x > MAP_WIDTH - player.getHeight()/2)
			player_position.x = MAP_WIDTH - player.getHeight()/2;

		if (player_position.y < player.getHeight()/2)
			player_position.y = player.getHeight()/2;

		if (player_position.y > MAP_HEIGHT - player.getHeight()/2)
			player_position.y = MAP_HEIGHT - player.getHeight()/2;

		// Update
		power_ups.update(delta);
		test.update(delta, player_position);
		testCollisions();
		// TODO: EnemyPattern class

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render everything
		sprite_batch.begin();
		sprite_batch.disableBlending();
		game_background.draw(sprite_batch);
		game_dock.draw(sprite_batch);
		sprite_batch.enableBlending();
		power_ups.render(sprite_batch);
		test.render(sprite_batch);
		player.draw(sprite_batch);
		bitmap_font.draw(sprite_batch, "SCORE: " + String.valueOf(player_score), GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);
		bitmap_font.draw(sprite_batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), GAME_DOCK_PADDING + 300, GAME_HEIGHT - GAME_DOCK_PADDING);
		cursor.draw(sprite_batch);
		sprite_batch.end();
	}

	private void onCollisionPlayerWithItem(PowerUp power_up) {

		power_up.setCollected(true);
		Gdx.app.log("Item", "Item collected!");
	};

	private void testCollisions() {

		// Set hitboxes
		r1.set(player_position.x - player.getWidth()/2,
				player_position.y - player.getHeight()/2,
				player.getWidth(),
				player.getHeight());

		// Test collisions Player <--> Item1
		for (PowerUp item1 : power_ups.getPowerUps()) {

			if(item1.getCollected()) continue;

			r2.set(item1.getX() - item1.getSprite().getWidth()/2,
					item1.getY() - item1.getSprite().getHeight()/2,
					item1.getSprite().getWidth(),
					item1.getSprite().getHeight());

			if(!r1.overlaps(r2)) continue;

			onCollisionPlayerWithItem(item1);
			break;
		}
	}

	@Override
	public void resize(int width, int height) {

		viewport.update(width, height);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}