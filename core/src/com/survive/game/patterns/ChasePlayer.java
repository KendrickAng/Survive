package com.survive.game.patterns;

import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;

public class ChasePlayer extends EnemyPattern {

	public ChasePlayer(float delay) {

		super(delay);
	}

	@Override
	public void spawn() {}

	@Override
	public void spawnDone() {}

	@Override
	public void run() {

		for (Enemy enemy:getArray())
			enemy.chasePlayer();
	}
}
