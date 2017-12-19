package com.survive.game;

public class AndroidPlatform implements Platform {

	@Override
	public void updateSettings() {

		Survive.getCursor().hidden = true;
	}

	// Don't display cursor if the setting is hidden.
	@Override
	public void updateCursor() {

		if (!Survive.getCursor().hidden)
			Survive.getCursor().update();
	}

	@Override
	public void renderCursor() {

		if (!Survive.getCursor().hidden)
			Survive.getCursor().render();
	}
}
