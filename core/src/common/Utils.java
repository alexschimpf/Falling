package common;

public class Utils {

	private final static float PIXELS_TO_METERS = 100;
	
	public static float convertToMeters(float pixels) {
		return pixels / PIXELS_TO_METERS;
	}
	
	public static float convertToPixels(float meters) {
		return meters * PIXELS_TO_METERS;
	}
	
	public static float getDistance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}
