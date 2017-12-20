package com.survive.game.patterns;

import com.badlogic.gdx.math.Vector2;
import com.survive.game.Enemy;
import com.survive.game.EnemyPattern;

import static com.survive.game.Enemy.SPAWN_TIMER;
import static com.survive.game.EnemyPatternController.addRandomPattern;
import static com.survive.game.EnemyPatternController.getSprite;
import static com.survive.game.GameScreen.getPlayer;

public class CirclePlayer extends EnemyPattern {

	private static final int SPAWN_DISTANCE = 100;
	private Vector2 player_position;

	public CirclePlayer(float delay) {

		super(delay);
		this.SPAWN_COUNT = 8;
		this.SPAWN_INTERVAL = 0.1f;
		this.NEXT_PATTERN_DELAY = 2;
		this.RUN_DELAY = SPAWN_TIMER;
		this.ENEMY_SPEED = 50;
	}

	@Override
	protected void spawn() {

		if (player_position == null)
			player_position = new Vector2(getPlayer().getX(), getPlayer().getY());

		double theta = Math.PI*2/SPAWN_COUNT * spawned;

		Enemy enemy = new Enemy(getSprite());
		enemy.x = this.player_position.x + (float) Math.sin(theta) * SPAWN_DISTANCE;
		enemy.y = this.player_position.y + (float) Math.cos(theta) * SPAWN_DISTANCE;
		enemy.theta = Math.PI - theta;
		enemy.speed = ENEMY_SPEED;
		getArray().add(enemy);
	}

	@Override
	protected void spawnDone() { addRandomPattern(NEXT_PATTERN_DELAY); }

	@Override
	protected void run() {

		if (timer > SPAWN_DISTANCE * 2 / ENEMY_SPEED) {

			transfer();
			dispose();
		}

		for (Enemy enemy:getArray())
			enemy.move();
	}
}
