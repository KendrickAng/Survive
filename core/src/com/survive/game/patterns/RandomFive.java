package com.survive.game.patterns;

import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;
import com.survive.game.EnemyPatterns;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class RandomFive extends EnemyPattern {

	public RandomFive(EnemyPatterns enemy_patterns, float delay) {

		super(enemy_patterns, delay);
		this.SPAWN_COUNT = 5;
		this.SPAWN_INTERVAL = 0.2f;
		this.NEXT_PATTERN_DELAY = 1;
	}

	@Override
	protected void spawn() {

		Enemy enemy = new Enemy(enemy_patterns.sprite);
		enemy.x = SPAWN_PADDING + (float) Math.random() * (MAP_WIDTH - SPAWN_PADDING * 2);
		enemy.y = SPAWN_PADDING + (float) Math.random() * (MAP_HEIGHT - SPAWN_PADDING * 2);
		array.add(enemy);
	}

	@Override
	protected void spawnDone() {}

	@Override
	protected void run() {

		enemy_patterns.addRandomPattern(NEXT_PATTERN_DELAY);
		transfer();
		dispose();
	}
}
