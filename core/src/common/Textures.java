package common;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public final class Textures {

	private Texture blueTexture;
	
	private static Textures instance;

	private Textures() {
		blueTexture = buildColorTexture(0, 0, 1, 0.3f);
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
	
	private Texture buildColorTexture(float r, float g, float b, float a) {
		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pix.setColor(r, g, b, a);
		pix.fill();
		return new Texture(pix);
	}
}
