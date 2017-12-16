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

/* -------------------------------------------------------------------
	Initialise game resources for usage in MainMenuScreen & GameScreen
	------------------------------------------------------------------
 */

public class Survive extends Game {

	// Define game dimensions
	public static final int GAME_WIDTH = 854;
	public static final int GAME_HEIGHT = 480;

	// Init storage for game resources
	static final Array<Texture> GAME_COLOR = new Array<Texture>();
	static final Array<BitmapFont> GAME_FONT = new Array<BitmapFont>();
	private static final Array<String> GAME_COLOR_STRING = new Array<String>();
	private static final Array<String> GAME_FONT_STRING = new Array<String>();

	// Call once during initialization
	static {
		GAME_COLOR_STRING.add("#263238");
		GAME_COLOR_STRING.add("#1A2226");

		GAME_FONT_STRING.add("fonts/bitmap_21.fnt");
		GAME_FONT_STRING.add("fonts/bitmap_28.fnt");
		GAME_FONT_STRING.add("fonts/bitmap_35.fnt");
	}

	Viewport viewport;
	SpriteBatch sprite_batch;
	Sprite player;
	Cursor cursor;

	@Override
	public void create() {

		// Add game colour backgrounds to Array
		for (String color:GAME_COLOR_STRING) {

			Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			pixmap.setColor(Color.valueOf(color));
			pixmap.drawPixel(0, 0);
			GAME_COLOR.add(new Texture(pixmap));
		}

		// Add game font(s) to Array
		for (String font:GAME_FONT_STRING) {

			BitmapFont bitmap_font = new BitmapFont(Gdx.files.internal(font));
			bitmap_font.setColor(1, 1, 1, 1);
			GAME_FONT.add(bitmap_font);
		}

		// Initialise orthographic camera
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);

		// Initialise SpriteBatch, setTop projection camera
		sprite_batch = new SpriteBatch();
		sprite_batch.setProjectionMatrix(camera.combined);

		// Initialise Player Sprite
		player = new Sprite(new Texture("player.bmp"));

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
	}
}