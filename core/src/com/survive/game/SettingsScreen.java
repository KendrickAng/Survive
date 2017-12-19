package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import static com.survive.game.Survive.*;

class SettingsScreen extends Screen {

	private static final int SCREEN_PADDING = 50;

	private Text title;
	private TextList options;

	SettingsScreen(Game game) {

		super(game);

		// Title
		title = new Text(GAME_FONT.get(2), "SETTINGS");
		title.setOrigin(0, SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING);

		// Combine all InputProcessors
		InputMultiplexer input_multiplexer = new InputMultiplexer();

		Text back = new Text(GAME_FONT.get(1), "BACK");
		back.setPadding(15);
		input_multiplexer.addProcessor(back.button(MAIN_MENU_SCREEN));

		options = new TextList(back);
		options.setOrigin(3, SCREEN_PADDING, SCREEN_PADDING);
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
		options.render();
		getPlatform().renderCursor();
	}
}
