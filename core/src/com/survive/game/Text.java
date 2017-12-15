package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.Survive.GAME_COLOR;
import static com.survive.game.Survive.GAME_WIDTH;

class Text {

	private float x;
	private float y;
	private float origin_x;
	private float origin_y;
	private int type;
	private int origin;
	private boolean button;
	private boolean mouse_over;
	private BitmapFont bitmap_font;
	private GlyphLayout glyph_layout;

	float width;
	float height;
	float padding;

	Text(BitmapFont bitmap_font) {

		glyph_layout = new GlyphLayout(bitmap_font, "");
		this.bitmap_font = bitmap_font;
	}

	Text(BitmapFont bitmap_font, CharSequence char_sequence) {

		glyph_layout = new GlyphLayout(bitmap_font, "");
		this.bitmap_font = bitmap_font;
		this.setText(char_sequence);
	}

	void setPadding(float padding) {

		this.padding = padding;
	}

	void setOrigin(int origin, float origin_x, float origin_y) {

		this.origin = origin;
		this.origin_x = origin_x;
		this.origin_y = origin_y;
		this.updateOrigin();
	}

	private void updateOrigin() {

		switch(origin) {

			case 0:
				this.x = origin_x;
				this.y = origin_y;
				break;

			case 1:
				this.x = origin_x - width;
				this.y = origin_y;
				break;

			case 2:
				this.x = origin_x - width;
				this.y = origin_y + height;
				break;

			case 3:
				this.x = origin_x;
				this.y = origin_y + height;
				break;
		}
	}

	void setText(CharSequence char_sequence) {

		glyph_layout.setText(bitmap_font, char_sequence);
		height = glyph_layout.height;
		width = glyph_layout.width;
		this.updateOrigin();
	}

	void button(int type) {

		button = true;
		this.type = type;
	}

	void update(Survive game) {

		if (button) {

			mouse_over = y - height - padding < game.cursor.position.y && game.cursor.position.y < y + padding;

			if (mouse_over && Gdx.input.isTouched())
				switch (type) {

					case 0:
						Gdx.app.exit();
						break;

					case 1:
						game.setScreen(new GameScreen(game));
						break;
				}
		}
	}

	void render(SpriteBatch sprite_batch) {

		if (mouse_over)
			sprite_batch.draw(GAME_COLOR.get(1), 0, y - height - padding, GAME_WIDTH, height + padding * 2);

		bitmap_font.draw(sprite_batch, glyph_layout, x, y);
	}
}
