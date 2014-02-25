package com.tiarsoft.lander.game.objetos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Laser {

	public static int DIRECCION_HORIZONTAL = 0;
	public static int DIRECCION_VERTICAL = 1;

	public static float DRAW_ANCHO = .35f;

	float TIME_TO_FIRE = 5;
	public float timeToFire;

	float TIME_FIRING = 3;
	public float timeFiring;

	public static int STATE_NORMAL = 0;
	public static int STATE_FIRE = 1;

	public Vector2 position;

	public float stateTime;
	public int state;
	public int direccion;

	public final float width, height;

	public Laser(float x, float y, float width, float height, String direccion) {
		position = new Vector2(x, y);
		stateTime = timeToFire = timeFiring = 0;
		state = STATE_NORMAL;

		if (height > width)
			width = DRAW_ANCHO;
		else
			height = DRAW_ANCHO;

		this.width = width;
		this.height = height;

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
