package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import static com.survive.game.Screen.getDelta;

public abstract class PowerUpType {

	protected float MIN_INTERVAL;
	protected float MAX_INTERVAL;
	protected int COUNT;

    private float timer;
	private float spawn_interval;
    private Sprite icon;

	private Array<PowerUp> power_up_array;
	private Array<Animation<TextureRegion>> animation_array;

	protected PowerUpType() {

		power_up_array = new Array<PowerUp>();
		animation_array = new Array<Animation<TextureRegion>>();
	}

    void update() {

		if (power_up_array.size < COUNT) {

    		timer += getDelta();

    		if (timer > spawn_interval) {

    			PowerUp power_up = new PowerUp();
    			power_up.setIcon(icon);

				for (Animation<TextureRegion> animation:animation_array)
					power_up.addAnimation(animation);

				power_up_array.add(power_up);
				spawn_interval = (float) Math.random() * (MAX_INTERVAL - MIN_INTERVAL) + MIN_INTERVAL;
				timer = 0;
			}
		}

        for (PowerUp power_up:power_up_array) {

			int index = power_up.getIndex();

			if (index == -1)
				power_up.updateIcon();

			if (index >= 0)
				updateAnimation(power_up);
		}
    }

    void render() {

        for (PowerUp power_up:power_up_array) {

			int index = power_up.getIndex();

			if (index == -1)
				power_up.renderIcon();

			if (index >= 0)
				renderAnimation(power_up);
		}
    }

    protected abstract void updateAnimation(PowerUp power_up);
	protected abstract void renderAnimation(PowerUp power_up);

	protected void setIcon(String string) { this.icon = new Sprite(new Texture(string)); }
	protected void addAnimation(String string) {

		TextureAtlas texture_atlas = new TextureAtlas(Gdx.files.internal(string));
		Animation<TextureRegion> animation = new Animation<TextureRegion>(0.033f, texture_atlas.getRegions());
		animation_array.add(animation);
	}
	protected void dispose(PowerUp power_up) { power_up_array.removeValue(power_up, true); }
}
