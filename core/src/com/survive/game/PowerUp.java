package com.survive.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PowerUp {

    Vector2 position;

    Texture texture = new Texture("power_up_temp.jpg");
    Sprite sprite = new Sprite(texture);

    public PowerUp(Vector2 position) {

        this.position = position;
    }

    public void render(SpriteBatch batch) {

        batch.draw(sprite, position.x, position.y);
    }
}
