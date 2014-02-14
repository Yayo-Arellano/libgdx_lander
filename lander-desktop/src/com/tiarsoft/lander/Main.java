package com.tiarsoft.lander;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "lander";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 800;
		//
//		Settings set = new Settings();
//		set.maxHeight = 2048;
//		set.maxWidth = 2048;
//		set.filterMag = TextureFilter.Linear;
//		set.filterMin = TextureFilter.Linear;
//		set.flattenPaths = true;
//		set.combineSubdirectories = true;

		// TexturePacker2.process(set, "/Users/Yayo/Pictures/Games/Lander/Images", "/Users/Yayo/Documents/eclipseAndroid/Lander/lander-android/assets/data/", "atlasMap.txt");

		new LwjglApplication(new MainLander(), cfg);
	}
}
