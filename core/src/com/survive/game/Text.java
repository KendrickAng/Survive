package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.Survive.GAME_COLOR;
import static com.survive.game.Survive.GAME_WIDTH;

class Text {

	private static final float BLINK_TIMER = 0.5f;
	private float x;
	private float origin_x;
	private float origin_y;
	private int origin;
	private BitmapFont bitmap_font;
	private GlyphLayout glyph_layout;
	private CharSequence char_sequence;
	private int button_type;
	private boolean blink;
	private float blink_timer;

	float y;
	float width;
	float height;
	float padding;
	boolean lock;
	boolean select;
	boolean enter;
	boolean touched;

	// Loads a new font
	Text(BitmapFont bitmap_font) {

		glyph_layout = new GlyphLayout(bitmap_font, "");
		this.bitmap_font = bitmap_font;
	}

	// Loads a new font, reading char_sequence
	Text(BitmapFont bitmap_font, CharSequence char_sequence) {

		glyph_layout = new GlyphLayout(bitmap_font, "");
		this.bitmap_font = bitmap_font;
		this.setText(char_sequence, true);
	}

	void setPadding(float padding) {

		this.padding = padding;
	}

	// Sets origin of current font instance
	void setOrigin(int origin, float origin_x, float origin_y) {

		this.origin = origin;
		this.origin_x = origin_x;
		this.origin_y = origin_y;
		this.updateOrigin();
	}

	/* Changes point of drawing for font:
		Starting from point (0),
		Ending at point(1),
		Ending at + above previous line (2)
		Starting from + above previous line (3)
	 */
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

	// Updates the line of text using current glyphLayout
	void setText(CharSequence char_sequence, boolean override) {

		if (override)
			this.char_sequence = char_sequence;

		glyph_layout.setText(bitmap_font, char_sequence);
		height = glyph_layout.height;
		width = glyph_layout.width;
		this.updateOrigin();
	}

	// Create button of specified function (Play, exit etc)
	TextInputProcessor button(Survive game, int button_type) {

		this.button_type = button_type;
		return new TextInputProcessor(game, this);
	}

	void update(Survive game, float delta) {

		blink_timer += delta;

		if (blink_timer > BLINK_TIMER) {

			blink = !blink;
			blink_timer -= BLINK_TIMER;
		}

		// If text moused over, add "_" to text. Else draw default text based on char sequence
		if (select && blink)
			this.setText(char_sequence + " _", false);
		else
			this.setText(char_sequence, false);

		/* Select action based on button classification
			Exit game (0)
			Play game (1)
			Back to main menu (2)
			Settings (3)
		 */
		if (enter)
			switch (button_type) {

				case 0:
					Gdx.app.exit();
					break;

				case 1:
					game.setScreen(new GameScreen(game));
					break;

				case 2:
					game.setScreen(new MainMenuScreen(game));
					break;

				case 3:
					game.setScreen(new SettingsScreen(game));
					break;
			}
		}

	void render(SpriteBatch sprite_batch) {

		// Draw highlight if selected
		if (select)
			sprite_batch.draw(GAME_COLOR.get(1), 0, y - height - padding, GAME_WIDTH, height + padding * 2);

		// Draw text over highlight (if any)
		bitmap_font.draw(sprite_batch, glyph_layout, x, y);
	}
}
