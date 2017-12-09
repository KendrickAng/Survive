package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.GAME_HEIGHT;
import static com.survive.game.Survive.GAME_WIDTH;

public class GameScreen implements Screen, ContactListener{

	private Viewport viewport;
	private Vector2 cursor_position;
	private SpriteBatch sprite_batch;
	private BitmapFont bitmap_font;
	private Sprite game_background;
	private Sprite cursor;
	private Texture player_texture;
	private Sprite player;

	private int player_acceleration;
	private int player_max_speed;

	private float player_x;
	private float player_y;
	private float player_rotation;
	private float offset_x;
	private float offset_y;

	private PowerUps power_ups;

	private World world;

	GameScreen(Survive game) {

		viewport = game.viewport;
		cursor_position = game.cursor_position;
		sprite_batch = game.sprite_batch;
		bitmap_font = game.bitmap_font;
		cursor = game.cursor;

		game_background = new Sprite(new Texture("game_background.png"));
		game_background.setSize(GAME_WIDTH, GAME_HEIGHT);

		// Init player sprite
		player_texture  = new Texture("player.png");
		player_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		player = new Sprite(player_texture);
		player.setOrigin(player.getWidth()/2, player.getHeight()/2);

		player_acceleration = game.player_acceleration;
		player_max_speed = game.player_max_speed;

		// Init power ups
		viewport.apply();
		bitmap_font.getData();
		power_ups = new PowerUps();

		world = new World(new Vector2(0,0), true);

		// Don't restrict cursor to screen boundaries
		// Gdx.input.setCursorCatched(true);
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
		player.setPosition(player_x - player.getWidth()/2, player_y - player.getHeight()/2);
		player.setRotation(player_rotation);

		// For Android phones (tilting sensor)
		if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {

			offset_x += Math.toDegrees(Gdx.input.getGyroscopeX())/10;
			offset_y += Math.toDegrees(Gdx.input.getGyroscopeY())/10;

		} else {

			// Find cursor-player distance and angle from x-axis
			offset_x = player_x - cursor_position.x;
			offset_y = cursor_position.y - player_y;
		}

		float offset_distance = (float) Math.sqrt(Math.pow(offset_x, 2) + Math.pow(offset_y, 2));
		player_rotation = (float) Math.toDegrees(Math.atan2(offset_x, offset_y));

		// Determine current speed, varies on cursor-player distance
		if (offset_distance > player.getHeight()/2)
			player_speed = offset_distance *player_acceleration* delta;

		else
			player_speed = 0;

		if (player_speed > player_max_speed)
			player_speed = player_max_speed;

		// Add displacement moved in one frame (x and y axis)
		player_x -= Math.sin(Math.toRadians(player_rotation))* player_speed;
		player_y += Math.cos(Math.toRadians(player_rotation))* player_speed;

		// Implement screen boundaries
		if (player_x < player.getHeight()/2)
			player_x = player.getHeight()/2;

		if (player_x > GAME_WIDTH - player.getHeight()/2)
			player_x = GAME_WIDTH - player.getHeight()/2;

		if (player_y < player.getHeight()/2)
			player_y = player.getHeight()/2;

		if (player_y > GAME_HEIGHT - player.getHeight()/2)
			player_y = GAME_HEIGHT - player.getHeight()/2;

		// Spawn powerups
		power_ups.update(delta);

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render everything
		sprite_batch.begin();
		game_background.draw(sprite_batch);
		power_ups.render(sprite_batch);
		player.draw(sprite_batch);
		bitmap_font.draw(sprite_batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), 50, 50);
		cursor.draw(sprite_batch);
		sprite_batch.end();
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
	public void dispose() {

		player_texture.dispose();
	}

	@Override
	public void beginContact(Contact contact) {

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
