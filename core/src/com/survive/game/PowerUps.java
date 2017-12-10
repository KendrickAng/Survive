package com.survive.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class PowerUps {

    private static final double SPAWN_RATE = 0.2;
    private static final int MAX_SIZE = 16;

    private Array<PowerUp> powerup_list;

    PowerUps() {

        // Initialise array
		// TODO: Change to PowerUpType class
        powerup_list = new Array<PowerUp>();
    }

    public void update(float delta) {

        if (powerup_list.size < MAX_SIZE && MathUtils.random() < delta * SPAWN_RATE) {

            PowerUp new_item = new PowerUp(1);
            powerup_list.add(new_item);
        }

        for (PowerUp power_up : powerup_list)
        	power_up.update(delta);
    }

    public void render(SpriteBatch batch) {

        for (PowerUp power_up : powerup_list)
        	power_up.render(batch);
    }

    public Array<PowerUp> getPowerUps() {

        return powerup_list;
    }
}
