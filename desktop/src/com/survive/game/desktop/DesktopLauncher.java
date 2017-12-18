package com.survive.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.survive.game.DesktopPlatform;
import com.survive.game.Survive;

import static com.survive.game.Survive.GAME_HEIGHT;
import static com.survive.game.Survive.GAME_WIDTH;

public class DesktopLauncher {

	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Survive";
		config.useGL30 = false;
		config.width = GAME_WIDTH;
		config.height = GAME_HEIGHT;
		config.fullscreen = false;
		config.vSyncEnabled = true;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;

		new LwjglApplication(new Survive(new DesktopPlatform()), config);

		// TODO: Somehow restart the game, in order to apply settings.
	}
}
