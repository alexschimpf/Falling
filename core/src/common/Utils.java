package common;

import com.badlogic.gdx.Gdx;

public class Utils {

	public static float toMetersX(float pixels) {
		return (pixels * Globals.VIEWPORT_WIDTH) / Gdx.graphics.getWidth();
	}
	
	public static float toMetersY(float pixels) {
		return (pixels * Globals.VIEWPORT_HEIGHT) / Gdx.graphics.getHeight();
	}
	
	public static float toPixelsX(float meters) {
		return (meters * Gdx.graphics.getWidth()) / Globals.VIEWPORT_WIDTH;
	}
	
	public static float toPixelsY(float meters) {
		return (meters * Gdx.graphics.getHeight()) / Globals.VIEWPORT_HEIGHT;
	}
}
