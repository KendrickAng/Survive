package com.survive.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PowerUps {

    private static final int SPAWN_RATE = 10;
    private static final int MAX_SIZE = 16;

    Array<PowerUp> powerup_list;
    Viewport viewport;

    public PowerUps(Viewport viewport) {

        this.viewport = viewport;
        init();
    }

    public void init() {

        // Initialise array
        powerup_list = new Array<PowerUp>();
    }

    public void update(float delta) {

        if(powerup_list.size < MAX_SIZE && MathUtils.random() < delta * SPAWN_RATE) {

            // Spawn new powerup
            Vector2 new_item_position = new Vector2(
                    MathUtils.random() * viewport.getWorldWidth(),
                    MathUtils.random() * viewport.getWorldHeight()
            );

            PowerUp new_item = new PowerUp(new_item_position);
            powerup_list.add(new_item);
        }
    }

    public void render(SpriteBatch batch) {

        for(PowerUp power_up : powerup_list) {
            power_up.render(batch);
        }
    }
}
