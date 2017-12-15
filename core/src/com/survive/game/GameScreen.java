package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.*;

public class GameScreen implements Screen {

	private static final int GAME_DOCK_PADDING = 10;
	private static final int DOCK_HEIGHT = 14 + GAME_DOCK_PADDING * 2;

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
	private SpriteBatch sprite_batch;
	private Cursor cursor;
	private Array<EnemyPattern> pattern_array;
	private Survive game;
	private Text score;
	private Text fps;

	float delta;
	Player player;
	Array<PowerUpType> power_up_types;

	GameScreen(Survive game) {

		this.game = game;

		viewport = game.viewport;
		sprite_batch = game.sprite_batch;
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

		score = new Text(GAME_FONT.get(0));
		score.setOrigin(0, GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);
		fps = new Text(GAME_FONT.get(0));
		fps.setOrigin(1, GAME_WIDTH - GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

		this.delta = delta;

		// Update
		cursor.update(game);
		player.update(game, this);

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.update(delta, player);

		for (EnemyPattern pattern:pattern_array)
			pattern.update(this);

		score.setText("SCORE: " + String.valueOf(player.score));
		fps.setText("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()));

		// Render
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		score.render(sprite_batch);
		fps.render(sprite_batch);
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
