package com.tiarsoft.lander.dialogs;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tiarsoft.lander.Assets;
import com.tiarsoft.lander.MainLander;
import com.tiarsoft.lander.game.GameScreen;
import com.tiarsoft.lander.game.WorldGame;
import com.tiarsoft.lander.screens.LevelScreen;
import com.tiarsoft.lander.screens.Screens;

/**
 * Use la clase Window porque le tenia que poner la tachita
 */
public class VentanaCompleted extends Window {
	static public float fadeDuration = 0.4f;

	MainLander game;
	WorldGame oWorld;

	Label lblLevelActual;
	Image[] estrellas;

	ImageButton btMenu, btTryAgain;
	final int levelActual;

	public VentanaCompleted(final MainLander game, final WorldGame oWorld, final int levelActual) {
		super("", Assets.styleDialogGameOver);
		this.game = game;
		this.oWorld = oWorld;
		this.setMovable(false);
		this.levelActual = levelActual;

		Label paused = new Label("Gameover", Assets.styleLabelMediana);
		lblLevelActual = new Label("Level " + (levelActual + 1), Assets.styleLabelMediana);

		estrellas = new Image[3];
		Table starTable = new Table();
		starTable.defaults().pad(15);
		if (oWorld.estrellasTomadas >= 0) {
			for (int star = 0; star < 3; star++) {
				// Todas son grises la primera vez
				estrellas[star] = new Image(Assets.starOff);
				starTable.add(estrellas[star]).width(50).height(50);
			}
		}

		btMenu = new ImageButton(Assets.styleImageButtonPause);
		btMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide(LevelScreen.class);
			}
		});

		btTryAgain = new ImageButton(Assets.styleImageButtonPause);
		btTryAgain.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide(GameScreen.class);
			}
		});

		this.row().padTop(30);
		this.add(paused).colspan(2);

		this.row().padTop(30);
		this.add(lblLevelActual).colspan(2);

		this.row().padTop(30);
		this.add(starTable).colspan(2);

		this.row().padTop(30).expandX();
		this.add(btMenu);

		this.add(btTryAgain);

		this.row().expandX();
		this.add(new Label("Menu", Assets.styleLabelMediana));
		this.add(new Label("Try Again", Assets.styleLabelMediana));

	}

	public void show(Stage stage) {

		/*
		 * Se reemplazan las estrellas grises por las tomadas =)
		 */
		for (int i = 0; i < oWorld.estrellasTomadas; i++) {
			estrellas[i].setDrawable(new TextureRegionDrawable(Assets.star));
		}

		this.pack();
		setSize(Screens.SCREEN_WIDTH, 0);

		SizeToAction sizeAction = Actions.action(SizeToAction.class);
		sizeAction.setSize(Screens.SCREEN_WIDTH, 500);// ALTURA FINAL
		sizeAction.setDuration(.25f);

		setPosition(Screens.SCREEN_WIDTH / 2f - getWidth() / 2f, Screens.SCREEN_HEIGHT / 2f - 500 / 2f);// 500 ALTURA FINAL
		addAction(sizeAction);

		stage.addActor(this);
		if (fadeDuration > 0) {
			getColor().a = 0;
			addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
		}

	}

	public void resumeGame() {
		hide(null);
		if (game.getScreen() instanceof GameScreen)
			GameScreen.state = GameScreen.STATE_RUNNING;
	}

	public void hide(final Class<?> newScreen) {
		if (fadeDuration > 0) {
			addCaptureListener(ignoreTouchDown);

			SizeToAction sizeAction = Actions.action(SizeToAction.class);
			sizeAction.setSize(Screens.SCREEN_WIDTH, 0);// ALTURA FINAL
			sizeAction.setDuration(.25f);

			RunnableAction run = Actions.run(new Runnable() {
				@Override
				public void run() {
					if (newScreen == LevelScreen.class) {
						game.setScreen(new LevelScreen(game));
					}
					else if (newScreen == GameScreen.class) {
						game.setScreen(new GameScreen(game, levelActual));
					}
				}
			});

			addAction(sequence(Actions.parallel(fadeOut(fadeDuration, Interpolation.fade), sizeAction), Actions.removeListener(ignoreTouchDown, true), run, Actions.removeActor()));
		}
		else
			remove();

	}

	InputListener ignoreTouchDown = new InputListener() {
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			event.cancel();
			return false;
		}
	};
}
