package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.Survive.*;

class SettingsScreen implements Screen {

	private static final int SCREEN_PADDING = 50;

	private Survive game;
	private SpriteBatch sprite_batch;
	private Text title;
	private TextList options;

	SettingsScreen(Survive game) {

		this.game = game;
		this.sprite_batch = game.sprite_batch;

		// Title
		title = new Text(GAME_FONT.get(2), "SETTINGS");
		title.setOrigin(0, SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING);

		// Combine all InputProcessors
		InputMultiplexer input_multiplexer = new InputMultiplexer();

		Text back = new Text(GAME_FONT.get(1), "BACK");
		back.setPadding(15);
		input_multiplexer.addProcessor(back.button(game, 2));

		options = new TextList(back);
		options.setOrigin(3, SCREEN_PADDING, SCREEN_PADDING);
		input_multiplexer.addProcessor(options.buttonController());

		Gdx.input.setInputProcessor(input_multiplexer);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

    	// Update
    	game.platform.updateCursor(game);
    	options.update(game, delta);

    	// Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite_batch.begin();
		sprite_batch.draw(GAME_COLOR.get(0), 0, 0, GAME_WIDTH, GAME_HEIGHT);
		title.render(sprite_batch);
		options.render(sprite_batch);
		game.platform.renderCursor(game);
		sprite_batch.end();
	}

	@Override
	public void resize(int width, int height) { game.viewport.update(width, height); }

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}
