package com.survive.game;

import com.badlogic.gdx.utils.Array;

import static com.survive.game.EnemyPatternController.getEnemyPatterns;

public abstract class EnemyPattern {

	protected static final int SPAWN_PADDING = 20;

	protected int SPAWN_COUNT;
	protected int ENEMY_SPEED;
	protected float SPAWN_INTERVAL;
	protected float NEXT_PATTERN_DELAY;
	protected float RUN_DELAY;

	protected int spawned;
	protected float timer;
	protected Array<Enemy> array;

	public EnemyPattern(float delay) {

		this.timer = -delay;
		array = new Array<Enemy>();
	}

	// Transfer enemies to CHASE_PLAYER pattern
	protected void transfer() {

		getEnemyPatterns().first().array.addAll(array);
		array.clear();
	}

	protected void dispose() {

		getEnemyPatterns().removeValue(this, true);
	}

	protected abstract void spawn();
	protected abstract void spawnDone();
	protected abstract void run();

	void render() {

		for (Enemy enemy:array)
			enemy.render();
	}
}
