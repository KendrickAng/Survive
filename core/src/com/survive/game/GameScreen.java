package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.*;

public class GameScreen extends Screen {

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

	private static Player player;
	private static Array<PowerUpType> power_up_types;
	private static EnemyPatternController enemy_controller;
	private Text score;
	private Text fps;


	GameScreen(Game game) {

		super(game);

		// Init Player
		player = new Player(new Sprite(new Texture("player.bmp")));

		// Init Power Up Types
		// TODO: PowerUpController
		power_up_types = new Array<PowerUpType>();
		
		for (int i = 0; i < 1; i ++)
			power_up_types.add(new PowerUpType(i));

		// Init Enemy Patterns
		enemy_controller = new EnemyPatternController();

		// Set Viewport to FitViewport
		// viewport.apply();

		// Use bitmap_21.fnt. Load score & fps HUD text
		score = new Text(GAME_FONT.get(0));
		score.setOrigin(0, GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);
		fps = new Text(GAME_FONT.get(0));
		fps.setOrigin(1, GAME_WIDTH - GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);

		Viewport viewport = getViewport();
		Gdx.input.setCursorPosition(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2);
	}

	@Override
	public void show() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

	@Override
	public void screenUpdate() {

		// Update game instance
		getPlatform().updateCursor();
		player.update();

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.update();

		enemy_controller.update();

		score.setText("SCORE: " + String.valueOf(player.score), true);
		fps.setText("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), true);
	}

	@Override
	public void screenRender() {

		getSpriteBatch().disableBlending();
		getSpriteBatch().draw(GAME_COLOR.get(0), 0, 0, MAP_WIDTH, MAP_HEIGHT);
		getSpriteBatch().draw(GAME_COLOR.get(1), 0, MAP_HEIGHT, MAP_WIDTH, DOCK_HEIGHT);
		getSpriteBatch().enableBlending();

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.render();

		player.render();
		enemy_controller.render();
		score.render();
		fps.render();
		getPlatform().renderCursor();
	}

	public static Player getPlayer() { return player; }
	static Array<PowerUpType> getPowerUpTypes() { return power_up_types; }
}
