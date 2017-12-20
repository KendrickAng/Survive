package com.survive.game;

import com.badlogic.gdx.utils.Array;

import static com.survive.game.EnemyPatternController.getEnemyPatterns;
import static com.survive.game.Screen.getDelta;

public abstract class EnemyPattern {

	protected static final int SPAWN_PADDING = 20;

	protected int SPAWN_COUNT;
	protected int ENEMY_SPEED;
	protected float SPAWN_INTERVAL;
	protected float NEXT_PATTERN_DELAY;
	protected float RUN_DELAY;

	protected int spawned;
	protected float timer;
	private Array<Enemy> array;

	protected EnemyPattern(float delay) {

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

	void update() {

		timer += getDelta();

		if (spawned < SPAWN_COUNT) {

			if (timer > SPAWN_INTERVAL) {

				spawn();

				spawned ++;
				timer -= SPAWN_INTERVAL;

				if (spawned == SPAWN_COUNT) {

					spawnDone();
					timer = -RUN_DELAY;
				}
			}

		} else {

			if (timer > 0)
				run();
		}

		for (Enemy enemy:array) {

			enemy.update();
			enemy.playerHitTest();
		}
	}

	void render() {

		for (Enemy enemy:array)
			enemy.render();
	}

	public Array<Enemy> getArray() { return array; }
	public void disposeEnemy(Enemy enemy) { array.removeValue(enemy, true);	}
}
