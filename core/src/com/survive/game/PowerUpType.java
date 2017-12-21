package com.survive.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.survive.game.powerups.Explosion;
import com.survive.game.powerups.Shield;

import static com.survive.game.PowerUpTypeController.EXPLOSION_POWER_UP;
import static com.survive.game.PowerUpTypeController.SHIELD_POWER_UP;
import static com.survive.game.Screen.getDelta;

public class PowerUpType {

	protected float MIN_INTERVAL;
	protected float MAX_INTERVAL;
	protected int COUNT;
	protected int TYPE;

    private float timer;
	private float spawn_interval;
    private Sprite icon;

	private Array<PowerUp> power_up_array;
	private Array<Animation<TextureRegion>> animation_array;

	public PowerUpType() {

		power_up_array = new Array<PowerUp>();
		animation_array = new Array<Animation<TextureRegion>>();
	}

    void update() {

		if (power_up_array.size < COUNT) {

    		timer += getDelta();

    		if (timer > spawn_interval) {

    			PowerUp power_up = null;

				switch (TYPE) {

					case EXPLOSION_POWER_UP:
						power_up = new Explosion(this);
						break;

					case SHIELD_POWER_UP:
						power_up = new Shield(this);
						break;
				}

				if (power_up != null)
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
				power_up.updateAnimation();
		}
    }

    void render() {

        for (PowerUp power_up:power_up_array) {

			int index = power_up.getIndex();

			if (index == -1)
				power_up.renderIcon();

			if (index >= 0)
				power_up.renderAnimation();
		}
    }

    protected void setDelay(float delay) { this.timer = -delay; }
	protected void setIcon(String string) { this.icon = new Sprite(new Texture(string)); }
	protected Sprite getIcon() { return icon; }
	protected void addAnimation(String string) {

		TextureAtlas texture_atlas = new TextureAtlas(Gdx.files.internal(string));
		Animation<TextureRegion> animation = new Animation<TextureRegion>(0.033f, texture_atlas.getRegions());
		animation_array.add(animation);
	}
	protected Array<Animation<TextureRegion>> getAnimation() { return animation_array; }
	protected void dispose(PowerUp power_up) { power_up_array.removeValue(power_up, true); }
}
