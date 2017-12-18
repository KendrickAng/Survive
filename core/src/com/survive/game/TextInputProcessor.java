package com.survive.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class TextInputProcessor implements InputProcessor {

	private Text text;

	TextInputProcessor(Text text) {

		this.text = text;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
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

		updateTouch(screenX, screenY);

		// Execute button if selected & touched
		if (text.touched && text.select)
			text.enter = true;

		// Select text unless locked
		if (!text.lock)
			text.select = text.touched;

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

		updateTouch(screenX, screenY);

		// Select text unless locked
		if (!text.lock)
			text.select = text.touched;

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private void updateTouch(int screenX, int screenY) {

		Vector2 touch_position = new Vector2(screenX, screenY);
		Survive.getViewport().unproject(touch_position);

		// Check if touch is within button (including padding)
		text.touched = text.y - text.height - text.padding < touch_position.y && touch_position.y < text.y + text.padding;
	}
}
