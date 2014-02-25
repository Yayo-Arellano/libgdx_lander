package com.tiarsoft.lander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tiarsoft.lander.game.GameScreen;
import com.tiarsoft.lander.screens.LevelScreen;
import com.tiarsoft.lander.screens.MainMenuScreen;

public class MainLander extends Game {

	public Stage stage;
	public SpriteBatch batcher;

	@Override
	public void create() {
		Assets.cargar();
		stage = new Stage();
		batcher = new SpriteBatch();

		setScreen(new MainMenuScreen(this));
	}

}