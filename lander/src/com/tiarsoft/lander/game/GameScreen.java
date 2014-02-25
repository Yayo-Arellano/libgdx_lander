package com.tiarsoft.lander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tiarsoft.lander.Assets;
import com.tiarsoft.lander.MainLander;
import com.tiarsoft.lander.Settings;
import com.tiarsoft.lander.dialogs.VentanaGameOver;
import com.tiarsoft.lander.game.objetos.LifeBar;
import com.tiarsoft.lander.screens.MainMenuScreen;
import com.tiarsoft.lander.screens.Screens;

public class GameScreen extends Screens {
	static int STATE_TUTORIAL = 0;
	static int STATE_READY = 1;
	static int STATE_RUNNING = 2;
	static int STATE_PAUSED = 3;
	static int STATE_GAME_OVER = 4;

	final int level;
	WorldGame oWorld;
	WorldGameRenderer renderer;

	float sensibilidad = 3;

	LifeBar lifeBar;
	LifeBar gasBar;
	Table marcoStats;

	VentanaGameOver dialogGameover;

	public GameScreen(MainLander game, int level) {
		super(game);
		this.level = level;
		Assets.cargarMapa(level);
		oWorld = new WorldGame();
		renderer = new WorldGameRenderer(batcher, oWorld);

		dialogGameover = new VentanaGameOver(game, oWorld);

		marcoStats = new Table();
		marcoStats.setSize(172, 98);
		marcoStats.setBackground(Assets.marcoStats);
		marcoStats.setPosition(0, SCREEN_HEIGHT - 99);

		lifeBar = new LifeBar(oWorld.oNave.vida);
		gasBar = new LifeBar(oWorld.oNave.gas);

		marcoStats.add(lifeBar).width(90).height(25).padLeft(35).padBottom(12);
		marcoStats.row();
		marcoStats.add(gasBar).width(90).height(25).padLeft(35).padTop(10);

		stage.addActor(marcoStats);

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

		lifeBar.updateActualLife(oWorld.oNave.vida);
		gasBar.updateActualLife(oWorld.oNave.gas);

		if (Gdx.input.justTouched() && (oWorld.state == WorldGame.STATE_NEXT_LEVEL || oWorld.state == WorldGame.STATE_GAME_OVER)) {
			dialogGameover.show(stage);
			Settings.setStarsFromLevel(level, oWorld.estrellasTomadas);
			// game.setScreen(new GameScreen(game, mundo));

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

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			game.setScreen(new MainMenuScreen(game));
			return true;
		}
		return false;
	}
}
