package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class Enemy {

	private static final int PLAYER_COLLISION_RADIUS = 5;
	private static final int PLAYER_CHASE_SPEED = 80;

	float x;
	float y;
	float speed;
	double theta;
	private Sprite sprite;

	Enemy(Sprite sprite) {

		this.sprite = sprite;
	}

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

	boolean playerCollision(Player player) {

		float offset_x = x - player.x;
		float offset_y = y - player.y;
		double distance = Math.sqrt(Math.pow(offset_x, 2) + Math.pow(offset_y, 2));
		return distance < sprite.getWidth()/2 + PLAYER_COLLISION_RADIUS;
	}

	void update() {

		if (x < sprite.getWidth()/2)
			x = sprite.getWidth()/2;

		if (x > MAP_WIDTH - sprite.getWidth()/2)
			x = MAP_WIDTH - sprite.getWidth()/2;

		if (y < sprite.getHeight()/2)
			y = sprite.getHeight()/2;

		if (y > MAP_HEIGHT - sprite.getHeight()/2)
			y = MAP_HEIGHT - sprite.getHeight()/2;
	}

	void render(SpriteBatch batch) {

		sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
		sprite.draw(batch);
	}
}
