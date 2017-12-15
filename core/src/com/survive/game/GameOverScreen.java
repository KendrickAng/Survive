package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.GAME_COLOR;
import static com.survive.game.Survive.GAME_HEIGHT;
import static com.survive.game.Survive.GAME_WIDTH;

public class GameOverScreen implements Screen {

	private static final int SCREEN_PADDING = 50;
	private static final int FONT_HEIGHT = 14;
	private static final int TITLE_PADDING = 50;
	private static final int INFORMATION_PADDING = 10;
	private static final int TITLE_HEIGHT = TITLE_PADDING + FONT_HEIGHT;
	private static final int INFORMATION_HEIGHT = INFORMATION_PADDING + FONT_HEIGHT;

	private int kills;
	private int time_alive;
	private int score;
	private Survive game;
	private Viewport viewport;
	private SpriteBatch sprite_batch;
	private BitmapFont bitmap_font;
	private Cursor cursor;
	private Button restart;

	GameOverScreen(Survive game, GameScreen screen) {

		this.game = game;
		this.viewport = game.viewport;
		this.sprite_batch = game.sprite_batch;
		this.bitmap_font = game.bitmap_font;
		this.cursor = game.cursor;
		this.kills = screen.player.kills;
		this.time_alive = (int) screen.player.time_alive;
		this.score = screen.player.score;

		// TODO: Improve Button & Fonts
		restart = new Button(bitmap_font, "RESTART", SCREEN_PADDING, SCREEN_PADDING);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

		// Update
		cursor.update(game);
		restart.update(game);

		// Render
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sprite_batch.begin();
		sprite_batch.draw(GAME_COLOR.get(0), 0, 0, GAME_WIDTH, GAME_HEIGHT);
		bitmap_font.draw(sprite_batch, "GAME OVER", SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING);
		bitmap_font.draw(sprite_batch, "KILLS: " + String.valueOf(kills), SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING - TITLE_HEIGHT);
		bitmap_font.draw(sprite_batch, "TIME: " + String.valueOf(time_alive), SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING - TITLE_HEIGHT - INFORMATION_HEIGHT);
		bitmap_font.draw(sprite_batch, "SCORE: " + String.valueOf(score), SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING - TITLE_HEIGHT - INFORMATION_HEIGHT * 2);
		restart.render(sprite_batch);
		cursor.render(sprite_batch);
		sprite_batch.end();
	}

	@Override
	public void resize(int width, int height) { viewport.update(width, height); }

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}
