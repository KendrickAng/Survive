package com.survive.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

	private static final float SPEED = 50;
	private float x;
	private float y;
	private Sprite sprite;

	Enemy(int x, int y) {

		this.x = x;
		this.y = y;
		sprite = new Sprite(new Texture("enemy.bmp"));
	}

	void update(float delta, Vector2 player_position) {

		// Chase player
		double theta = Math.atan2(x - player_position.x, player_position.y - y);
		x -= Math.sin(theta) * SPEED * delta;
		y += Math.cos(theta) * SPEED * delta;
		sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
	}

	void render(SpriteBatch batch) {

		sprite.draw(batch);
	}
}
