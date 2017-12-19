package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.Survive.getSpriteBatch;
import static com.survive.game.Survive.getViewport;

public abstract class Screen implements com.badlogic.gdx.Screen {

	static final int MAIN_MENU_SCREEN = 0;
	static final int SETTINGS_SCREEN = 1;
	static final int GAME_SCREEN = 2;
	static final int GAME_OVER_SCREEN = 3;

	private static Game game;
	private static float delta;
	private static SpriteBatch sprite_batch;

	Screen(Game game) {

		Screen.game = game;
		sprite_batch = getSpriteBatch();
	}

	public abstract void show();

	@Override
	public void render(float delta) {

		Screen.delta = delta;
		screenUpdate();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sprite_batch.begin();
		screenRender();
		sprite_batch.end();
	}

	@Override
	public void resize(int width, int height) { getViewport().update(width, height); }

	public abstract void pause();
	public abstract void resume();
	public abstract void hide();
	public abstract void dispose();
	public abstract void screenUpdate();
	public abstract void screenRender();

	public static float getDelta() { return delta; }
	static void setScreen(int choice) {

		Screen screen = null;

		switch (choice) {

			case MAIN_MENU_SCREEN:
				screen = new MainMenuScreen(game);
				break;

			case SETTINGS_SCREEN:
				screen = new SettingsScreen(game);
				break;

			case GAME_SCREEN:
				screen = new GameScreen(game);
				break;

			case GAME_OVER_SCREEN:
				screen = new GameOverScreen(game);
				break;
		}

		if (screen != null)
			game.setScreen(screen);
	}
}
