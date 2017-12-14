package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.GAME_COLOR;
import static com.survive.game.Survive.GAME_HEIGHT;
import static com.survive.game.Survive.GAME_WIDTH;

public class GameScreen implements Screen {

	private static final int GAME_DOCK_PADDING = 10;
	private static final int DOCK_HEIGHT = 14 + GAME_DOCK_PADDING * 2;
	private static final int SCORE = 1234567890;

	static final int MAP_HEIGHT = GAME_HEIGHT - DOCK_HEIGHT;
	static final int MAP_WIDTH = GAME_WIDTH;

	static final Array<Integer> ENEMY_PATTERN_COUNT = new Array<Integer>();
	static final Array<Float> ENEMY_PATTERN_SPAWN_INTERVAL = new Array<Float>();
	static final Array<Integer> ENEMY_PATTERN_SPEED = new Array<Integer>();

	static final Array<Integer> POWER_UP_TYPE_COUNT = new Array<Integer>();
	static final Array<Integer> POWER_UP_TYPE_MIN_SPAWN_INTERVAL = new Array<Integer>();
	static final Array<Integer> POWER_UP_TYPE_MAX_SPAWN_INTERVAL = new Array<Integer>();

	static {
		ENEMY_PATTERN_COUNT.add(0);
		ENEMY_PATTERN_COUNT.add(5);
		ENEMY_PATTERN_COUNT.add(8);
		ENEMY_PATTERN_COUNT.add(8);

		ENEMY_PATTERN_SPAWN_INTERVAL.add(0f);
		ENEMY_PATTERN_SPAWN_INTERVAL.add(0.2f);
		ENEMY_PATTERN_SPAWN_INTERVAL.add(0.1f);
		ENEMY_PATTERN_SPAWN_INTERVAL.add(0.1f);

		ENEMY_PATTERN_SPEED.add(0);
		ENEMY_PATTERN_SPEED.add(0);
		ENEMY_PATTERN_SPEED.add(50);
		ENEMY_PATTERN_SPEED.add(50);

		POWER_UP_TYPE_COUNT.add(3);
		POWER_UP_TYPE_COUNT.add(1);

		POWER_UP_TYPE_MIN_SPAWN_INTERVAL.add(3);
		POWER_UP_TYPE_MIN_SPAWN_INTERVAL.add(7);

		POWER_UP_TYPE_MAX_SPAWN_INTERVAL.add(10);
		POWER_UP_TYPE_MAX_SPAWN_INTERVAL.add(20);
	}

	private Viewport viewport;
	private Vector2 cursor_position;
	private SpriteBatch sprite_batch;
	private BitmapFont bitmap_font;
	private Sprite cursor;
	private Player player;
	private Array<EnemyPattern> pattern_array;
	private Array<PowerUpType> power_up_types;

	GameScreen(Survive game) {

		viewport = game.viewport;
		cursor_position = game.cursor_position;
		sprite_batch = game.sprite_batch;
		bitmap_font = game.bitmap_font;
		cursor = game.cursor;

		// Init Player
		player = new Player(new Sprite(new Texture("player.bmp")));

		// Init Power Up Types
		power_up_types = new Array<PowerUpType>();
		
		for (int i = 0; i < 2; i ++)
			power_up_types.add(new PowerUpType(i));

		// Init Enemy Patterns
		pattern_array = new Array<EnemyPattern>();
		pattern_array.add(new EnemyPattern(new Sprite(new Texture("enemy.bmp")), pattern_array, 0, 0));
		pattern_array.first().newPattern(2);

		// Set Viewport to FitViewport
		viewport.apply();
		bitmap_font.getData();

		// Don't restrict cursor to screen boundaries
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

		// Keep tracking cursor position, transform screen to world coordinates
		// TODO: Cursor class
		cursor_position.set(Gdx.input.getX(), Gdx.input.getY());
		viewport.unproject(cursor_position);
		cursor.setPosition(cursor_position.x - cursor.getWidth()/2, cursor_position.y - cursor.getHeight()/2);

		// Update everything
		player.updateOffset(cursor_position);
		player.update(delta);

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.update(delta, player);

		for (EnemyPattern pattern:pattern_array)
			pattern.update(delta, player, power_up_types);

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render everything
		sprite_batch.begin();
		sprite_batch.disableBlending();
		sprite_batch.draw(GAME_COLOR.get(0), 0, 0, MAP_WIDTH, MAP_HEIGHT);
		sprite_batch.draw(GAME_COLOR.get(1), 0, MAP_HEIGHT, MAP_WIDTH, DOCK_HEIGHT);
		sprite_batch.enableBlending();

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.render(sprite_batch);

		for (EnemyPattern pattern:pattern_array)
			pattern.render(sprite_batch);

		player.render(sprite_batch);
		bitmap_font.draw(sprite_batch, "SCORE: " + String.valueOf(SCORE), GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);
		bitmap_font.draw(sprite_batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), GAME_DOCK_PADDING + 300, GAME_HEIGHT - GAME_DOCK_PADDING);
		cursor.draw(sprite_batch);
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
