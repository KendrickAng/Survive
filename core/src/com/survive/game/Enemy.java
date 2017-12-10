package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

	private static final int PLAYER_CHASE_SPEED = 80;
	float x;
	float y;
	private Sprite sprite;

	Enemy(Sprite sprite, float x, float y) {

		this.sprite = sprite;
		this.set(x, y);
	}

	void set(float x, float y) {

		this.x = x;
		this.y = y;
	}

	void move(float delta, double theta, float speed) {

		x -= Math.sin(theta) * speed * delta;
		y += Math.cos(theta) * speed * delta;
	}

	void player_chase(float delta, Vector2 player_position) {

		// Chase player
		double theta = Math.atan2(x - player_position.x, player_position.y - y);
		move(delta, theta, PLAYER_CHASE_SPEED);
	}

	void render(SpriteBatch batch) {

		sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
		sprite.draw(batch);
	}
}
