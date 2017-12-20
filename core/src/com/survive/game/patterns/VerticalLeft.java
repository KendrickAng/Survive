package com.survive.game.patterns;

import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;

import static com.survive.game.Enemy.SPAWN_TIMER;
import static com.survive.game.EnemyPatternController.addRandomPattern;
import static com.survive.game.EnemyPatternController.getSprite;
import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;
import static com.survive.game.Screen.getDelta;

public class VerticalLeft extends EnemyPattern {

	private float x = SPAWN_PADDING;

	public VerticalLeft(float delay) {

		super(delay);
		this.SPAWN_COUNT = 8;
		this.SPAWN_INTERVAL = 0.1f;
		this.NEXT_PATTERN_DELAY = 2;
		this.RUN_DELAY = SPAWN_TIMER;
		this.ENEMY_SPEED = 50;
	}

	@Override
	protected void spawn() {

		Enemy enemy = new Enemy(getSprite());
		enemy.x = x;
		enemy.y = SPAWN_PADDING + (MAP_HEIGHT - SPAWN_PADDING * 2) / (SPAWN_COUNT - 1) * spawned;
		getArray().add(enemy);
	}

	@Override
	protected void spawnDone() { addRandomPattern(NEXT_PATTERN_DELAY); }

	@Override
	protected void run() {

		x += ENEMY_SPEED * getDelta();

		if (x > MAP_WIDTH - getSprite().getWidth() / 2) {

			transfer();
			dispose();
		}

		for (Enemy enemy:getArray())
			enemy.x = x;
	}
}
