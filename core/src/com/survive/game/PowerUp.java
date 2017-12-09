package com.survive.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.Survive.GAME_HEIGHT;
import static com.survive.game.Survive.GAME_WIDTH;

public class PowerUp {

    private float x;
    private float y;
    private Sprite sprite;

    PowerUp(int type) {

        x = (float) Math.random() * GAME_WIDTH;
        y = (float) Math.random() * GAME_HEIGHT;

        sprite = new Sprite(new Texture("power_up_" + type + ".bmp"));
    }

    public void render(SpriteBatch batch) {

        sprite.setPosition(x, y);
        sprite.draw(batch);
    }
}
