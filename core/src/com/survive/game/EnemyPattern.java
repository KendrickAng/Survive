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
	private static final int NEXT_MAX_PATTERN = 4;

	private static final int PATTERN_2_ENEMIES = 5;
	private static final float PATTERN_2_SPAWN_INTERVAL = 0.2f;

	private static final int PATTERN_3_ENEMIES = 8;
	private static final float PATTERN_3_SPAWN_INTERVAL = 0.1f;
	private static final int PATTERN_3_SPEED = 50;

	private static final int PATTERN_4_ENEMIES = 8;
	private static final float PATTERN_4_SPAWN_INTERVAL = 0.1f;
	private static final int PATTERN_4_SPAWN_DISTANCE = 100;
	private static final int PATTERN_4_SPEED = 50;

	int pattern;
	private float x;
	private float timer;
	private int spawned = 0;

	private Sprite sprite;
	private Array<EnemyPattern> pattern_array;
	private Array<Enemy> enemy_array;
	private Vector2 player_position;

	EnemyPattern(Sprite sprite, Array<EnemyPattern> pattern_array, int pattern, float delay) {

		this.sprite = sprite;
		this.pattern_array = pattern_array;
		this.pattern = pattern;
		this.timer = -delay;
		enemy_array = new Array<Enemy>();

		switch (pattern) {

			case 3:
				x = SPAWN_PADDING;
				break;
		}
	}

	// Finds next enemy pattern randomly (pattern 2 to 4)
	void next_pattern(float next_timer) {

		int next_pattern = random.nextInt(NEXT_MAX_PATTERN - NEXT_MIN_PATTERN + 1) + NEXT_MIN_PATTERN;
		pattern_array.add(new EnemyPattern(sprite, pattern_array, next_pattern, next_timer));
	}

	// Remove CURRENT pattern from array, add THAT pattern's enemies to pattern1
	private void transfer_enemy() {

		pattern_array.removeValue(this, true);
		pattern_array.first().enemy_array.addAll(enemy_array);
	}

	// Enemies chase player
	void pattern1(float delta, Player player) {

		for (Enemy enemy:enemy_array)
			enemy.playerChase(delta, player);
	}

	// Spawn 5 enemies in random positions
	void pattern2(float delta) {

		if (spawned < PATTERN_2_ENEMIES) {

			timer += delta;

			if (timer > PATTERN_2_SPAWN_INTERVAL) {

				Enemy enemy = new Enemy(sprite);
				enemy.x = SPAWN_PADDING + (float) Math.random() * (MAP_WIDTH - SPAWN_PADDING * 2);
				enemy.y = SPAWN_PADDING + (float) Math.random() * (MAP_HEIGHT - SPAWN_PADDING * 2);

				enemy_array.add(enemy);
				spawned ++;
				timer -= PATTERN_2_SPAWN_INTERVAL;
			}
		} else {

			// Move enemies to pattern1 and get new pattern
			transfer_enemy();
			next_pattern(1);
		}
	}

	// Spawn enemies in vertical line
	void pattern3(float delta) {

		if (spawned < PATTERN_3_ENEMIES) {

			timer += delta;

			if (timer > PATTERN_3_SPAWN_INTERVAL) {

				Enemy enemy = new Enemy(sprite);
				enemy.x = x;
				enemy.y = SPAWN_PADDING + (MAP_HEIGHT - SPAWN_PADDING * 2) / (PATTERN_3_ENEMIES - 1) * spawned;

				enemy_array.add(enemy);
				spawned ++;
				timer -= PATTERN_3_SPAWN_INTERVAL;

				if (spawned == PATTERN_3_ENEMIES)
					next_pattern(2);
			}

		} else {

			x += PATTERN_3_SPEED * delta;

			if (x > MAP_WIDTH - sprite.getWidth()/2)
				transfer_enemy();

			for (Enemy enemy:enemy_array)
				enemy.x = x;
		}
	}

	// Spawns enemies in a circle around last player position
	void pattern4(float delta, Player player) {

		timer += delta;

		if (spawned < PATTERN_4_ENEMIES) {

			if (timer > PATTERN_4_SPAWN_INTERVAL) {

				if (this.player_position == null)
					this.player_position = new Vector2(player.x, player.y);

				double theta = Math.PI*2/PATTERN_4_ENEMIES * spawned;

				Enemy enemy = new Enemy(sprite);
				enemy.x = this.player_position.x + (float) Math.sin(theta) * PATTERN_4_SPAWN_DISTANCE;
				enemy.y = this.player_position.y + (float) Math.cos(theta) * PATTERN_4_SPAWN_DISTANCE;
				enemy.theta = Math.PI - theta;
				enemy.speed = PATTERN_4_SPEED;

				enemy_array.add(enemy);
				spawned ++;
				timer -= PATTERN_4_SPAWN_INTERVAL;

				if (spawned == PATTERN_4_ENEMIES) {

					timer = -0.5f;
					next_pattern(2);
				}
			}

		} else {

			if (timer > 0) {

				if (timer > PATTERN_4_SPAWN_DISTANCE * 2 / PATTERN_4_SPEED)
					transfer_enemy();

				for (Enemy enemy : enemy_array)
					enemy.move(delta);
			}
		}
	}

	void update() {

		for (Enemy enemy:enemy_array)
			enemy.update();
	}

	void playerCollision(Player player) {

		for (Enemy enemy:enemy_array) {

			if (player.hitbox.get(0).intersectCircle(enemy.x, enemy.y, enemy.height/2)) {

				enemy_array.removeValue(enemy, true);
			}
		}
	}

	void render(SpriteBatch batch) {

		for (Enemy enemy:enemy_array)
			enemy.render(batch);
	}
}
