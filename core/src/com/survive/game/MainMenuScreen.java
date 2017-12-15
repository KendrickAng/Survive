package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.*;

public class MainMenuScreen implements Screen {

    private static final int SCREEN_PADDING = 50;

    private Survive game;
    private Cursor cursor;
    private Viewport viewport;
	private SpriteBatch sprite_batch;
	private Text title;
    private TextList options;
    private Sprite player;

    MainMenuScreen(Survive game) {

    	this.game = game;
    	this.cursor = game.cursor;
    	this.viewport = game.viewport;
    	this.sprite_batch = game.sprite_batch;
    	this.player = game.player;

    	title = new Text(GAME_FONT.get(2), "SURVIVE");
    	title.setOrigin(0, SCREEN_PADDING, GAME_HEIGHT - SCREEN_PADDING);

		player.setPosition(SCREEN_PADDING * 2 + title.width, GAME_HEIGHT - SCREEN_PADDING - player.getHeight()/2);
		player.setScale(2);
		player.setRotation(45);

    	Text play = new Text(GAME_FONT.get(1), "PLAY");
    	play.setPadding(15);
    	play.button(1);
		Text settings = new Text(GAME_FONT.get(1), "SETTINGS");
		settings.setPadding(15);
		settings.button(0);
    	Text exit = new Text(GAME_FONT.get(1), "EXIT");
    	exit.setPadding(15);
    	exit.button(0);

    	options = new TextList(play, settings, exit);
    	options.setOrigin(3, SCREEN_PADDING, SCREEN_PADDING);
    	options.keyboard();

    	// Don't restrict cursor to screen boundaries
		Gdx.input.setCursorCatched(true);
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
		player.draw(sprite_batch);
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
