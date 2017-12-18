package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.survive.game.Survive.*;


public class MainMenuScreen extends Screen {

    private static final int SCREEN_PADDING = 50;

	private Text title;
    private TextList options;
    private Sprite player_sprite;

	MainMenuScreen(Game game) {

		super(game);

    	// Use same resources as game
		player_sprite = getPlayerSprite();

    	// Game title
    	title = new Text(GAME_FONT.get(2), "SURVIVE");
    	title.setOrigin(0, SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING);

    	// Set image beside game title
		player_sprite.setPosition(SCREEN_PADDING * 2 + title.width, GAME_HEIGHT - SCREEN_PADDING - player_sprite.getHeight()/2);
		player_sprite.setScale(2);
		player_sprite.setRotation(45);

		// Combine all InputProcessors
		InputMultiplexer input_multiplexer = new InputMultiplexer();

		// bitmap_28.fnt for menu text. Button types can be found in Text
    	Text play = new Text(GAME_FONT.get(1), "PLAY");
    	play.setPadding(15);
    	input_multiplexer.addProcessor(play.button(GAME_SCREEN));
		Text settings = new Text(GAME_FONT.get(1), "SETTINGS");
		settings.setPadding(15);
		input_multiplexer.addProcessor(settings.button(SETTINGS_SCREEN));
    	Text exit = new Text(GAME_FONT.get(1), "EXIT");
    	exit.setPadding(15);
    	input_multiplexer.addProcessor(exit.button(-1));

		// Load custom text-buttons onto screen
		options = new TextList(play, settings, exit);
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
		player_sprite.draw(getSpriteBatch());
		options.render();
		Survive.getPlatform().renderCursor();
	}
}
