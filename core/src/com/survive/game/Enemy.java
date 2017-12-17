package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class Enemy {

	public static final int SPAWN_TIMER = 1;
	private static final float BLINK_TIMER = 0.1f;
	private static final int PLAYER_CHASE_SPEED = 80;

	public float x;
	public float y;
	public float speed;
	public double theta;

	private float radius;
	private boolean blink;
	private boolean spawned;
	private float blink_timer;
	private float spawn_timer;
	private Sprite sprite;
	private Circle hit_box;

	public Enemy(Sprite sprite) {

		radius = sprite.getWidth()/2;
		hit_box = new Circle(0, 0, 0);
		this.sprite = sprite;
	}

	// Add displacement per unit time in x and y direction
	public void move(float delta) {

		if (spawned) {

			x -= Math.sin(theta) * speed * delta;
			y += Math.cos(theta) * speed * delta;
		}
	}

	public void chasePlayer(GameScreen screen) {

		// Chase player
		theta = Math.atan2(x - screen.player.x, screen.player.y - y);
		speed = PLAYER_CHASE_SPEED;
		move(screen.delta);
	}

	void update(float delta) {

		// Keep enemy within screen
		if (x < radius)
			x = radius;

		if (x > MAP_WIDTH - radius)
			x = MAP_WIDTH - radius;

		if (y < radius)
			y = radius;

		if (y > MAP_HEIGHT - radius)
			y = MAP_HEIGHT - radius;

		hit_box.set(x, y, radius);

		if (!spawned) {

			blink_timer += delta;
			spawn_timer += delta;

			if (blink_timer > BLINK_TIMER) {

				blink = !blink;
				blink_timer -= BLINK_TIMER;
			}

			if (spawn_timer > SPAWN_TIMER)
				spawned = true;
		}
	}

	void playerHitTest(Player player) {

		if (spawned && player.hit_box.intersectCircle(hit_box))
			player.dead = true;
	}

	void powerUpHitTest(GameScreen screen, Array<Enemy> enemy_array) {

		for (PowerUpType power_up_type:screen.power_up_types)
			for (PowerUp power_up:power_up_type.power_up_array)
				if (power_up.triggered && power_up.hit_box.intersectCircle(hit_box)) {

					screen.player.kills ++;
					enemy_array.removeValue(this, true);
				}
	}

	void render(SpriteBatch batch) {

		sprite.setAlpha(0.1f);

		if (blink)
			sprite.setAlpha(0.8f);

		if (spawned)
			sprite.setAlpha(1);

		sprite.setPosition(x - radius, y - radius);
		sprite.draw(batch);
	}
}
