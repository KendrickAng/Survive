package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class TextList {

	private static final float KEYBOARD_TIMER = 0.2f;

	private float x;
	private float y;
	private float width;
	private float height;
	private boolean keyboard;
	private int keyboard_index;
	private float keyboard_timer;
	private Text[] texts;

	TextList(Text... texts) {

		this.texts = texts;

		for (Text text:texts) {

			width = Math.max(width, text.width);
			height += text.height + text.padding * 2;
		}
	}

	void keyboard() {

		keyboard = true;

		for (Text text:texts)
			text.lock = true;
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

	void update(Survive game, float delta) {

		if (keyboard) {

			keyboard_timer += delta;
			texts[keyboard_index].select = false;

			for (int i = 0; i < texts.length; i ++)
				if (texts[i].cursor_over)
					keyboard_index = i;

			// TODO: Input Processor
			if (keyboard_timer > KEYBOARD_TIMER) {

				if (Gdx.input.isKeyPressed(Input.Keys.UP))
					keyboard_index --;

				if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
					keyboard_index ++;

				if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
					texts[keyboard_index].enter = true;

				keyboard_timer = 0;
			}

			if (keyboard_index < 0)
				keyboard_index = texts.length - 1;

			if (keyboard_index >= texts.length)
				keyboard_index = 0;

			texts[keyboard_index].select = true;
		}

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
