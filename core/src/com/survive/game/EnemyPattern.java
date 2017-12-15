package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.survive.game.GameScreen.*;

public class EnemyPattern {

	static final int SPAWN_TIMER = 1;
	private static final int SPAWN_PADDING = 20;
	private static final int NEXT_MIN_PATTERN = 1;
	private static final int NEXT_MAX_PATTERN = 7;

	private static final int PATTERN_3_SPAWN_DISTANCE = 100;

	private int pattern;
	private float x;
	private float y;
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

			case 2:
			case 5:
				x = SPAWN_PADDING;
				break;

			case 7:
				y = SPAWN_PADDING;
				break;
		}
	}

	// Start next enemy pattern randomly
	void nextPattern(float next_timer) {

		int next_pattern = random.nextInt(NEXT_MAX_PATTERN - NEXT_MIN_PATTERN + 1) + NEXT_MIN_PATTERN;
		pattern_array.add(new EnemyPattern(sprite, pattern_array, next_pattern, next_timer));
	}

	// Transfer pattern's enemies to first pattern
	private void chasePlayer() {

		pattern_array.first().enemy_array.addAll(enemy_array);
		enemy_array.clear();
	}

	private void dispose() {

		pattern_array.removeValue(this, true);
	}

	void update(GameScreen screen) {

		switch(pattern) {

			case 0: // Enemies chase player

				for (Enemy enemy : enemy_array)
					enemy.playerChase(screen);

				break;
			case 1: // Spawn 5 enemies in random positions

				timer += screen.delta;

				if (spawned < ENEMY_PATTERN_COUNT.get(pattern)) {

					if (timer > ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern)) {

						Enemy enemy = new Enemy(sprite);
						enemy.x = SPAWN_PADDING + (float) Math.random() * (MAP_WIDTH - SPAWN_PADDING * 2);
						enemy.y = SPAWN_PADDING + (float) Math.random() * (MAP_HEIGHT - SPAWN_PADDING * 2);

						enemy_array.add(enemy);
						spawned ++;
						timer -= ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern);
					}

				} else {

					nextPattern(ENEMY_PATTERN_NEXT_PATTERN.get(pattern));
					chasePlayer();
					dispose();
				}

				break;
			case 2: // Spawn enemies in vertical line (left)

				timer += screen.delta;

				if (spawned < ENEMY_PATTERN_COUNT.get(pattern)) {

					if (timer > ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern)) {

						Enemy enemy = new Enemy(sprite);
						enemy.x = x;
						enemy.y = SPAWN_PADDING + (MAP_HEIGHT - SPAWN_PADDING * 2) / (ENEMY_PATTERN_COUNT.get(pattern) - 1) * spawned;

						enemy_array.add(enemy);
						spawned ++;
						timer -= ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern);

						if (spawned == ENEMY_PATTERN_COUNT.get(pattern)) {

							timer = -SPAWN_TIMER;
							nextPattern(ENEMY_PATTERN_NEXT_PATTERN.get(pattern));
						}
					}

				} else {

					if (timer > 0) {

						x += ENEMY_PATTERN_SPEED.get(pattern) * screen.delta;

						if (x > MAP_WIDTH - sprite.getWidth() / 2) {

							chasePlayer();
							dispose();
						}

						for (Enemy enemy : enemy_array)
							enemy.x = x;
					}
				}

				break;
			case 3: // Spawns enemies in a circle around last player position

				timer += screen.delta;

				if (spawned < ENEMY_PATTERN_COUNT.get(pattern)) {

					if (timer > ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern)) {

						if (this.player_position == null)
							this.player_position = new Vector2(screen.player.x, screen.player.y);

						double theta = Math.PI*2/ENEMY_PATTERN_COUNT.get(pattern) * spawned;

						Enemy enemy = new Enemy(sprite);
						enemy.x = this.player_position.x + (float) Math.sin(theta) * PATTERN_3_SPAWN_DISTANCE;
						enemy.y = this.player_position.y + (float) Math.cos(theta) * PATTERN_3_SPAWN_DISTANCE;
						enemy.theta = Math.PI - theta;
						enemy.speed = ENEMY_PATTERN_SPEED.get(pattern);

						enemy_array.add(enemy);
						spawned ++;
						timer -= ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern);

						if (spawned == ENEMY_PATTERN_COUNT.get(pattern)) {

							timer = -SPAWN_TIMER;
							nextPattern(ENEMY_PATTERN_NEXT_PATTERN.get(pattern));
						}
					}

				} else {

					if (timer > 0) {

						if (timer > PATTERN_3_SPAWN_DISTANCE * 2 / ENEMY_PATTERN_SPEED.get(pattern)) {

							chasePlayer();
							dispose();
						}

						for (Enemy enemy : enemy_array)
							enemy.move(screen.delta);
					}
				}

				break;
			case 4: // Spawn 10 enemies in random positions, chase immediately

				timer += screen.delta;

				if (spawned < ENEMY_PATTERN_COUNT.get(pattern)) {

					if (timer > ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern)) {

						Enemy enemy = new Enemy(sprite);
						enemy.x = SPAWN_PADDING + (float) Math.random() * (MAP_WIDTH - SPAWN_PADDING * 2);
						enemy.y = SPAWN_PADDING + (float) Math.random() * (MAP_HEIGHT - SPAWN_PADDING * 2);

						enemy_array.add(enemy);
						spawned ++;
						timer -= ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern);
						chasePlayer();
					}

				} else {

					nextPattern(ENEMY_PATTERN_NEXT_PATTERN.get(pattern));
					dispose();
				}

				break;
			case 5: // Spawn enemies in vertical line (left), chase immediately

				timer += screen.delta;

				if (spawned < ENEMY_PATTERN_COUNT.get(pattern)) {

					if (timer > ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern)) {

						Enemy enemy = new Enemy(sprite);
						enemy.x = x;
						enemy.y = SPAWN_PADDING + (MAP_HEIGHT - SPAWN_PADDING * 2) / (ENEMY_PATTERN_COUNT.get(pattern) - 1) * spawned;

						enemy_array.add(enemy);
						spawned ++;
						timer -= ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern);
						chasePlayer();
					}

				} else {

					nextPattern(ENEMY_PATTERN_NEXT_PATTERN.get(pattern));
					dispose();
				}

				break;
			case 6: // Spawns enemies in a circle around last player position, chase immediately

				timer += screen.delta;

				if (spawned < ENEMY_PATTERN_COUNT.get(pattern)) {

					if (timer > ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern)) {

						if (this.player_position == null)
							this.player_position = new Vector2(screen.player.x, screen.player.y);

						double theta = Math.PI*2/ENEMY_PATTERN_COUNT.get(pattern) * spawned;

						Enemy enemy = new Enemy(sprite);
						enemy.x = this.player_position.x + (float) Math.sin(theta) * PATTERN_3_SPAWN_DISTANCE;
						enemy.y = this.player_position.y + (float) Math.cos(theta) * PATTERN_3_SPAWN_DISTANCE;
						enemy.theta = Math.PI - theta;
						enemy.speed = ENEMY_PATTERN_SPEED.get(pattern);

						enemy_array.add(enemy);
						spawned ++;
						timer -= ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern);
						chasePlayer();
					}

				} else {

					nextPattern(ENEMY_PATTERN_NEXT_PATTERN.get(pattern));
					dispose();
				}

				break;
			case 7: // Spawn enemies in a horizontal line (bottom)

				timer += screen.delta;

				if (spawned < ENEMY_PATTERN_COUNT.get(pattern)) {

					if (timer > ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern)) {

						Enemy enemy = new Enemy(sprite);
						enemy.x = SPAWN_PADDING + (MAP_WIDTH - SPAWN_PADDING * 2) / (ENEMY_PATTERN_COUNT.get(pattern) - 1) * spawned;
						enemy.y = y;

						enemy_array.add(enemy);
						spawned ++;
						timer -= ENEMY_PATTERN_SPAWN_INTERVAL.get(pattern);

						if (spawned == ENEMY_PATTERN_COUNT.get(pattern)) {

							timer = -SPAWN_TIMER;
							nextPattern(ENEMY_PATTERN_NEXT_PATTERN.get(pattern));
						}
					}

				} else {

					if (timer > 0) {

						y += ENEMY_PATTERN_SPEED.get(pattern) * screen.delta;

						if (y > MAP_HEIGHT - sprite.getWidth() / 2) {

							chasePlayer();
							dispose();
						}

						for (Enemy enemy : enemy_array)
							enemy.y = y;
					}
				}

				break;
		}

		for (Enemy enemy:enemy_array) {

			enemy.update(screen.delta);
			enemy.playerHitTest(screen.player);
			enemy.powerUpHitTest(screen, enemy_array);
		}
	}

	void render(SpriteBatch batch) {

		for (Enemy enemy:enemy_array)
			enemy.render(batch);
	}
}
