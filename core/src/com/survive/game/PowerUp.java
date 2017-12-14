package com.survive.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;

public class PowerUp {

    private static final float SPEED = 10;
    private static final float ROTATION_SPEED = 1;
    private static final float SPAWN_PADDING = 50;

    private float x;
    private float y;
    private double rotation;
    private double rotation_speed;
    private double x_speed;
    private double y_speed;
    private boolean collected;
    private boolean triggered;
    private Sprite sprite;

    PowerUp(int type) {

        // Load texture based on type
        sprite = new Sprite(new Texture("power_up_" + type + ".bmp"));

        // Spawn within map boundaries
        x = SPAWN_PADDING + (float) Math.random() * (MAP_WIDTH - SPAWN_PADDING * 2);
        y = SPAWN_PADDING + (float) Math.random() * (MAP_HEIGHT - SPAWN_PADDING * 2);

        // Init Speed & Rotation
        double theta = Math.random() * 2 * Math.PI;
        double speed = Math.random() * SPEED;

        rotation = theta;
        rotation_speed = Math.random() * ROTATION_SPEED;

        x_speed = Math.sin(theta) * speed;
        y_speed = Math.cos(theta) * speed;

        collected = false;
    }

    public void update(float delta) {

        x -= x_speed * delta;
        y += y_speed * delta;
        rotation += rotation_speed * delta;

        // Bounce on map boundaries
        if (x < sprite.getWidth()/2 || x > MAP_WIDTH - sprite.getWidth()/2)
            x_speed = -x_speed;

        else if (y < sprite.getHeight()/2 || y > MAP_HEIGHT - sprite.getHeight()/2)
            y_speed = -y_speed;

        // Render
        sprite.setRotation((float) Math.toDegrees(rotation));
        sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
    }

    public void render(SpriteBatch batch)  {

        if (collected) return;

        sprite.draw(batch);
    }

    public float getX() {return x;}

    public float getY() {return y;}

    public Sprite getSprite() {return sprite;}

    public void setCollected(boolean b) {collected = b;}

    public boolean getCollected() {return collected;}

    public void setTriggered(boolean b) { triggered = b; }

    public boolean getTriggered() {return triggered;}
}
