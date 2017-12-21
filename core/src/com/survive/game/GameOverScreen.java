package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import static com.survive.game.Survive.*;

public class GameOverScreen extends Screen {

	private static final int SCREEN_PADDING = 50;

	private Text title;
	private TextList information;
	private TextList options;

	GameOverScreen(Game game) {

		super(game);

		title = new Text(GAME_FONT.get(2), "GAME OVER");
		title.setOrigin(0, SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING);

		Player player = GameScreen.getPlayer();

		Text kills = new Text(GAME_FONT.get(0), "KILLS: " + String.valueOf(player.getKills()));
		kills.setPadding(5);
		Text time_alive = new Text(GAME_FONT.get(0), "TIME: " + String.valueOf((int) player.getTimeAlive()));
		time_alive.setPadding(5);
		Text score = new Text(GAME_FONT.get(0), "SCORE: " + String.valueOf(player.getScore()));
		score.setPadding(5);

		information = new TextList(kills, time_alive, score);
		information.setOrigin(0, SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING * 2 - title.height);

		// Combine all InputProcessors
		InputMultiplexer input_multiplexer = new InputMultiplexer();

		Text restart = new Text(GAME_FONT.get(1), "RESTART");
		restart.setPadding(15);
		input_multiplexer.addProcessor(restart.button(GAME_SCREEN));
		Text back = new Text(GAME_FONT.get(1), "BACK");
		back.setPadding(15);
		input_multiplexer.addProcessor(back.button(MAIN_MENU_SCREEN));

		// Create Text list for GameOverScreen, enable interaction
		options = new TextList(restart, back);
		options.setOrigin(3,SCREEN_PADDING, SCREEN_PADDING);
		input_multiplexer.addProcessor(options.buttonController());

		Gdx.input.setInputProcessor(input_multiplexer);
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

		getPlatform().updateCursor();
		options.update();
	}

	@Override
	public void screenRender() {

		getSpriteBatch().draw(GAME_COLOR.get(0), 0, 0, GAME_WIDTH, GAME_HEIGHT);
		title.render();
		information.render();
		options.render();
		getPlatform().renderCursor();
	}
}
