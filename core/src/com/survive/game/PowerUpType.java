package com.survive.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static com.survive.game.GameScreen.POWER_UP_TYPE_COUNT;
import static com.survive.game.GameScreen.POWER_UP_TYPE_MIN_SPAWN_INTERVAL;
import static com.survive.game.GameScreen.POWER_UP_TYPE_MAX_SPAWN_INTERVAL;

public class PowerUpType {

    private int type;
    private float timer;
	private int max_spawn_interval;
	private int min_spawn_interval;
    private float spawn_interval;
    private Sprite sprite;
    private Array<PowerUp> power_up_array;

    PowerUpType(int type) {

    	timer = 0;
    	spawn_interval = 0;
		power_up_array = new Array<PowerUp>();
		sprite = new Sprite(new Texture("power_up_" + type + ".bmp"));

		min_spawn_interval = POWER_UP_TYPE_MIN_SPAWN_INTERVAL.get(type);
		max_spawn_interval = POWER_UP_TYPE_MAX_SPAWN_INTERVAL.get(type);

		this.type = type;
    }

    void update(float delta, Player player) {

		if (spawn_interval == 0)
			spawn_interval = (float) Math.random() * (max_spawn_interval - min_spawn_interval) + min_spawn_interval;

		if (power_up_array.size < POWER_UP_TYPE_COUNT.get(type)) {

    		timer += delta;

    		if (timer > spawn_interval) {

				power_up_array.add(new PowerUp(sprite));
				timer = 0;
				spawn_interval = 0;
			}
		}

        for (PowerUp power_up : power_up_array)
        	power_up.update(delta);

		for (PowerUp power_up : power_up_array)
			power_up.playerHitTest(player, power_up_array);
    }

    void render(SpriteBatch batch) {

        for (PowerUp power_up : power_up_array)
        	power_up.render(batch);
    }
}
