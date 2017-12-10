package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class EnemyPattern {

	private static final int SPAWN_PADDING = 20;
	private static final int NEXT_MIN_PATTERN = 2;
	private static final int NEXT_MAX_PATTERN = 3;

	private static final int PATTERN_2_ENEMIES = 5;
	private static final float PATTERN_2_SPAWN_INTERVAL = 0.2f;
	private static final int PATTERN_3_ENEMIES = 10;
	private static final float PATTERN_3_SPAWN_INTERVAL = 0.2f;
	private static final int PATTERN_3_SPEED = 50;

	int pattern;
	private float x;
	private float y;
	private float timer;
	private int spawned = 0;

	private Sprite sprite;
	private Array<EnemyPattern> pattern_array;
	private Array<Enemy> enemy_array;

	EnemyPattern(Sprite sprite, Array<EnemyPattern> pattern_array, int pattern, float delay) {

		this.sprite = sprite;
		this.pattern_array = pattern_array;
		this.pattern = pattern;
		this.timer = -delay;
		enemy_array = new Array<Enemy>();
	}

	void next_pattern(float next_timer) {

		int next_pattern = random.nextInt(NEXT_MAX_PATTERN - NEXT_MIN_PATTERN + 1) + NEXT_MIN_PATTERN;
		pattern_array.add(new EnemyPattern(sprite, pattern_array, next_pattern, next_timer));
	}

	private void transfer_enemy() {

		pattern_array.removeValue(this, true);
		pattern_array.first().enemy_array.addAll(enemy_array);
	}

	void pattern1(float delta, Vector2 player_position) {

		for (Enemy enemy:enemy_array)
			enemy.player_chase(delta, player_position);
	}

	void pattern2(float delta) {

		if (spawned < PATTERN_2_ENEMIES) {

			timer += delta;

			if (timer > PATTERN_2_SPAWN_INTERVAL) {

				x = SPAWN_PADDING + (float) Math.random() * (MAP_WIDTH - SPAWN_PADDING * 2);
				y = SPAWN_PADDING + (float) Math.random() * (MAP_HEIGHT - SPAWN_PADDING * 2);
				enemy_array.add(new Enemy(sprite, x, y));
				spawned ++;
				timer -= PATTERN_2_SPAWN_INTERVAL;
			}

		} else {

			transfer_enemy();
			next_pattern(1);
		}
	}

	void pattern3(float delta) {

		if (spawned < PATTERN_3_ENEMIES) {

			x = SPAWN_PADDING;

			timer += delta;

			if (timer > PATTERN_3_SPAWN_INTERVAL) {

				y = SPAWN_PADDING + (MAP_HEIGHT - SPAWN_PADDING * 2) / (PATTERN_3_ENEMIES - 1) * spawned;

				enemy_array.add(new Enemy(sprite, x, y));
				spawned ++;
				timer -= PATTERN_3_SPAWN_INTERVAL;

				if (spawned == PATTERN_3_ENEMIES)
					next_pattern(2);
			}

		} else {

			x += PATTERN_3_SPEED * delta;

			if (x > MAP_WIDTH - sprite.getWidth()/2) {

				transfer_enemy();
			}

			for (Enemy enemy:enemy_array)
				enemy.set(x, enemy.y);
		}
	}

	void render(SpriteBatch batch) {

		for (Enemy enemy:enemy_array)
			enemy.render(batch);
	}
}
