package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Survive extends Game {

	public static final int GAME_WIDTH = 854;
	public static final int GAME_HEIGHT = 480;

	Viewport viewport;
	SpriteBatch sprite_batch;
	BitmapFont bitmap_font;
	Vector2 cursor_position;
	Sprite cursor;

	@Override
	public void create() {

		// Initialise orthographic camera
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);

		// Initialise SpriteBatch, set projection camera
		sprite_batch = new SpriteBatch();
		sprite_batch.setProjectionMatrix(camera.combined);

		// Initialise remaining declared objects
		bitmap_font = new BitmapFont(Gdx.files.internal("bitmap-21.fnt"));
		bitmap_font.setColor(1, 1, 1, 1);

		// Initialise cursor
		cursor_position = new Vector2();
		cursor = new Sprite(new Texture("cursor.bmp"));

		// Set Screen to MainMenuScreen
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() { super.render(); }

	@Override
	public void dispose() {

		sprite_batch.dispose();
		bitmap_font.dispose();
	}
}