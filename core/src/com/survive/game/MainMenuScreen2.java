package com.survive.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen2 implements Screen {

    private Stage stage;
    private Table root_table;
    private Table header_table;
    private Skin skin;
    private Label header;
    private TextButton play_button;
    private TextButton exit_button;

    MainMenuScreen2(final Survive game) {

        // Define constants
        final int COLUMN_WIDTH = Gdx.graphics.getWidth() / 12;
        final int ROW_HEIGHT = Gdx.graphics.getWidth() / 12;
        final float TABLE_PADDING = 10.0f;

        // Stage catches events
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));

        // Size root table to stage, set default padding
        root_table = new Table(skin);
        root_table.setFillParent(true);
        root_table.setDebug(true);
        root_table.defaults().pad(TABLE_PADDING);

        // Initialise, define header
        header = new Label("SURVIVE", skin, "black");
        header.setAlignment(Align.center);
        header.setFontScale(2.0f);

        // Create Play button and
        play_button = new TextButton("Play", skin, "default");
        play_button.setPosition(COLUMN_WIDTH, ROW_HEIGHT);
        play_button.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Play touchDown", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Play touchUp", "touch done at (" + x + ", " + y + ")");
                game.setScreen(new GameScreen(game));
            }
        });

        // Create Exit button
        exit_button = new TextButton("Exit", skin, "default");
        exit_button.setPosition(COLUMN_WIDTH * 8, ROW_HEIGHT);
        exit_button.addListener(new InputListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Exit touchDown", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Exit touchUp", "touch done at (" + x + ", " + y + ")");
                System.exit(0);
            }
        });

        // Add actors to root table and configure
        root_table.add(header).colspan(2).fillX().top();
        root_table.row();
        root_table.add(play_button).width(COLUMN_WIDTH * 4).height(ROW_HEIGHT).expand();
        root_table.add(exit_button).width(COLUMN_WIDTH * 4).height(ROW_HEIGHT).expand();

        stage.addActor(root_table);
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) { stage.getViewport().update(width, height, true); }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }
}