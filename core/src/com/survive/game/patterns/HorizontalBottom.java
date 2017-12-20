package com.survive.game.patterns;

import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;

import static com.survive.game.Enemy.SPAWN_TIMER;
import static com.survive.game.EnemyPatternController.addRandomPattern;
import static com.survive.game.EnemyPatternController.getSprite;
import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;
import static com.survive.game.Screen.getDelta;

public class HorizontalBottom extends EnemyPattern {

	private float y = SPAWN_PADDING;

	public HorizontalBottom(float delay) {

		super(delay);
		this.SPAWN_COUNT = 12;
		this.SPAWN_INTERVAL = 0.1f;
		this.ENEMY_SPEED = 50;
		this.NEXT_PATTERN_DELAY = 2;
		this.RUN_DELAY = SPAWN_TIMER;
	}

	@Override
	protected void spawn() {

		Enemy enemy = new Enemy(getSprite());
		enemy.x = SPAWN_PADDING + (MAP_WIDTH - SPAWN_PADDING * 2) / (SPAWN_COUNT - 1) * spawned;
		enemy.y = y;
		getArray().add(enemy);
	}

	@Override
	protected void spawnDone() { addRandomPattern(NEXT_PATTERN_DELAY); }

	@Override
	protected void run() {

		y += ENEMY_SPEED * getDelta();

		if (y > MAP_HEIGHT - getSprite().getWidth() / 2) {

			transfer();
			dispose();
		}

		for (Enemy enemy:getArray())
			enemy.y = y;
	}
}
