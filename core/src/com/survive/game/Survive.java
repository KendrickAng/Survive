package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Survive extends Game {

	public static final int GAME_WIDTH = 854;
	public static final int GAME_HEIGHT = 480;

	static final Array<Texture> GAME_COLOR = new Array<Texture>();
	private static final Array<String> GAME_COLOR_STRING = new Array<String>();
	static {
		GAME_COLOR_STRING.add("#263238");
		GAME_COLOR_STRING.add("#1A2226");
	}

	Viewport viewport;
	SpriteBatch sprite_batch;
	BitmapFont bitmap_font;
	Cursor cursor;

	@Override
	public void create() {

		for (String color: GAME_COLOR_STRING) {

			Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			pixmap.setColor(Color.valueOf(color));
			pixmap.drawPixel(0, 0);
			GAME_COLOR.add(new Texture(pixmap));
		}

		// Initialise orthographic camera
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);

		// Initialise SpriteBatch, set projection camera
		sprite_batch = new SpriteBatch();
		sprite_batch.setProjectionMatrix(camera.combined);

		// Initialise Font
		bitmap_font = new BitmapFont(Gdx.files.internal("bitmap_21.fnt"));
		bitmap_font.setColor(1, 1, 1, 1);

		// Initialise Cursor
		cursor = new Cursor(new Sprite(new Texture("cursor.bmp")));

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