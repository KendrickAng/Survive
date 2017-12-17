package com.survive.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.survive.game.patterns.*;

public class EnemyPatterns {

	private static final int CHASE_PLAYER = 0;
	private static final int RANDOM_FIVE = 1;
	private static final int VERTICAL_LEFT = 2;
	private static final int CIRCLE_PLAYER = 3;
	private static final int RANDOM_TEN_CHASE = 4;
	private static final int HORIZONTAL_BOTTOM = 5;

	private static final int NEXT_MIN_PATTERN = RANDOM_FIVE;
	private static final int NEXT_MAX_PATTERN = HORIZONTAL_BOTTOM;

	public GameScreen screen;
	Array<EnemyPattern> array;
	public Sprite sprite;

	EnemyPatterns(GameScreen screen) {

		this.screen = screen;
		sprite = new Sprite(new Texture("enemy.bmp"));
		array = new Array<EnemyPattern>();
		addPattern(CHASE_PLAYER);
		addRandomPattern(2);
	}

	void update() {

		for(EnemyPattern enemy_pattern:array) {

			int SPAWN_COUNT = enemy_pattern.SPAWN_COUNT;
			float SPAWN_INTERVAL = enemy_pattern.SPAWN_INTERVAL;
			float RUN_DELAY = enemy_pattern.RUN_DELAY;

			int spawned = enemy_pattern.spawned;
			float timer = enemy_pattern.timer + screen.delta;

			if (spawned < SPAWN_COUNT) {

				if (timer > SPAWN_INTERVAL) {

					enemy_pattern.spawn();

					spawned ++;
					timer -= SPAWN_INTERVAL;

					if (spawned == SPAWN_COUNT) {

						enemy_pattern.spawnDone();
						timer = -RUN_DELAY;
					}
				}

			} else {

				if (timer > 0)
					enemy_pattern.run();
			}

			enemy_pattern.spawned = spawned;
			enemy_pattern.timer = timer;

			for (Enemy enemy:enemy_pattern.array) {

				enemy.update(screen.delta);
				enemy.powerUpHitTest(screen, enemy_pattern.array);
				enemy.playerHitTest(screen.player);
			}
		}
	}

	void render() {

		for (EnemyPattern pattern:array)
			pattern.render(screen.sprite_batch);
	}

	private void addPattern(int pattern) {

		addPattern(pattern, 0);
	}

	// Start a new enemy pattern
	private void addPattern(int pattern, float delay) {

		switch(pattern) {

			case CHASE_PLAYER:
				array.add(new ChasePlayer(this, delay));
				break;
			case RANDOM_FIVE:
				array.add(new RandomFive(this, delay));
				break;
			case VERTICAL_LEFT:
				array.add(new VerticalLeft(this, delay));
				break;
			case CIRCLE_PLAYER:
				array.add(new CirclePlayer(this, delay));
				break;
			case RANDOM_TEN_CHASE:
				array.add(new RandomTenChase(this, delay));
				break;
			case HORIZONTAL_BOTTOM:
				array.add(new HorizontalBottom(this, delay));
				break;
		}
	}

	public void addRandomPattern(float delay) {

		int pattern = MathUtils.random(NEXT_MIN_PATTERN, NEXT_MAX_PATTERN);
		addPattern(pattern, delay);
	}
}
