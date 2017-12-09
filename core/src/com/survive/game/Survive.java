package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Survive extends Game {

	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;

	Viewport viewport;
	SpriteBatch sprite_batch;
	BitmapFont bitmap_font;
	Vector2 cursor_position;
	Sprite cursor;

	int player_acceleration = 10;
	int player_max_speed = 30;

	private Texture cursor_texture;

	@Override
	public void create () {

		// Initialise orthographic camera
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);

		// Initialise SpriteBatch, set projection camera
		sprite_batch = new SpriteBatch();
		sprite_batch.setProjectionMatrix(camera.combined);

		// Initialise remaining declared objects
		bitmap_font = new BitmapFont();
		bitmap_font.setColor(0, 0, 0, 1);

		// Init cursor coordinates
		cursor_position = new Vector2();

		// Init cursor sprite
		cursor_texture  = new Texture("cursor.png");
		cursor_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		cursor = new Sprite(cursor_texture);

		// Set Screen to MainMenuScreen
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () { super.render(); }

	@Override
	public void dispose () {

		sprite_batch.dispose();
		bitmap_font.dispose();
		cursor_texture.dispose();
	}
}