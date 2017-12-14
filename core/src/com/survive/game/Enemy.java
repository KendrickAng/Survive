package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class Enemy {

	private static final int PLAYER_CHASE_SPEED = 80;

	float x;
	float y;
	float speed;
	double theta;
	private float radius;
	private Sprite sprite;
	private Circle hit_box;

	Enemy(Sprite sprite) {

		radius = sprite.getWidth()/2;
		hit_box = new Circle(0, 0, 0);
		this.sprite = sprite;
	}

	// Add displacement per unit time in x and y direction
	void move(float delta) {

		x -= Math.sin(theta) * speed * delta;
		y += Math.cos(theta) * speed * delta;
	}

	void playerChase(float delta, Player player) {

		// Chase player
		theta = Math.atan2(x - player.x, player.y - y);
		speed = PLAYER_CHASE_SPEED;
		move(delta);
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
	}

	void playerHitTest(Player player, Array<Enemy> enemy_array) {

		if (player.hit_box.intersectCircle(hit_box))
			enemy_array.removeValue(this, true);
	}

	void powerUpHitTest(Array<PowerUpType> power_up_types, Array<Enemy> enemy_array) {

		for (PowerUpType power_up_type:power_up_types)
			for (PowerUp power_up:power_up_type.power_up_array)
				if (power_up.triggered && power_up.hit_box.intersectCircle(hit_box))
					enemy_array.removeValue(this, true);
	}

	void render(SpriteBatch batch) {

		sprite.setPosition(x - radius, y - radius);
		sprite.draw(batch);
	}
}
