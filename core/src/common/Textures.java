package common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class Textures {

	private Texture blueTexture;
	private TextureRegion planetTexture;
	private Texture starTexture;
	
	private static Textures instance;

	private Textures() {
		blueTexture = buildColorTexture(0, 0, 1, 0.3f);
		starTexture = new Texture(Gdx.files.internal("star.png"));
		planetTexture = new TextureRegion(new Texture(Gdx.files.internal("planet.png")));
		planetTexture.flip(false, true);
	}

	public static Textures getInstance() {
		if (instance == null) {
			instance = new Textures();
		}

		return instance;
	}
	
	public Texture getBlueTexture() {
		return blueTexture;
	}
	
	public TextureRegion getPlanetTexture() {
		return planetTexture;
	}
	
	public Texture getStarTexture() {
		return starTexture;
	}
	
	private Texture buildColorTexture(float r, float g, float b, float a) {
		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pix.setColor(r, g, b, a);
		pix.fill();
		return new Texture(pix);
	}
}
