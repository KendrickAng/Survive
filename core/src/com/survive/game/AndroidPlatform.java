package com.survive.game;

public class AndroidPlatform implements Platform {

	@Override
	public void updateSettings(Survive game) {

		game.cursor.hidden = true;
	}

	@Override
	public void updateCursor(Survive game) {

		if (!game.cursor.hidden)
			game.cursor.update(game);
	}

	@Override
	public void renderCursor(Survive game) {

		if (!game.cursor.hidden)
			game.cursor.render(game.sprite_batch);
	}
}
