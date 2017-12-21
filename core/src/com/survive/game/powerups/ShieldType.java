package com.survive.game.powerups;

import com.survive.game.PowerUpType;

import static com.survive.game.PowerUpTypeController.SHIELD_POWER_UP;

public class ShieldType extends PowerUpType {

	public ShieldType() {

		super();

		this.TYPE = SHIELD_POWER_UP;
		this.COUNT = 1;
		this.MIN_INTERVAL = 7;
		this.MAX_INTERVAL = 20;
		this.setDelay(5);
		this.setIcon("power_up_1.bmp");
		this.addAnimation("power_up_1_animation.atlas");
		this.addAnimation("power_up_0_animation.atlas");
	}
}
