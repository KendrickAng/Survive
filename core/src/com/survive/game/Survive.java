package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Survive extends Game {

	int screen_width;
	int screen_height;

	OrthographicCamera camera;
	SpriteBatch sprite_batch;
	BitmapFont bitmap_font;
	Vector3 cursor_position;
	Texture cursor;
	Sprite player;

	int cursor_radius = 5;
	int player_width = 15;
	int player_height = 21;
	int player_acceleration = 10;
	int player_max_speed = 30;

	@Override
	public void create () {

		// Bind cursor within screen boundaries (THIS HIDES THE CURSOR IN MAIN MENU)
		//Gdx.input.setCursorCatched(true);

		// Get local screen dimensions (varies on machine)
		screen_width = Gdx.graphics.getWidth();
		screen_height = Gdx.graphics.getHeight();

		// Log DIP scaling factor
		Gdx.app.log("Density", String.valueOf(Gdx.graphics.getDensity()));

		// Initialise ortho camera (stage)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screen_width, screen_height);

		// Initialise SpriteBatch, set projection camera (actors)
		sprite_batch = new SpriteBatch();
		sprite_batch.setProjectionMatrix(camera.combined);

		// Initialise remaining declared objects
		bitmap_font = new BitmapFont();
		bitmap_font.setColor(0, 0, 0, 1);

		// Init cursor coordinates
		cursor_position = new Vector3();

		// Load pixmap to draw player and cursor, send to GPU
		Pixmap pixmap = new Pixmap(cursor_radius * 2 + 1, cursor_radius * 2 + 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(0, 0, 0, 1);
		pixmap.drawCircle(cursor_radius, cursor_radius, cursor_radius);
		cursor = new Texture(pixmap);

		pixmap = new Pixmap(player_width, player_height, Pixmap.Format.RGBA8888);
		pixmap.setColor(0, 0, 0, 1);
		pixmap.fillTriangle(0, 0, (player_width - 1)/2, player_height - 1, player_width - 1, 0);
		player = new Sprite(new Texture(pixmap), player_width, player_height);

		pixmap.dispose();

		// Set clear color
		Gdx.gl.glClearColor(1, 1, 1, 0);

		// Set screen to main screen
		// this.setScreen(new MainMenuScreen(this));

		// Set screen to game screen
		// this.setScreen(new GameScreen(this));

		// Run MainMenuScreen2 from main Survive Game instead?
		this.setScreen(new MainMenuScreen2(this));
	}

	@Override
	public void render () { super.render(); }

	@Override
	public void dispose () {

		sprite_batch.dispose();
		bitmap_font.dispose();
		cursor.dispose();
	}
}