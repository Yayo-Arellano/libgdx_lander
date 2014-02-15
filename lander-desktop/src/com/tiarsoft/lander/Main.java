package com.tiarsoft.lander;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "lander";
		cfg.useGL20 = false;
		cfg.width = 240;
		cfg.height = 400;
		//
		Settings set = new Settings();
		set.maxHeight = 2048;
		set.maxWidth = 2048;
		set.filterMag = TextureFilter.Linear;
		set.filterMin = TextureFilter.Linear;
		set.flattenPaths = true;
		set.combineSubdirectories = true;

//		TexturePacker2.process(set, "/Users/Yayo/Pictures/Games/Lander/Images", "/Users/Yayo/Documents/eclipseAndroid/Lander-P/lander-android/assets/data/", "atlasMap.txt");

		new LwjglApplication(new MainLander(), cfg);
	}
}
