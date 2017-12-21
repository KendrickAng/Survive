package com.survive.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import static com.survive.game.GameScreen.MAP_HEIGHT;
import static com.survive.game.GameScreen.MAP_WIDTH;
import static com.survive.game.GameScreen.getPlayer;
import static com.survive.game.Screen.getDelta;
import static com.survive.game.Survive.getSpriteBatch;

public abstract class PowerUp {

	private static final float SPEED = 10;
    private static final float ROTATION_SPEED = 1;
    private static final float SPAWN_PADDING = 50;

    private float x;
    private float y;
    private float radius;
    private double rotation;
    private double rotation_speed;
    private double x_speed;
    private double y_speed;
	private Circle hit_box;
	private Sprite icon;
	private  PowerUpType type;

	private int animation_index = -1;
	private float animation_time;
	private Array<Animation<TextureRegion>> animation_array;

	protected PowerUp(PowerUpType type) {

		this.type = type;
		icon = type.getIcon();
		radius = icon.getWidth()/2;
		animation_array = type.getAnimation();
		hit_box = new Circle();

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
    }

    void updateIcon() {

		x -= x_speed * getDelta();
		y += y_speed * getDelta();
		rotation += rotation_speed * getDelta();

		// Bounce on map boundaries
		if (x < radius || x > MAP_WIDTH - radius)
			x_speed = -x_speed;

		else if (y < radius || y > MAP_HEIGHT - radius)
			y_speed = -y_speed;

		hit_box.set(x, y, radius);

		if (getPlayer().getHitBox().intersectCircle(hit_box))
			nextAnimation();
	}

    void renderIcon() {

		icon.setRotation((float) Math.toDegrees(rotation));
		icon.setPosition(x - radius, y - radius);
		icon.draw(getSpriteBatch());
    }

	protected void dispose() { this.type.dispose(this); }

	protected abstract void updateAnimation();
	protected abstract void renderAnimation();

	protected int getIndex() { return animation_index; }
	protected Animation<TextureRegion> getAnimation() { return animation_array.get(animation_index); }
    protected void nextAnimation() { animation_index ++; }
    protected float getTimer() { return animation_time; }
	protected void addTimer() { animation_time += getDelta(); }
	protected void resetTimer() { animation_time = 0; }
    protected float getX() { return x; }
    protected float getY() { return y; }
	protected void setX(float x) { this.x = x; }
	protected void setY(float y) { this.y = y; }
}
