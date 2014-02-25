package com.tiarsoft.lander;

public class Settings {

	public static int[] arrEstrellasMundo;// Cada posicion es un mundo

	public static void load(int numMapas) {
		arrEstrellasMundo = new int[numMapas];

		for (int i = 0; i < numMapas; i++) {
			arrEstrellasMundo[i] = 0;
		}

	}

	public static void save() {

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
