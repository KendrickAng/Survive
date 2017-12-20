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

public class PowerUp {

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
	private Sprite sprite;

	private int animation_index = -1;
	private float animation_time;
	private Array<Animation<TextureRegion>> animation_array;

	PowerUp() {

		animation_array = new Array<Animation<TextureRegion>>();
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

		if (getPlayer().hit_box.intersectCircle(hit_box))
			nextAnimation();
	}

    void renderIcon() {

		sprite.setRotation((float) Math.toDegrees(rotation));
		sprite.setPosition(x - radius, y - radius);
		sprite.draw(getSpriteBatch());
    }

    void setIcon(Sprite sprite) {

		this.sprite = sprite;
		this.radius = sprite.getWidth()/2;
	}

	void addAnimation(Animation<TextureRegion> animation) { animation_array.add(animation); }
	public int getIndex() { return animation_index; }
	public Animation<TextureRegion> getAnimation() { return animation_array.get(animation_index); }
    public void nextAnimation() { animation_index ++; }
    public float getTimer() { return animation_time; }
	public void addTimer() { animation_time += getDelta(); }
	public void resetTimer() { animation_time = 0; }
    public float getX() { return x; }
    public float getY() { return y; }
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
}
