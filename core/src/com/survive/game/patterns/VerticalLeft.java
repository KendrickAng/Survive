package com.survive.game.patterns;

import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;
import com.survive.game.EnemyPatterns;

import static com.survive.game.Enemy.SPAWN_TIMER;
import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class VerticalLeft extends EnemyPattern {

	private float x = SPAWN_PADDING;

	public VerticalLeft(EnemyPatterns enemy_patterns, float delay) {

		super(enemy_patterns, delay);
		this.SPAWN_COUNT = 8;
		this.SPAWN_INTERVAL = 0.1f;
		this.NEXT_PATTERN_DELAY = 2;
		this.RUN_DELAY = SPAWN_TIMER;
		this.ENEMY_SPEED = 50;
	}

	@Override
	protected void spawn() {

		Enemy enemy = new Enemy(enemy_patterns.sprite);
		enemy.x = x;
		enemy.y = SPAWN_PADDING + (MAP_HEIGHT - SPAWN_PADDING * 2) / (SPAWN_COUNT - 1) * spawned;
		array.add(enemy);
	}

	@Override
	protected void spawnDone() { enemy_patterns.addRandomPattern(NEXT_PATTERN_DELAY); }

	@Override
	protected void run() {

		x += ENEMY_SPEED * enemy_patterns.screen.delta;

		if (x > MAP_WIDTH - enemy_patterns.sprite.getWidth() / 2) {

			transfer();
			dispose();
		}

		for (Enemy enemy : array)
			enemy.x = x;
	}
}
