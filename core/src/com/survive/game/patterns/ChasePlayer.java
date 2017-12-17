package com.survive.game.patterns;

import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;
import com.survive.game.EnemyPatterns;

public class ChasePlayer extends EnemyPattern {

	public ChasePlayer(EnemyPatterns enemy_patterns, float delay) {

		super(enemy_patterns, delay);
	}

	@Override
	public void spawn() {}

	@Override
	public void spawnDone() {}

	@Override
	public void run() {

		for (Enemy enemy:this.array)
			enemy.chasePlayer(this.enemy_patterns.screen);
	}
}
