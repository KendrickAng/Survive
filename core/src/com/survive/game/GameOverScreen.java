package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.*;

public class GameOverScreen implements Screen {

	private static final int SCREEN_PADDING = 50;

	private Survive game;
	private Viewport viewport;
	private SpriteBatch sprite_batch;
	private Cursor cursor;
	private Text title;
	private TextList information;
	private TextList options;

	GameOverScreen(Survive game, GameScreen screen) {

		this.game = game;
		this.viewport = game.viewport;
		this.sprite_batch = game.sprite_batch;
		this.cursor = game.cursor;

		title = new Text(GAME_FONT.get(2), "GAME OVER");
		title.setOrigin(0, SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING);

		Text kills = new Text(GAME_FONT.get(0), "KILLS: " + String.valueOf(screen.player.kills));
		kills.setPadding(5);
		Text time_alive = new Text(GAME_FONT.get(0), "TIME: " + String.valueOf((int) screen.player.time_alive));
		time_alive.setPadding(5);
		Text score = new Text(GAME_FONT.get(0), "SCORE: " + String.valueOf(screen.player.score));
		score.setPadding(5);

		information = new TextList(kills, time_alive, score);
		information.setOrigin(0, SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING * 2 - title.height);

		Text restart = new Text(GAME_FONT.get(1), "RESTART");
		restart.setPadding(15);
		restart.button(1);
		Text back = new Text(GAME_FONT.get(1), "BACK");
		back.setPadding(15);
		back.button(2);

		options = new TextList(restart, back);
		options.setOrigin(3,SCREEN_PADDING, SCREEN_PADDING);
		options.keyboard();
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

		// Update
		cursor.update(game);
		options.update(game, delta);

		// Render
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sprite_batch.begin();
		sprite_batch.draw(GAME_COLOR.get(0), 0, 0, GAME_WIDTH, GAME_HEIGHT);
		title.render(sprite_batch);
		information.render(sprite_batch);
		options.render(sprite_batch);
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
