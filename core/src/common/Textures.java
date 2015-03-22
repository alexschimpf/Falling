package common;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public final class Textures {

	private Texture greenTexture;
	
	private static Textures instance;

	private Textures() {
		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pix.setColor(0, 1, 0, 0.3f);
		pix.fill();
		greenTexture = new Texture(pix);
	}

	public static Textures getInstance() {
		if (instance == null) {
			instance = new Textures();
		}

		return instance;
	}
	
	public Texture getGreenTexture() {
		return greenTexture;
	}
}
