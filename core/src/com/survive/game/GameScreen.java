package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.survive.game.Survive.GAME_HEIGHT;
import static com.survive.game.Survive.GAME_WIDTH;

public class GameScreen implements Screen {

	private static final int GAME_DOCK_PADDING = 10;
	private static final int DOCK_HEIGHT = 14 + GAME_DOCK_PADDING * 2;
	private static final int SCORE = 1234567890;

	static final int MAP_HEIGHT = GAME_HEIGHT - DOCK_HEIGHT;
	static final int MAP_WIDTH = GAME_WIDTH;

	private Viewport viewport;
	private Vector2 cursor_position;
	private SpriteBatch sprite_batch;
	private BitmapFont bitmap_font;
	private Sprite game_background;
	private Sprite game_dock;
	private Sprite cursor;
	private Player player;
	private Array<EnemyPattern> pattern_array;
	private PowerUps power_ups;

	// For collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();

	GameScreen(Survive game) {

		viewport = game.viewport;
		cursor_position = game.cursor_position;
		sprite_batch = game.sprite_batch;
		bitmap_font = game.bitmap_font;
		cursor = game.cursor;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#263238"));
        pixmap.drawPixel(0, 0);

		game_background = new Sprite(new Texture(pixmap));
		game_background.setSize(MAP_WIDTH, MAP_HEIGHT);

		pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.valueOf("#1A2226"));
		pixmap.drawPixel(0, 0);
		game_dock = new Sprite(new Texture(pixmap));
		game_dock.setSize(GAME_WIDTH, DOCK_HEIGHT);
		game_dock.setPosition(0, MAP_HEIGHT);

		pixmap.dispose();

		// Init Player
		player = new Player(new Sprite(new Texture("player.bmp")));

		// Init Enemy Patterns
		pattern_array = new Array<EnemyPattern>();
		pattern_array.add(new EnemyPattern(new Sprite(new Texture("enemy.bmp")), pattern_array, 1, 0));
		pattern_array.first().next_pattern(2);

		// Set Viewport to FitViewport
		viewport.apply();
		bitmap_font.getData();
		power_ups = new PowerUps();

		// Don't restrict cursor to screen boundaries
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {

		// Keep tracking cursor position, transform screen to world coordinates
		cursor_position.set(Gdx.input.getX(), Gdx.input.getY());
		viewport.unproject(cursor_position);
		cursor.setPosition(cursor_position.x - cursor.getWidth()/2, cursor_position.y - cursor.getHeight()/2);

		// Update everything
		power_ups.update(delta);
		player.cursorOffset(cursor_position);
		player.update(delta);
		testCollisions();

		for (EnemyPattern pattern:pattern_array) {

			switch (pattern.pattern) {

				case 1:
					pattern.pattern1(delta, player);
					break;

				case 2:
					pattern.pattern2(delta);
					break;

				case 3:
					pattern.pattern3(delta);
					break;

				case 4:
					pattern.pattern4(delta, player);
					break;
			}

			pattern.update();
			pattern.playerCollision(player);
		}

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render everything
		sprite_batch.begin();
		sprite_batch.disableBlending();
		game_background.draw(sprite_batch);
		game_dock.draw(sprite_batch);
		sprite_batch.enableBlending();
		power_ups.render(sprite_batch);

		for (EnemyPattern pattern:pattern_array)
			pattern.render(sprite_batch);

		player.render(sprite_batch);
		bitmap_font.draw(sprite_batch, "SCORE: " + String.valueOf(SCORE), GAME_DOCK_PADDING, GAME_HEIGHT - GAME_DOCK_PADDING);
		bitmap_font.draw(sprite_batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), GAME_DOCK_PADDING + 300, GAME_HEIGHT - GAME_DOCK_PADDING);
		cursor.draw(sprite_batch);
		sprite_batch.end();
	}

	private void onCollisionPlayerWithItem(PowerUp power_up) {

		power_up.setCollected(true);
		Gdx.app.log("Item", "Item collected!");
	}

	private void testCollisions() {

		// Set hitboxes
		r1.set(player.x - player.width/2,
				player.y - player.height/2,
				player.width,
				player.height);

		// Test collisions Player <--> Item1
		for (PowerUp item1 : power_ups.getPowerUps()) {

			if(item1.getCollected()) continue;

			r2.set(item1.getX() - item1.getSprite().getWidth()/2,
					item1.getY() - item1.getSprite().getHeight()/2,
					item1.getSprite().getWidth(),
					item1.getSprite().getHeight());

			if(!r1.overlaps(r2)) continue;

			onCollisionPlayerWithItem(item1);
			break;
		}
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
