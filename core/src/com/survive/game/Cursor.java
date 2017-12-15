package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Cursor {

	private float radius;
	private Sprite sprite;

	Vector2 position;

	Cursor(Sprite sprite) {

		this.sprite = sprite;
		radius = sprite.getWidth()/2;
		position = new Vector2(0, 0);
	}

	void update(Survive game) {

		position.set(Gdx.input.getX(), Gdx.input.getY());
		game.viewport.unproject(position);
	}

	void render(SpriteBatch sprite_batch) {

		sprite.setPosition(position.x - radius, position.y - radius);
		sprite.draw(sprite_batch);
	}
}
