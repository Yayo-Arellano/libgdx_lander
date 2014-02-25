package com.tiarsoft.lander.dialogs;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.text.MessageFormat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tiarsoft.lander.Assets;
import com.tiarsoft.lander.MainLander;
import com.tiarsoft.lander.game.GameScreen;
import com.tiarsoft.lander.game.WorldGame;
import com.tiarsoft.lander.screens.MainMenuScreen;
import com.tiarsoft.lander.screens.Screens;

/**
 * Use la clase Window porque le tenia que poner la tachita
 */
public class VentanaGameOver extends Window {
	static public float fadeDuration = 0.4f;

	MainLander game;
	WorldGame oWorld;

	Label lblScore, lbBestScore, lbShareScore, lblNewBestScore;

	Label lblEstadisticas, lblContestados, lblNoContestados, lblTotalProblemas;

	TextButton btMenu, btShareFacebook;

	String bestScore;

	public VentanaGameOver(final MainLander game, final WorldGame oWorld) {
		super("Game over", Assets.styleDialogGameOver);
		this.game = game;
		this.oWorld = oWorld;
		this.setMovable(false);

		this.setWidth(Screens.SCREEN_WIDTH);
		this.setHeight(500);

	}

	public void show(Stage stage) {

		this.pack();
		setWidth(480);
		setHeight(600);
		this.setPosition(Screens.SCREEN_WIDTH / 2f - this.getWidth() / 2f, Screens.SCREEN_HEIGHT / 2f - this.getHeight() / 2f);

		stage.addActor(this);
		if (fadeDuration > 0) {
			getColor().a = 0;
			addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
		}

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
