package com.survive.game.powerups;

import com.survive.game.PowerUpType;

import static com.survive.game.PowerUpTypeController.EXPLOSION_POWER_UP;

public class ExplosionType extends PowerUpType {


	public ExplosionType() {

		super();

		this.TYPE = EXPLOSION_POWER_UP;
		this.COUNT = 3;
		this.MIN_INTERVAL = 3;
		this.MAX_INTERVAL = 10;
		this.setIcon("power_up_0.bmp");
		this.addAnimation("power_up_0_animation.atlas");
	}
}
