package entity;

import entity.floating.FloatingEntity;
import entity.floating.NeutralEntity;

public final class EntityFactory {

	/**
	 * Returns a random floating entity.
	 * The level uses this method and supplies a width and height to try.
	 * However, certain entities have a fixed size and cannot use the given width and height.
	 */
	public static FloatingEntity getRandomFloatingEntity(float x, float y, float tryWidth, float tryHeight) {
		return new NeutralEntity(x, y, tryWidth, tryHeight);
	}
}