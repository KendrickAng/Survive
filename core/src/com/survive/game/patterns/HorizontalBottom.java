package com.survive.game.patterns;

import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;
import com.survive.game.EnemyPatterns;

import static com.survive.game.Enemy.SPAWN_TIMER;
import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class HorizontalBottom extends EnemyPattern {

	private float y = SPAWN_PADDING;

	public HorizontalBottom(EnemyPatterns enemy_patterns, float delay) {

		super(enemy_patterns, delay);
		this.SPAWN_COUNT = 12;
		this.SPAWN_INTERVAL = 0.1f;
		this.ENEMY_SPEED = 50;
		this.NEXT_PATTERN_DELAY = 2;
		this.RUN_DELAY = SPAWN_TIMER;
	}

	@Override
	protected void spawn() {

		Enemy enemy = new Enemy(enemy_patterns.sprite);
		enemy.x = SPAWN_PADDING + (MAP_WIDTH - SPAWN_PADDING * 2) / (SPAWN_COUNT - 1) * spawned;
		enemy.y = y;
		array.add(enemy);
	}

	@Override
	protected void spawnDone() { enemy_patterns.addRandomPattern(NEXT_PATTERN_DELAY); }

	@Override
	protected void run() {

		y += ENEMY_SPEED * enemy_patterns.screen.delta;

		if (y > MAP_HEIGHT - enemy_patterns.sprite.getWidth() / 2) {

			transfer();
			dispose();
		}

		for (Enemy enemy:array)
			enemy.y = y;
	}
}
