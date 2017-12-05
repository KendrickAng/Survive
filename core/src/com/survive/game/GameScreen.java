package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {

	private int screen_width;
	private int screen_height;

	private OrthographicCamera camera;
	private Vector3 cursor_position;
	private SpriteBatch sprite_batch;
	private BitmapFont bitmap_font;
	private Texture cursor;
	private Sprite player;

	private int cursor_radius;
	private int player_width;
	private int player_height;
	private int player_acceleration;
	private int player_max_speed;

	private float player_x;
	private float player_y;
	private float player_rotation;
	private float offset_x;
	private float offset_y;

	GameScreen(Survive game) {

		screen_width = game.screen_width;
		screen_height = game.screen_height;

		camera = game.camera;
		cursor_position = game.cursor_position;
		sprite_batch = game.sprite_batch;
		bitmap_font = game.bitmap_font;
		player = game.player;
		cursor = game.cursor;

		cursor_radius = game.cursor_radius;
		player_width = game.player_width;
		player_height = game.player_height;
		player_acceleration = game.player_acceleration;
		player_max_speed = game.player_max_speed;
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

		float player_speed;
		// Find time elapsed between 2 simultaneous frames
		float game_time = Gdx.graphics.getDeltaTime();

		// Keep tracking cursor position, transform screen to world coordinates
		cursor_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(cursor_position);

		// Set player sprite positions
		player.setPosition(player_x - player_width/2, player_y - player_height/2);
		player.setRotation(player_rotation);


		// For Android phones (tilting sensor)
		if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {

			offset_x += Math.toDegrees(Gdx.input.getGyroscopeX())/10;
			offset_y += Math.toDegrees(Gdx.input.getGyroscopeY())/10;

		} else {

			// Find cursor-player distance and angle from x-axis
			offset_x = cursor_position.x - player_x;
			offset_y = player_y - cursor_position.y;
		}

		float offset_distance = (float) Math.sqrt(Math.pow(offset_x, 2) + Math.pow(offset_y, 2));
		player_rotation = (float) Math.toDegrees(Math.atan2(offset_x, offset_y));

		// Determine current speed, varies on cursor-player distance
		if (offset_distance > player_height/2)
			player_speed = offset_distance *player_acceleration* game_time;

		else
			player_speed = 0;

		if (player_speed > player_max_speed)
			player_speed = player_max_speed;

		// Add displacement moved in one frame (x and y axis)
		player_x += Math.sin(Math.toRadians(player_rotation))* player_speed;
		player_y -= Math.cos(Math.toRadians(player_rotation))* player_speed;

		// Implement screen boundaries
		if (player_x < player_height/2)
			player_x = player_height/2;

		if (player_x > screen_width - player_height/2)
			player_x = screen_width - player_height/2;

		if (player_y < player_height/2)
			player_y = player_height/2;

		if (player_y > screen_height - player_height/2)
			player_y = screen_height - player_height/2;

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Re-draw player, cursor and FPS counter
		sprite_batch.begin();
		player.draw(sprite_batch);
		bitmap_font.draw(sprite_batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), 50, 50);
		sprite_batch.draw(cursor, cursor_position.x - cursor_radius, cursor_position.y - cursor_radius);
		sprite_batch.end();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}
