package com.tiarsoft.lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

	public static int nivelVida = 0;
	public static int nivelGas = 0;
	public static int nivelRotacion = 0;
	public static int nivelVelocidadY = 0;
	public static int nivelPower = 0;
	public static int nivelOtro1 = 0;
	private final static Preferences pref = Gdx.app.getPreferences("com.tiar.aaa.aaa.aa");

	public static int[] arrEstrellasMundo;// Cada posicion es un mundo

	public static void load(int numMapas) {
		arrEstrellasMundo = new int[numMapas];

		pref.clear();
		pref.flush();
		for (int i = 0; i < numMapas; i++) {
			arrEstrellasMundo[i] = pref.getInteger("arrEstrellasMundo" + i, 0);
		}

		// Upgrades
		nivelVida = pref.getInteger("nivelVida", 0);
		nivelGas = pref.getInteger("nivelGas", 0);
		nivelRotacion = pref.getInteger("nivelRotacion", 0);
		nivelVelocidadY = pref.getInteger("nivelVelocidadY", 0);
		nivelPower = pref.getInteger("nivelPower", 0);
		nivelOtro1 = pref.getInteger("nivelOtro1", 0);
		// Fin upgrades

	}

	public static void save() {

		for (int i = 0; i < arrEstrellasMundo.length; i++) {
			pref.putInteger("arrEstrellasMundo" + i, arrEstrellasMundo[i]);
		}

		// Upgrades
		pref.putInteger("nivelVida", nivelVida);
		pref.putInteger("nivelGas", nivelGas);
		pref.putInteger("nivelRotacion", nivelRotacion);
		pref.putInteger("nivelVelocidadY", nivelVelocidadY);
		pref.putInteger("nivelPower", nivelPower);
		pref.putInteger("nivelOtro1", nivelOtro1);
		// Fin upgrades

		pref.flush();
	}

	public static int getStarsFromLevel(int level) {

		if (level >= arrEstrellasMundo.length)
			return 0;
		return arrEstrellasMundo[level];
	}

	public static void setStarsFromLevel(int level, int numStars) {
		int startActuales = arrEstrellasMundo[level];

		if (startActuales < numStars) {
			arrEstrellasMundo[level] = numStars;
			save();
		}
	}
}
