package com.survive.game.powerups;

import com.survive.game.*;

import static com.survive.game.EnemyPatternController.getEnemyPatterns;
import static com.survive.game.GameScreen.getPlayer;
import static com.survive.game.Survive.getSpriteBatch;

public class Shield extends PowerUp {

	private static final float RADIUS_0 = 25;
	private static final float RADIUS_1 = 99.5f;

	public Shield(PowerUpType type) { super(type); }

	@Override
	protected void updateAnimation() {

		float x = getX();
		float y = getY();

		switch (getIndex()) {

			case 0:

				if (getTimer() < getAnimation().getAnimationDuration())
					addTimer();

				x = getPlayer().getX();
				y = getPlayer().getY();
				setX(x);
				setY(y);

				for (EnemyPattern pattern:getEnemyPatterns())
					for (Enemy enemy : pattern.getArray())
						if (enemy.isSpawned() && enemy.getHitBox().intersectCircle(new Circle(x, y, RADIUS_0))) {

							pattern.disposeEnemy(enemy);
							getPlayer().addKills();

							nextAnimation();
							resetTimer();
						}
				break;

			case 1:

				addTimer();

				for (EnemyPattern pattern:getEnemyPatterns())
					for (Enemy enemy : pattern.getArray())
						if (enemy.getHitBox().intersectCircle(new Circle(x, y, RADIUS_1))) {

							pattern.disposeEnemy(enemy);
							getPlayer().addKills();
						}

				if (getTimer() > getAnimation().getAnimationDuration())
					dispose();
				break;
		}
	}

	@Override
	protected void renderAnimation() {

		float x = getX();
		float y = getY();

		switch (getIndex()) {

			case 0:

				getSpriteBatch().draw(getAnimation().getKeyFrame(getTimer(), false), x - RADIUS_0, y - RADIUS_0);
				break;

			case 1:

				getSpriteBatch().draw(getAnimation().getKeyFrame(getTimer(), false), x - RADIUS_1, y - RADIUS_1);
				break;
		}
	}
}
