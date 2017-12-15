package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.Survive.GAME_COLOR;
import static com.survive.game.Survive.GAME_WIDTH;

class Button {

	private static final float PADDING = 10;

	private float x;
	private float y;
	private float height;
	private BitmapFont bitmap_font;
	private GlyphLayout glyph_layout;

	private boolean mouse_over;

	Button(BitmapFont bitmap_font, CharSequence char_sequence, float x, float y) {

		this.x = x;
		this.y = y;
		this.bitmap_font = bitmap_font;

		glyph_layout = new GlyphLayout(bitmap_font, char_sequence);
		height = glyph_layout.height;
	}

	void update(Survive game) {

		mouse_over = y - height - PADDING < game.cursor.position.y && game.cursor.position.y < y + PADDING;

		if (mouse_over && Gdx.input.isTouched())
			game.setScreen(new GameScreen(game));
	}

	void render(SpriteBatch sprite_batch) {

		if (mouse_over)
			sprite_batch.draw(GAME_COLOR.get(1), 0, y - height - PADDING, GAME_WIDTH, height + PADDING * 2);

		bitmap_font.draw(sprite_batch, glyph_layout, x, y);
	}
}
