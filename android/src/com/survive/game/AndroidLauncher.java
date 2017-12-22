package com.survive.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		config.useImmersiveMode = true;
		config.useGyroscope = true;
		config.useAccelerometer = false;
		config.useCompass = false;

		initialize(new Survive(new AndroidPlatform()), config);
	}
}
