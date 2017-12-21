package com.survive.game;

import com.badlogic.gdx.utils.Array;
import com.survive.game.powerups.ExplosionType;
import com.survive.game.powerups.ShieldType;

public class PowerUpTypeController {

	public static final int EXPLOSION_POWER_UP = 0;
	public static final int SHIELD_POWER_UP = 1;

	private static Array<PowerUpType> power_up_types;

	PowerUpTypeController() {

		power_up_types = new Array<PowerUpType>();

		for (int i = EXPLOSION_POWER_UP; i <= SHIELD_POWER_UP; i++)
			addPowerUpType(i);
	}

	void update() {

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.update();
	}

	void render() {

		for (PowerUpType power_up_type:power_up_types)
			power_up_type.render();
	}

	private void addPowerUpType(int choice) {

		PowerUpType power_up_type = null;

		switch (choice) {

			case EXPLOSION_POWER_UP:
				power_up_type = new ExplosionType();
				break;

			case SHIELD_POWER_UP:
				power_up_type = new ShieldType();
				break;
		}

		if (power_up_type != null)
			power_up_types.add(power_up_type);
	}
}
