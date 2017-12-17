package com.survive.game.patterns;

import com.badlogic.gdx.math.Vector2;
import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;
import com.survive.game.EnemyPatterns;

import static com.survive.game.Enemy.SPAWN_TIMER;

public class CirclePlayer extends EnemyPattern {

	private static final int SPAWN_DISTANCE = 100;
	private Vector2 player_position;

	public CirclePlayer(EnemyPatterns enemy_patterns, float delay) {

		super(enemy_patterns, delay);
		this.SPAWN_COUNT = 8;
		this.SPAWN_INTERVAL = 0.1f;
		this.NEXT_PATTERN_DELAY = 2;
		this.RUN_DELAY = SPAWN_TIMER;
		this.ENEMY_SPEED = 50;
	}

	@Override
	protected void spawn() {

		if (player_position == null)
			player_position = new Vector2(enemy_patterns.screen.player.x, enemy_patterns.screen.player.y);

		double theta = Math.PI*2/SPAWN_COUNT * spawned;

		Enemy enemy = new Enemy(enemy_patterns.sprite);
		enemy.x = this.player_position.x + (float) Math.sin(theta) * SPAWN_DISTANCE;
		enemy.y = this.player_position.y + (float) Math.cos(theta) * SPAWN_DISTANCE;
		enemy.theta = Math.PI - theta;
		enemy.speed = ENEMY_SPEED;
		array.add(enemy);
	}

	@Override
	protected void spawnDone() { enemy_patterns.addRandomPattern(NEXT_PATTERN_DELAY); }

	@Override
	protected void run() {

		if (timer > SPAWN_DISTANCE * 2 / ENEMY_SPEED) {

			transfer();
			dispose();
		}

		for (Enemy enemy:array)
			enemy.move(enemy_patterns.screen.delta);
	}
}
