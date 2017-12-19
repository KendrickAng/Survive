package com.survive.game;

public class DesktopPlatform implements Platform {

	@Override
	public void updateSettings() {}

	@Override
	public void updateCursor() {

		Survive.getCursor().update();
	}

	@Override
	public void renderCursor() {

		Survive.getCursor().render();
	}
}
