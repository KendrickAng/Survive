package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class Enemy {

	private static final int PLAYER_CHASE_SPEED = 80;

	float x;
	float y;
	float speed;
	double theta;
	float width;
	float height;
	private Sprite sprite;

	Enemy(Sprite sprite) {

		width = sprite.getWidth();
		height = sprite.getHeight();
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

	void update() {

		if (x < width/2)
			x = width/2;

		if (x > MAP_WIDTH - width/2)
			x = MAP_WIDTH - width/2;

		if (y < height/2)
			y = height/2;

		if (y > MAP_HEIGHT - height/2)
			y = MAP_HEIGHT - height/2;
	}

	void render(SpriteBatch batch) {

		sprite.setPosition(x - width/2, y - height/2);
		sprite.draw(batch);
	}
}
