package com.survive.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class TextList {

	private float x;
	private float y;
	private float width;
	private float height;
	Text[] texts;

	// (Variable number of arguments) Add padding to texts, choose longer text width available
	TextList(Text... texts) {

		this.texts = texts;

		for (Text text:texts) {

			width = Math.max(width, text.width);
			height += text.height + text.padding * 2;
		}
	}

	// Activate button control input
	TextListInputProcessor buttonController() {

		for (Text text:texts)
			text.lock = true;

		return new TextListInputProcessor(this);
	}

	/* Changes point of drawing for text(s):
		Starting from point (0),
		Ending at point(1),
		Ending at + above previous line (2)
		Starting from + above previous line (3)
	 */
	void setOrigin(int origin, float origin_x, float origin_y) {

		switch (origin) {

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

	void update(Survive game) {

		// Continue parsing for inputs/mouse-overs on all 'Text-buttons'
		for (Text text:texts)
			text.update(game);
	}

	// Draw on screen all text(s) in TextList, including padding
	void render(SpriteBatch sprite_batch) {

		float height = 0;

		for (Text text:texts) {

			height += text.padding;
			text.setOrigin(0, x, y - height);
			height += text.height + text.padding;
			text.render(sprite_batch);
		}
	}
}
