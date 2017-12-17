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

	// Define HUD constants
	private static final int GAME_DOCK_PADDING = 10;
	private static final int DOCK_HEIGHT = 14 + GAME_DOCK_PADDING * 2;

	// Define game boundary constants
	public static final int MAP_HEIGHT = GAME_HEIGHT - DOCK_HEIGHT;
	public static final int MAP_WIDTH = GAME_WIDTH;

	// Define item (powerup) constants
	static final Array<Integer> POWER_UP_TYPE_COUNT = new Array<Integer>();
	static final Array<Integer> POWER_UP_TYPE_MIN_SPAWN_INTERVAL = new Array<Integer>();
	static final Array<Integer> POWER_UP_TYPE_MAX_SPAWN_INTERVAL = new Array<Integer>();

	static {

		POWER_UP_TYPE_COUNT.add(3);
		POWER_UP_TYPE_COUNT.add(1);

		POWER_UP_TYPE_MIN_SPAWN_INTERVAL.add(3);
		POWER_UP_TYPE_MIN_SPAWN_INTERVAL.add(7);

		POWER_UP_TYPE_MAX_SPAWN_INTERVAL.add(10);
		POWER_UP_TYPE_MAX_SPAWN_INTERVAL.add(20);
	}

	private Viewport viewport;
	private EnemyPatterns enemy_patterns;
	private Survive game;
	private Text score;
	private Text fps;

	public float delta;
	public Player player;
	Array<PowerUpType> power_up_types;
	SpriteBatch sprite_batch;

	GameScreen(Survive game) {

		this.game = game;

		viewport = game.viewport;
		sprite_batch = game.sprite_batch;

		// Init Player
		player = new Player(new Sprite(new Texture("player.bmp")));

		// Init Power Up Types
		power_up_types = new Array<PowerUpType>();
		
		for (int i = 0; i < 1; i ++)
			power_up_types.add(new PowerUpType(i));

		// Init Enemy Patterns
		enemy_patterns = new EnemyPatterns(this);

		// Set Viewport to FitViewport
		viewport.apply();

		// Use bitmap_21.fnt. Load score & fps HUD text
		score = new Text(GAME_FONT.get(0));
		score.setOrigin(0, GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);
		fps = new Text(GAME_FONT.get(0));
		fps.setOrigin(1, GAME_WIDTH - GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);

		Gdx.input.setCursorPosition(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

		this.delta = delta;

		// Update game instance
		game.platform.updateCursor(game);
		player.update(game, this);

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.update(delta, player);

		enemy_patterns.update();

		score.setText("SCORE: " + String.valueOf(player.score), true);
		fps.setText("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), true);

		// Render background, items, player, enemies, HUD
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sprite_batch.begin();
		sprite_batch.disableBlending();
		sprite_batch.draw(GAME_COLOR.get(0), 0, 0, MAP_WIDTH, MAP_HEIGHT);
		sprite_batch.draw(GAME_COLOR.get(1), 0, MAP_HEIGHT, MAP_WIDTH, DOCK_HEIGHT);
		sprite_batch.enableBlending();

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.render(sprite_batch);

		player.render(sprite_batch);
		enemy_patterns.render();
		score.render(sprite_batch);
		fps.render(sprite_batch);
		game.platform.renderCursor(game);
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
