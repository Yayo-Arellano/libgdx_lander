package com.tiarsoft.lander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.tiarsoft.lander.Assets;
import com.tiarsoft.lander.MainLander;
import com.tiarsoft.lander.game.objetos.Nave;
import com.tiarsoft.lander.screens.Screens;

public class GameScreen extends Screens {
	static int STATE_TUTORIAL = 0;
	static int STATE_READY = 1;
	static int STATE_RUNNING = 2;
	static int STATE_PAUSED = 3;
	static int STATE_GAME_OVER = 4;

	int mundo;
	WorldGame oWorld;
	WorldGameRenderer renderer;

	float sensibilidad = 3;

	public GameScreen(MainLander game, int mundo) {
		super(game);
		this.mundo = mundo;
		Assets.cargarMapa(mundo);
		oWorld = new WorldGame();
		renderer = new WorldGameRenderer(batcher, oWorld);
	}

	@Override
	public void update(float delta) {

		float accelX = 0, accelY = 0;

		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
			accelX = Gdx.input.getAccelerometerX() / sensibilidad * -1;
		}

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
			accelX = -1;
		else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
			accelX = 1;

		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE))
			accelY = 1;

		oWorld.update(delta, accelY, accelX);

		if (Gdx.input.justTouched() && (oWorld.state == WorldGame.STATE_NEXT_LEVEL || oWorld.state == WorldGame.STATE_GAME_OVER)) {

			if (oWorld.state == WorldGame.STATE_NEXT_LEVEL)
				mundo++;
			if (mundo > Assets.mundos.size())
				mundo = 1;
			game.setScreen(new GameScreen(game, mundo));

		}
	}

	@Override
	public void draw(float delta) {
		renderer.render(delta);
		oCam.update();
		batcher.setProjectionMatrix(oCam.combined);
		batcher.begin();
		// Assets.font.draw(batcher, "Velocidad " + oWorld.oNave.velocidadResultante, 10, 60);
		// Assets.font.draw(batcher, "Velocidad X " + oWorld.oNave.velocity.x, 10, 40);
		// Assets.font.draw(batcher, "Velocidad Y " + oWorld.oNave.velocity.y, 10, 20);
		Assets.font.draw(batcher, "Estrellas  " + oWorld.estrellasTomadas, 300, 60);
		Assets.font.draw(batcher, "Vida  " + oWorld.oNave.vida, 300, 40);
		Assets.font.draw(batcher, "Gas  " + oWorld.oNave.gas, 300, 20);
		batcher.end();

	}

}
