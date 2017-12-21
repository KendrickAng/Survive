package com.survive.game.powerups;

import com.survive.game.*;

import static com.survive.game.EnemyPatternController.getEnemyPatterns;
import static com.survive.game.GameScreen.getPlayer;
import static com.survive.game.Survive.getSpriteBatch;

public class Explosion extends PowerUp {

	private static final float RADIUS = 99.5f;

	public Explosion(PowerUpType type) { super(type); }

	@Override
	protected void updateAnimation() {

		float x = getX();
		float y = getY();

		addTimer();

		for (EnemyPattern pattern:getEnemyPatterns())
			for (Enemy enemy : pattern.getArray())
				if (enemy.getHitBox().intersectCircle(new Circle(x, y, RADIUS))) {

					pattern.disposeEnemy(enemy);
					getPlayer().addKills();
				}

		if (getTimer() > getAnimation().getAnimationDuration())
			dispose();
	}

	@Override
	protected void renderAnimation() {

		float x = getX();
		float y = getY();

		getSpriteBatch().draw(getAnimation().getKeyFrame(getTimer(), false), x - RADIUS, y - RADIUS);
	}
}
