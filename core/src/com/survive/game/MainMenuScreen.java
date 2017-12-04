package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen{

    private static final int PLAY_BUTTON_HEIGHT = 300;
    private static final int PLAY_BUTTON_WIDTH = 600;
    private static final int EXIT_BUTTON_HEIGHT = 300;
    private static final int EXIT_BUTTON_WIDTH = 600;

    private int play_button_y = Gdx.graphics.getHeight()/8;
    private int play_button_x = Gdx.graphics.getWidth()/4 - PLAY_BUTTON_WIDTH/2;
    private int exit_button_y = play_button_y;
    private int exit_button_x = Gdx.graphics.getWidth() * 3/4 - EXIT_BUTTON_WIDTH/2;

    Survive game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;

    Vector3 cursor_position;
    OrthographicCamera camera;

    BitmapFont font;

    public MainMenuScreen(Survive game) {

        //Initialise game and textures
        this.game = game;
        playButtonActive = new Texture("play_inverse.jpg");
        playButtonInactive = new Texture("play.jpg");
        exitButtonActive = new Texture("exit_inverse.jpg");
        exitButtonInactive = new Texture("exit.jpg");

        // For mouse position tracking
        cursor_position = game.cursor_position;

        // For the header title
        font = game.bitmap_font;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, (float) 0.5);
        Gdx.gl.glClear((GL20.GL_COLOR_BUFFER_BIT));

        // Keep tracking cursor position, transform screen to world coords
        cursor_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.camera.unproject(cursor_position);

        game.sprite_batch.begin();

        // Game header
        font.getData().setScale(5,5);
        font.draw(game.sprite_batch, "Welcome to Survive.", Gdx.graphics.getWidth() * 1/3, Gdx.graphics.getHeight() * 2/3);

        // Highlight buttons if mouse hovers over
        if (cursor_position.x <= (play_button_x + PLAY_BUTTON_WIDTH) && cursor_position.x >= play_button_x &&
                cursor_position.y <= (play_button_y + PLAY_BUTTON_HEIGHT) && cursor_position.y >= play_button_y)

            game.sprite_batch.draw(playButtonActive, play_button_x, play_button_y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        else

            game.sprite_batch.draw(playButtonInactive, play_button_x, play_button_y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

        if (cursor_position.x <= (exit_button_x + EXIT_BUTTON_WIDTH) && cursor_position.x >= exit_button_x &&
                cursor_position.y <= (exit_button_y + EXIT_BUTTON_HEIGHT) && cursor_position.y >= exit_button_y)

            game.sprite_batch.draw(exitButtonActive, exit_button_x, exit_button_y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        else

            game.sprite_batch.draw(exitButtonInactive, exit_button_x, exit_button_y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

        game.sprite_batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
