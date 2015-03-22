package common;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public final class Textures {

	private TextureRegion redTexture, lightRedTexture, blueTexture, greenTexture, yellowTexture;
	private TextureRegion starTexture;
	private TextureRegion planetTexture;
	private TextureRegion rockTexture;
	private HashMap<String, TextureRegion> colorTextureMap = new HashMap<String, TextureRegion>();
	
	private static Textures instance;

	private Textures() {
		redTexture = buildColorTexture(1, 0, 0, 0.3f, "red");
		lightRedTexture = buildColorTexture(1, 0, 0, 0.15f, "lightRed");
		blueTexture = buildColorTexture(0, 0, 1, 0.3f, "blue");
		greenTexture = buildColorTexture(0, 1, 0, 0.3f, "green");
		yellowTexture = buildColorTexture(1, 1, 0, 0.3f, "yellow");
		starTexture = buildTextureRegion("star.png");
		planetTexture = buildTextureRegion("planet.png");
		rockTexture = buildTextureRegion("space_rock.jpg");
	}

	public static Textures getInstance() {
		if (instance == null) {
			instance = new Textures();
		}

		return instance;
	}
	
	public TextureRegion getColorTexture(String color) {		
		return colorTextureMap.get(color);
	}
	
	public TextureRegion getRandomNeutralTexture() {
		TextureRegion texture = null;
		while(texture == null || texture.equals(redTexture) || texture.equals(lightRedTexture)) {
			int choice = MathUtils.random(0, colorTextureMap.size() - 1);
			texture = (TextureRegion)colorTextureMap.values().toArray()[choice];
		}
		
		return texture;
	}
	
	public TextureRegion getPlanetTexture() {
		return planetTexture;
	}
	
	public TextureRegion getStarTexture() {
		return starTexture;
	}
	
	public TextureRegion getRockTexture() {
		return rockTexture;
	}
	
	private TextureRegion buildTextureRegion(String filename) {
		TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal(filename)));
		textureRegion.flip(false, true);
		return textureRegion;
	}
	
	private TextureRegion buildColorTexture(float r, float g, float b, float a, String key) {
		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pix.setColor(r, g, b, a);
		pix.fill();
		TextureRegion texture = new TextureRegion(new Texture(pix));
		colorTextureMap.put(key, texture);
		return texture;
	}
}
