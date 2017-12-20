package com.survive.game.powerups;

import com.survive.game.*;

import static com.survive.game.EnemyPatternController.getEnemyPatterns;
import static com.survive.game.Survive.getSpriteBatch;
import static com.survive.game.GameScreen.getPlayer;

public class Shield extends PowerUpType {

	private static final float RADIUS_0 = 25;
	private static final float RADIUS_1 = 49.5f;

	public Shield() {

		super();

		this.COUNT = 1;
		this.MIN_INTERVAL = 7;
		this.MAX_INTERVAL = 20;
		this.addAnimation("power_up_1_animation.atlas");
		this.addAnimation("power_up_0_animation.atlas");
	}

	@Override
	protected void updateAnimation(PowerUp power_up) {

		float x = power_up.getX();
		float y = power_up.getY();

		switch (power_up.getIndex()) {

			case 0:

				if (power_up.getTimer() < power_up.getAnimation().getAnimationDuration())
					power_up.addTimer();

				x = getPlayer().getX();
				y = getPlayer().getY();
				power_up.setX(x);
				power_up.setY(y);

				for (EnemyPattern pattern:getEnemyPatterns())
					for (Enemy enemy : pattern.getArray())
						if (enemy.isSpawned() && enemy.getHitBox().intersectCircle(new Circle(x, y, RADIUS_0))) {

							pattern.disposeEnemy(enemy);
							getPlayer().addKills();

							power_up.nextAnimation();
							power_up.resetTimer();
						}
				break;

			case 1:

				power_up.addTimer();

				for (EnemyPattern pattern:getEnemyPatterns())
					for (Enemy enemy : pattern.getArray())
						if (enemy.getHitBox().intersectCircle(new Circle(x, y, RADIUS_1))) {

							pattern.disposeEnemy(enemy);
							getPlayer().addKills();
						}

				if (power_up.getTimer() > power_up.getAnimation().getAnimationDuration())
					dispose(power_up);
				break;
		}
	}

	@Override
	protected void renderAnimation(PowerUp power_up) {

		float x = power_up.getX();
		float y = power_up.getY();

		switch (power_up.getIndex()) {

			case 0:

				getSpriteBatch().draw(power_up.getAnimation().getKeyFrame(power_up.getTimer(), false), x - RADIUS_0, y - RADIUS_0);
				break;

			case 1:

				getSpriteBatch().draw(power_up.getAnimation().getKeyFrame(power_up.getTimer(), false), x - RADIUS_1, y - RADIUS_1);
				break;
		}
	}
}
