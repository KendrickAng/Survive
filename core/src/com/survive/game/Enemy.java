package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import static com.survive.game.GameScreen.*;

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
	public void move() {

		if (spawned) {

			x -= Math.sin(theta) * speed * getDelta();
			y += Math.cos(theta) * speed * getDelta();
		}
	}

	public void chasePlayer() {

		// Chase player
		theta = Math.atan2(x - getPlayer().x, getPlayer().y - y);
		speed = PLAYER_CHASE_SPEED;
		move();
	}

	void update() {

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

			blink_timer += getDelta();
			spawn_timer += getDelta();

			if (blink_timer > BLINK_TIMER) {

				blink = !blink;
				blink_timer -= BLINK_TIMER;
			}

			if (spawn_timer > SPAWN_TIMER)
				spawned = true;
		}
	}

	void playerHitTest() {

		if (spawned && getPlayer().hit_box.intersectCircle(hit_box))
			getPlayer().dead = true;
	}

	void powerUpHitTest(Array<Enemy> enemy_array) {

		for (PowerUpType power_up_type:getPowerUpTypes())
			for (PowerUp power_up:power_up_type.power_up_array)
				if (power_up.triggered && power_up.hit_box.intersectCircle(hit_box)) {

					getPlayer().kills ++;
					enemy_array.removeValue(this, true);
				}
	}

	void render() {

		sprite.setAlpha(0.1f);

		if (blink)
			sprite.setAlpha(0.8f);

		if (spawned)
			sprite.setAlpha(1);

		sprite.setPosition(x - radius, y - radius);
		sprite.draw(Survive.getSpriteBatch());
	}
}
