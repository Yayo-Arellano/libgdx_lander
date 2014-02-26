package com.tiarsoft.lander.dialogs;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
public class VentanaPaused extends Window {
	static public float fadeDuration = 0.4f;

	MainLander game;
	WorldGame oWorld;

	Label lblLevelActual;
	Image[] estrellas;

	ImageButton btMenu, btResume, btTryAgain;

	public VentanaPaused(final MainLander game, final WorldGame oWorld, final int levelActual) {
		super("", Assets.styleDialogGameOver);
		this.game = game;
		this.oWorld = oWorld;
		this.setMovable(false);

		Label paused = new Label("Paused", Assets.styleLabelMediana);
		lblLevelActual = new Label("Level " + (levelActual + 1), Assets.styleLabelMediana);

		estrellas = new Image[3];
		Table starTable = new Table();
		starTable.defaults().pad(15);
		if (oWorld.estrellasTomadas >= 0) {
			for (int star = 0; star < 3; star++) {
				// Todas son grises la primera vez
				estrellas[star] = new Image(Assets.bomba);
				starTable.add(estrellas[star]).width(50).height(50);
			}
		}

		btMenu = new ImageButton(Assets.StyleImageButtonPause);
		btMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new LevelScreen(game));
			}
		});

		btResume = new ImageButton(Assets.StyleImageButtonPause);
		btResume.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide();
				resumeGame();
			}
		});

		btTryAgain = new ImageButton(Assets.StyleImageButtonPause);
		btTryAgain.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, levelActual));
			}
		});

		this.row().padTop(30);
		this.add(paused).colspan(3);

		this.row().padTop(30);
		this.add(lblLevelActual).colspan(3);

		this.row().padTop(30);
		this.add(starTable).colspan(3);

		this.row().padTop(30).expandX();
		this.add(btMenu);
		this.add(btResume);
		this.add(btTryAgain);

		this.row().expandX();
		this.add(new Label("Menu", Assets.styleLabelMediana));
		this.add(new Label("Resume", Assets.styleLabelMediana));
		this.add(new Label("Try Again", Assets.styleLabelMediana));

	}

	public void show(Stage stage) {

		/*
		 * Se reemplazan las estrellas grises por las tomadas =)
		 */
		for (int i = 0; i < oWorld.estrellasTomadas; i++) {
			estrellas[i].setDrawable(new TextureRegionDrawable(Assets.estrella));
		}

		this.pack();
		setWidth(Screens.SCREEN_WIDTH);
		this.setPosition(Screens.SCREEN_WIDTH / 2f - this.getWidth() / 2f, Screens.SCREEN_HEIGHT / 2f - this.getHeight() / 2f);

		stage.addActor(this);
		if (fadeDuration > 0) {
			getColor().a = 0;
			addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
		}

	}

	public void resumeGame() {
		hide();
		if (game.getScreen() instanceof GameScreen)
			GameScreen.state = GameScreen.STATE_RUNNING;
	}

	public void hide() {
		if (fadeDuration > 0) {
			addCaptureListener(ignoreTouchDown);
			addAction(sequence(fadeOut(fadeDuration, Interpolation.fade), Actions.removeListener(ignoreTouchDown, true), Actions.removeActor()));
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
