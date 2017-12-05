package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

    private static final int PLAY_BUTTON_HEIGHT = 300;
    private static final int PLAY_BUTTON_WIDTH = 600;
    private static final int EXIT_BUTTON_HEIGHT = 300;
    private static final int EXIT_BUTTON_WIDTH = 600;

    private int play_button_y = Gdx.graphics.getHeight()/8;
    private int play_button_x = Gdx.graphics.getWidth()/4 - PLAY_BUTTON_WIDTH/2;
    private int exit_button_y = play_button_y;
    private int exit_button_x = Gdx.graphics.getWidth() * 3/4 - EXIT_BUTTON_WIDTH/2;

	private OrthographicCamera camera;
	private Vector3 cursor_position;
	private SpriteBatch sprite_batch;
	private BitmapFont bitmap_font;
	private Texture cursor;

    private Texture playButtonActive;
    private Texture playButtonInactive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;

    private int cursor_radius;

    MainMenuScreen(Survive game) {

        //Initialise game and textures
        playButtonActive = new Texture("play_inverse.jpg");
        playButtonInactive = new Texture("play.jpg");
        exitButtonActive = new Texture("exit_inverse.jpg");
        exitButtonInactive = new Texture("exit.jpg");

		camera = game.camera;
		cursor_position = game.cursor_position;
		sprite_batch = game.sprite_batch;
		bitmap_font = game.bitmap_font;
		cursor = game.cursor;
        cursor_radius = game.cursor_radius;

        bitmap_font.getData().setScale(5);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Keep tracking cursor position, transform screen to world coordinates
        cursor_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(cursor_position);

        sprite_batch.begin();
        // Game header
        bitmap_font.draw(sprite_batch, "Welcome to Survive.", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight() * 2/3);

        // Highlight buttons if mouse hovers over
        if (cursor_position.x <= (play_button_x + PLAY_BUTTON_WIDTH) && cursor_position.x >= play_button_x &&
                cursor_position.y <= (play_button_y + PLAY_BUTTON_HEIGHT) && cursor_position.y >= play_button_y)

            sprite_batch.draw(playButtonActive, play_button_x, play_button_y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        else

            sprite_batch.draw(playButtonInactive, play_button_x, play_button_y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

        if (cursor_position.x <= (exit_button_x + EXIT_BUTTON_WIDTH) && cursor_position.x >= exit_button_x &&
                cursor_position.y <= (exit_button_y + EXIT_BUTTON_HEIGHT) && cursor_position.y >= exit_button_y)

            sprite_batch.draw(exitButtonActive, exit_button_x, exit_button_y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        else

            sprite_batch.draw(exitButtonInactive, exit_button_x, exit_button_y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

        sprite_batch.draw(cursor, cursor_position.x - cursor_radius, cursor_position.y - cursor_radius);
        sprite_batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

        bitmap_font.dispose();
    }
}
