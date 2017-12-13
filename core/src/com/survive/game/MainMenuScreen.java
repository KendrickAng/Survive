package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.survive.game.Survive.GAME_HEIGHT;
import static com.survive.game.Survive.GAME_WIDTH;

public class MainMenuScreen implements Screen {

    // Scene2D stage and actors
    private Stage stage;
    private Table root_table;
    private Skin skin;
    private ImageButton play_button;
    private ImageButton exit_button;

    // Main menu textures
    private Image header;
    private Image background;
    private TextureAtlas atlas;
    private TextureRegionDrawable drawable_play;
    private TextureRegionDrawable drawable_title;
    private TextureRegionDrawable drawable_exit;
    private TextureRegionDrawable drawable_hover_e;
    private TextureRegionDrawable drawable_hover_p;

    private Sound sound_hover;
    private Sound sound_select;

    MainMenuScreen(final Survive game) {

        // Define constants
        final int COLUMN_WIDTH = Gdx.graphics.getWidth() / 12;
        final int ROW_HEIGHT = Gdx.graphics.getWidth() / 12;
        final float TABLE_PADDING = 10.0f;

        // Stage catches events
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas(Gdx.files.internal("unamed.pack"));
        drawable_play = new TextureRegionDrawable(atlas.findRegion("play_button"));
        drawable_title = new TextureRegionDrawable(atlas.findRegion("title"));
        drawable_exit = new TextureRegionDrawable(atlas.findRegion("exit_button"));
        drawable_hover_e = new TextureRegionDrawable(atlas.findRegion("exit_button_hover"));
        drawable_hover_p = new TextureRegionDrawable(atlas.findRegion("play_button_hover"));

        sound_hover = Gdx.audio.newSound(Gdx.files.internal("sfx_menu_move1.wav"));
        sound_select = Gdx.audio.newSound(Gdx.files.internal("sfx_menu_select1.wav"));

        // Set background colour for main menu
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#000000"));
        pixmap.drawPixel(0, 0);
        background = new Image(new Texture(pixmap));
        background.setSize(GAME_WIDTH, GAME_HEIGHT);
        pixmap.dispose();

        skin = new Skin(atlas);

        // Size root table to stage, set default padding
        root_table = new Table(skin);
        root_table.setFillParent(true);
        root_table.setDebug(true);
        root_table.defaults().pad(TABLE_PADDING);

        header = new Image(drawable_title);

        // Create Play button
        play_button = new ImageButton(drawable_play);
        play_button.setPosition(COLUMN_WIDTH, ROW_HEIGHT);
        play_button.addListener(new ClickListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Play touchDown", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.app.log("Play enter", "enter started at (" + x + ", " + y + ")");
                play_button.getStyle().imageUp=new TextureRegionDrawable(drawable_hover_p);
                sound_hover.play();
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.app.log("Play exit", "exit started at (" + x + ", " + y + ")");
                play_button.getStyle().imageUp=new TextureRegionDrawable(drawable_play);
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Play touchUp", "touch done at (" + x + ", " + y + ")");
                sound_select.play();
                stage.dispose();
                game.setScreen(new GameScreen(game));
            }
        });

        // Create Exit button
        exit_button = new ImageButton(drawable_exit);
        exit_button.setPosition(COLUMN_WIDTH * 8, ROW_HEIGHT);
        exit_button.addListener(new ClickListener() {

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Exit touchDown", "touch started at (" + x + ", " + y + ")");
                return true;
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.app.log("Exit enter", "enter started at (" + x + ", " + y + ")");
                exit_button.getStyle().imageUp=new TextureRegionDrawable(drawable_hover_e);
                sound_hover.play();
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.app.log("Exit exit", "exit started at (" + x + ", " + y + ")");
                exit_button.getStyle().imageUp=new TextureRegionDrawable(drawable_exit);
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Exit touchUp", "touch done at (" + x + ", " + y + ")");
                sound_select.play();
                // Exit cleanly
                Gdx.app.exit();
            }
        });

        // Add actors to root table and configure
        root_table.add(header).expand();
        root_table.row();
        root_table.add(play_button).width(Gdx.graphics.getWidth()).height(ROW_HEIGHT/2).padTop(10.0f).padBottom(10.0f);
        root_table.row();
        root_table.add(exit_button).width(Gdx.graphics.getWidth()).height(ROW_HEIGHT/2).padTop(10.0f).padBottom(10.0f);

        stage.addActor(background);
        stage.addActor(root_table);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

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

        sound_hover.dispose();
        sound_select.dispose();
        stage.dispose(); }
}
