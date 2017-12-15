package com.survive.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class TextList {

	private float x;
	private float y;
	private float width;
	private float height;
	private Text[] texts;

	TextList(Text... texts) {

		this.texts = texts;

		for (Text text:texts) {

			width = Math.max(width, text.width);
			height += text.height + text.padding * 2;
		}
	}

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

		for (Text text:texts)
			text.update(game);
	}

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
