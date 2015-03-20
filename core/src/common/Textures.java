package common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public final class Textures {

	private Texture solidGreenTexture;
	
	private static Textures instance;

	private Textures() {
		Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pix.setColor(Color.GREEN);
		pix.fill();
		solidGreenTexture = new Texture(pix);
	}

	public static Textures getInstance() {
		if (instance == null) {
			instance = new Textures();
		}

		return instance;
	}
	
	public Texture getSolidGreenTexture() {
		return solidGreenTexture;
	}
}
