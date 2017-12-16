package com.survive.game;

public class DesktopPlatform implements Platform {

	@Override
	public void updateSettings(Survive game) {}

	@Override
	public void updateCursor(Survive game) {

		game.cursor.update(game);
	}

	@Override
	public void renderCursor(Survive game) {

		game.cursor.render(game.sprite_batch);
	}
}
