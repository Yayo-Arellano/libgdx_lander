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
public class VentanaShop extends Window {
	static public float fadeDuration = 0.4f;

	final int NIVEL_MAX_UPGRADES = 15;

	public static int nivelVida = 0;
	public static int nivelGas = 0;
	public static int nivelVelocidad = 0;

	MainLander game;
	WorldGame oWorld;

	Image[] arrVida;
	Image[] arrVelocidad;
	Image[] arrGas;

	ImageButton btUpVida, btUpVelocidad, btUpGas;

	ImageButton btMenu;

	public VentanaShop(final MainLander game) {
		super("", Assets.styleDialogGameOver);
		this.game = game;
		this.setMovable(false);

		arrVida = new Image[NIVEL_MAX_UPGRADES];
		arrVelocidad = new Image[NIVEL_MAX_UPGRADES];
		arrGas = new Image[NIVEL_MAX_UPGRADES];

		initBotonesMenu();
		initBotonesUpgrades();

		Label upgrades = new Label("Upgrades", Assets.styleLabelMediana);

		this.row().padTop(30);
		this.add(upgrades).colspan(4);

		// Velocidad
		Table auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < NIVEL_MAX_UPGRADES; i++) {
			arrVelocidad[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrVelocidad[i]).width(14).height(18);
		}
		this.row().padTop(30);
		this.add(new Label("Thrusters", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpVelocidad);
		this.add(new Label("$100", Assets.styleLabelMediana));

		// Vida
		auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < NIVEL_MAX_UPGRADES; i++) {
			arrVida[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrVida[i]).width(14).height(18);
		}
		this.row().padTop(30);
		this.add(new Label("Life", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpVida);
		this.add(new Label("$100", Assets.styleLabelMediana));

		// Gas
		auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < NIVEL_MAX_UPGRADES; i++) {
			arrGas[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrGas[i]).width(14).height(18);
		}
		this.row().padTop(30);
		this.add(new Label("Gas", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpGas);
		this.add(new Label("$100", Assets.styleLabelMediana));

		this.row().padTop(30).expandX();
		this.add(btMenu).colspan(4);

		this.row().expandX();
		this.add(new Label("Menu", Assets.styleLabelMediana)).colspan(4);

		setArrVidas();
		setArrVelocidad();
		setArrGas();

	}

	private void initBotonesUpgrades() {

		btUpVelocidad = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpVelocidad.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				nivelVelocidad++;
				setArrVelocidad();

			}
		});

		btUpVida = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpVida.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				nivelVida++;
				setArrVidas();

			}
		});

		btUpGas = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpGas.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				nivelGas++;
				setArrGas();

			}
		});

	}

	private void initBotonesMenu() {
		btMenu = new ImageButton(Assets.styleImageButtonPause);
		btMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide();
			}
		});
	}

	private void setArrVelocidad() {
		for (int i = 0; i < nivelVelocidad; i++) {
			arrVelocidad[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (nivelVelocidad >= NIVEL_MAX_UPGRADES) {
			btUpVelocidad.setVisible(false);
		}

	}

	private void setArrVidas() {
		for (int i = 0; i < nivelVida; i++) {
			arrVida[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (nivelVida >= NIVEL_MAX_UPGRADES) {
			btUpVida.setVisible(false);
		}

	}

	private void setArrGas() {
		for (int i = 0; i < nivelGas; i++) {
			arrGas[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (nivelGas >= NIVEL_MAX_UPGRADES) {
			btUpGas.setVisible(false);
		}

	}

	public void show(Stage stage) {

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

	public void hide() {
		if (fadeDuration > 0) {
			addCaptureListener(ignoreTouchDown);

			SizeToAction sizeAction = Actions.action(SizeToAction.class);
			sizeAction.setSize(Screens.SCREEN_WIDTH, 0);// ALTURA FINAL
			sizeAction.setDuration(.25f);
			addAction(sequence(Actions.parallel(fadeOut(fadeDuration, Interpolation.fade), sizeAction), Actions.removeListener(ignoreTouchDown, true), Actions.removeActor()));
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
