package com.survive.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public abstract class EnemyPattern {

	protected static final int SPAWN_PADDING = 20;

	protected int SPAWN_COUNT;
	protected int ENEMY_SPEED;
	protected float SPAWN_INTERVAL;
	protected float NEXT_PATTERN_DELAY;
	protected float RUN_DELAY;

	protected int spawned;
	protected float timer;
	protected EnemyPatterns enemy_patterns;
	protected Array<Enemy> array;

	public EnemyPattern(EnemyPatterns enemy_patterns, float delay) {

		this.enemy_patterns = enemy_patterns;
		this.timer = -delay;
		array = new Array<Enemy>();
	}

	// Transfer enemies to CHASE_PLAYER pattern
	protected void transfer() {

		enemy_patterns.array.first().array.addAll(array);
		array.clear();
	}

	protected void dispose() {

		enemy_patterns.array.removeValue(this, true);
	}

	protected abstract void spawn();
	protected abstract void spawnDone();
	protected abstract void run();

	void render(SpriteBatch batch) {

		for (Enemy enemy:array)
			enemy.render(batch);
	}
}
