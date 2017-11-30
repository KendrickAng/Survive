package com.survive.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Survive extends ApplicationAdapter {

	public static int screen_width = 1920;
	public static int screen_height = 1080;

	OrthographicCamera camera;
	SpriteBatch sprite_batch;
	BitmapFont bitmap_font;

	Vector3 cursor_position;

	Pixmap pixmap;
	
	Texture cursor;
	Sprite player;

	int cursor_radius = 5;
	
	int player_width = 15;
	int player_height = 21;
	int player_acceleration = 10;
	int player_max_speed = 30;

	float player_x = screen_width/2;
	float player_y = screen_height/2;

	float player_speed;
	float player_rotation;

	float offset_x;
	float offset_y;
	float offset_distance;

	float game_time;

	@Override
	public void create () {

		// Paint background opaque black
		Gdx.gl.glClearColor(0, 0, 0, 1);

		// Bind cursor within screen boundaries
		Gdx.input.setCursorCatched(true);

		// Get local screen dimensions (varies on machine)
		screen_width = Gdx.graphics.getWidth();
		screen_height = Gdx.graphics.getHeight();

		// Get DIP scaling factor
		Gdx.app.log("Density", String.valueOf(Gdx.graphics.getDensity()));

		// Initialise ortho camera (stage)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screen_width, screen_height);

		// Initialise SpriteBatch, set projection camera (actors)
		sprite_batch = new SpriteBatch();
		sprite_batch.setProjectionMatrix(camera.combined);

		// Initialise remaining declared objects
		bitmap_font = new BitmapFont();
		cursor_position = new Vector3();

		// Load pixmap to draw player and cursor, send to GPU
		pixmap = new Pixmap(cursor_radius*2 + 1, cursor_radius*2 + 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.drawCircle(cursor_radius, cursor_radius, cursor_radius);
		cursor = new Texture(pixmap);

		pixmap = new Pixmap(player_width, player_height, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillTriangle(0, 0, (player_width - 1)/2, player_height - 1, player_width - 1, 0);
		player = new Sprite(new Texture(pixmap), player_width, player_height);

		pixmap.dispose();
	}

	@Override
	public void render () {

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Find time elapsed between 2 simultaneous frames
		game_time = Gdx.graphics.getDeltaTime();

		// Keep tracking cursor position, transform screen to world coords
		cursor_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(cursor_position);

		// Set player sprite positions
		player.setPosition(player_x - player_width/2, player_y - player_height/2);
		player.setRotation(player_rotation);

		// Re-draw player, cursor and FPS counter
		sprite_batch.begin();
		sprite_batch.draw(cursor, cursor_position.x - cursor_radius, cursor_position.y - cursor_radius);
		player.draw(sprite_batch);
		bitmap_font.draw(sprite_batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), 50, 50);
		sprite_batch.end();

		// For Android phones (tilting sensor)
		if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {
			offset_x += Math.toDegrees(Gdx.input.getGyroscopeX())/10;
			offset_y += Math.toDegrees(Gdx.input.getGyroscopeY())/10;

		} else {
			// Find cursor-player distance and angle from x-axis
			offset_x = cursor_position.x - player_x;
			offset_y = player_y - cursor_position.y;
		}

		offset_distance = (float) Math.sqrt(Math.pow(offset_x, 2) + Math.pow(offset_y, 2));
		player_rotation = (float) Math.toDegrees(Math.atan2(offset_x, offset_y));

		// Determine current speed, varies on cursor-player distance
		if (offset_distance > player_height/2)
			player_speed = offset_distance*player_acceleration*game_time;
		else
			player_speed = 0;

		if (player_speed > player_max_speed)
			player_speed = player_max_speed;

		// Add displacement moved in one frame (x and y axis)
		player_x += Math.sin(Math.toRadians(player_rotation))*player_speed;
		player_y -= Math.cos(Math.toRadians(player_rotation))*player_speed;

		// Implement screen boundaries
		if (player_x < player_height/2)
			player_x = player_height/2;

		if (player_x > screen_width - player_height/2)
			player_x = screen_width - player_height/2;

		if (player_y < player_height/2)
			player_y = player_height/2;

		if (player_y > screen_height - player_height/2)
			player_y = screen_height - player_height/2;
	}

	@Override
	public void dispose () {

		sprite_batch.dispose();
		bitmap_font.dispose();
		cursor.dispose();
	}
}