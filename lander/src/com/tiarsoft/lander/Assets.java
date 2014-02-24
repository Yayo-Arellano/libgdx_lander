package com.tiarsoft.lander;

import java.util.LinkedHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {

	public static boolean isDebug = true;
	public static AtlasRegion nave;
	public static Animation naveFly;
	public static Animation explosion;

	public static AtlasRegion fondo;

	public static AtlasRegion gas;
	public static AtlasRegion estrella;
	public static AtlasRegion bomba;

	public static BitmapFont font;
	public static LinkedHashMap<Integer, String> mundos;

	public static TiledMap map;

	public static void cargar() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/atlasMap.txt"));

		nave = atlas.findRegion("nave");

		AtlasRegion an1 = atlas.findRegion("nave1");
		AtlasRegion an2 = atlas.findRegion("nave2");
		AtlasRegion an3 = atlas.findRegion("nave3");
		AtlasRegion an4 = atlas.findRegion("nave4");
		AtlasRegion an5 = atlas.findRegion("nave5");
		naveFly = new Animation(.15f, an1, an2, an3, an4, an5);

		fondo = atlas.findRegion("fondo");

		AtlasRegion newExpl1 = atlas.findRegion("newExplosion1");
		AtlasRegion newExpl2 = atlas.findRegion("newExplosion2");
		AtlasRegion newExpl3 = atlas.findRegion("newExplosion3");
		AtlasRegion newExpl4 = atlas.findRegion("newExplosion4");
		AtlasRegion newExpl5 = atlas.findRegion("newExplosion5");
		AtlasRegion newExpl6 = atlas.findRegion("newExplosion6");
		AtlasRegion newExpl7 = atlas.findRegion("newExplosion7");
		AtlasRegion newExpl8 = atlas.findRegion("newExplosion8");
		AtlasRegion newExpl9 = atlas.findRegion("newExplosion9");
		AtlasRegion newExpl10 = atlas.findRegion("newExplosion10");
		AtlasRegion newExpl11 = atlas.findRegion("newExplosion11");
		AtlasRegion newExpl12 = atlas.findRegion("newExplosion12");
		AtlasRegion newExpl13 = atlas.findRegion("newExplosion13");
		AtlasRegion newExpl14 = atlas.findRegion("newExplosion14");
		AtlasRegion newExpl15 = atlas.findRegion("newExplosion15");
		AtlasRegion newExpl16 = atlas.findRegion("newExplosion16");
		AtlasRegion newExpl17 = atlas.findRegion("newExplosion17");
		AtlasRegion newExpl18 = atlas.findRegion("newExplosion18");
		AtlasRegion newExpl19 = atlas.findRegion("newExplosion19");
		explosion = new Animation(0.05f, newExpl1, newExpl2, newExpl3, newExpl4, newExpl5, newExpl6, newExpl7, newExpl8, newExpl9, newExpl10, newExpl11, newExpl12, newExpl13, newExpl14, newExpl15, newExpl16, newExpl17, newExpl18, newExpl19);

		gas = atlas.findRegion("gas");
		estrella = atlas.findRegion("estrella");
		bomba = atlas.findRegion("bomba");

		font = new BitmapFont();

		mundos = new LinkedHashMap<Integer, String>();
		mundos.put(0, "data/mundos/mundo01.tmx");
		mundos.put(1, "data/mundos/mundo02.tmx");
		mundos.put(2, "data/mundos/mundo03.tmx");
		mundos.put(3, "data/mundos/mundo04.tmx");

	}

	public static void cargarMapa(int numeroMundo) {
		if (map != null) {
			map.dispose();
			map = null;
		}
		if (isDebug)
			map = new TmxMapLoader().load(mundos.get(numeroMundo - 1));
		else
			// map=new AtlasTmxMapLoader().load(mundos[numeroMundo - 1]);
			map = new TmxMapLoader().load(mundos.get(numeroMundo - 1));
	}
}
