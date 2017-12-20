package com.survive.game.powerups;

import com.survive.game.*;

import static com.survive.game.EnemyPatternController.getEnemyPatterns;
import static com.survive.game.GameScreen.getPlayer;
import static com.survive.game.Survive.getSpriteBatch;

public class Explosion extends PowerUpType {

	private static final float RADIUS = 49.5f;

	public Explosion() {

		super();

		this.COUNT = 3;
		this.MIN_INTERVAL = 3;
		this.MAX_INTERVAL = 10;
		this.addAnimation("power_up_0_animation.atlas");
	}

	@Override
	protected void updateAnimation(PowerUp power_up) {

		float x = power_up.getX();
		float y = power_up.getY();

		power_up.addTimer();

		for (EnemyPattern pattern:getEnemyPatterns())
			for (Enemy enemy : pattern.getArray())
				if (enemy.getHitBox().intersectCircle(new Circle(x, y, RADIUS))) {

					pattern.disposeEnemy(enemy);
					getPlayer().addKills();
				}

		if (power_up.getTimer() > power_up.getAnimation().getAnimationDuration())
			dispose(power_up);
	}

	@Override
	protected void renderAnimation(PowerUp power_up) {

		float x = power_up.getX();
		float y = power_up.getY();

		getSpriteBatch().draw(power_up.getAnimation().getKeyFrame(power_up.getTimer(), false), x - RADIUS, y - RADIUS);
	}
}
