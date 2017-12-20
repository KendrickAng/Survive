package com.survive.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.survive.game.patterns.*;

public class EnemyPatternController {

	private static final int CHASE_PLAYER = 0;
	private static final int RANDOM_FIVE = 1;
	private static final int VERTICAL_LEFT = 2;
	private static final int CIRCLE_PLAYER = 3;
	private static final int RANDOM_TEN_CHASE = 4;
	private static final int HORIZONTAL_BOTTOM = 5;

	private static final int NEXT_MIN_PATTERN = RANDOM_FIVE;
	private static final int NEXT_MAX_PATTERN = HORIZONTAL_BOTTOM;

	private static Sprite sprite;
	private static Array<EnemyPattern> array;

	EnemyPatternController() {

		sprite = new Sprite(new Texture("enemy.bmp"));
		array = new Array<EnemyPattern>();
		addPattern(CHASE_PLAYER);
		addRandomPattern(2);
	}

	void update() {

		for(EnemyPattern enemy_pattern:array)
			enemy_pattern.update();
	}

	void render() {

		for (EnemyPattern pattern:array)
			pattern.render();
	}

	private static void addPattern(int pattern) {

		addPattern(pattern, 0);
	}

	// Start a new enemy pattern
	private static void addPattern(int choice, float delay) {

		EnemyPattern pattern = null;

		switch(choice) {

			case CHASE_PLAYER:
				pattern = new ChasePlayer(delay);
				break;
			case RANDOM_FIVE:
				pattern = new RandomFive(delay);
				break;
			case VERTICAL_LEFT:
				pattern = new VerticalLeft(delay);
				break;
			case CIRCLE_PLAYER:
				pattern = new CirclePlayer(delay);
				break;
			case RANDOM_TEN_CHASE:
				pattern = new RandomTenChase(delay);
				break;
			case HORIZONTAL_BOTTOM:
				pattern = new HorizontalBottom(delay);
				break;
		}

		array.add(pattern);
	}

	public static void addRandomPattern(float delay) {

		int pattern = MathUtils.random(NEXT_MIN_PATTERN, NEXT_MAX_PATTERN);
		addPattern(pattern, delay);
	}

	public static Sprite getSprite() { return sprite; }
	public static Array<EnemyPattern> getEnemyPatterns() { return array; }
}
