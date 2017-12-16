package com.survive.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

class TextListInputProcessor implements InputProcessor {

	private int index;
	private Text[] texts;

	TextListInputProcessor(TextList text_list) {

		this.texts = text_list.texts;
		texts[index].select = true;
	}

	@Override
	public boolean keyDown(int keycode) {

		texts[index].select = false;

		switch (keycode) {

			case Input.Keys.UP:
				index--;
				break;
			case Input.Keys.DOWN:
				index++;
				break;
			case Input.Keys.ENTER:
				texts[index].enter = true;
				break;
		}

		if (index < 0)
			index = texts.length - 1;

		if (index >= texts.length)
			index = 0;

		texts[index].select = true;
		return true;
	}


	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		updateTouch();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		updateTouch();
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private void updateTouch() {

		for (int i = 0; i < texts.length; i ++) {

			if (texts[i].touch_position) {

				texts[index].select = false;
				index = i;
				texts[index].select = true;
			}
		}
	}
}
