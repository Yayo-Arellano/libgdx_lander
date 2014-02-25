package com.tiarsoft.lander.game.objetos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Laser {

	public static int DIRECCION_HORIZONTAL = 0;
	public static int DIRECCION_VERTICAL = 1;

	float TIME_TO_FIRE = 5;
	float TIME_FIRING = 3;

	public static int STATE_NORMAL = 0;
	public static int STATE_FIRE = 1;

	public Vector2 position;

	public float timeToFire;
	public float timeFiring;
	public float stateTime;
	public int state;
	public int direccion;

	public Laser(float x, float y, String direccion) {
		position = new Vector2(x, y);
		stateTime = timeToFire = timeFiring = 0;
		state = STATE_NORMAL;
		if (direccion.equals("horizontal"))
			this.direccion = DIRECCION_HORIZONTAL;
		else if (direccion.equals("vertical"))
			this.direccion = DIRECCION_VERTICAL;

	}

	public void update(float delta, Body body) {
		position.x = body.getPosition().x;
		position.y = body.getPosition().y;

		if (state == STATE_NORMAL) {
			timeToFire += delta;
			if (timeToFire >= TIME_TO_FIRE) {
				state = STATE_FIRE;
				timeToFire -= TIME_TO_FIRE;
				stateTime = 0;
			}
		}

		if (state == STATE_FIRE) {
			timeFiring += delta;
			if (timeFiring >= TIME_FIRING) {
				state = STATE_NORMAL;
				timeFiring -= TIME_FIRING;
				stateTime = 0;
			}
		}

		stateTime += delta;

	}
}
