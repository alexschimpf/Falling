package entity;

import com.badlogic.gdx.math.MathUtils;

import entity.floating.FatalEntity;
import entity.floating.FloatingEntity;
import entity.floating.NeutralEntity;
import entity.floating.collectable.CoinEntity;

public final class EntityFactory {

	/**
	 * Returns a random floating entity.
	 * The level uses this method and supplies a width and height to try.
	 * However, certain entities may choose to use their own custom width and height.
	 */
	public static FloatingEntity getRandomFloatingEntity(float x, float y, float tryWidth, float tryHeight) {
	    FloatingEntity entity;
		if(MathUtils.random() < 0.1) {
	    	entity = new CoinEntity(x, y);
	    } else if(MathUtils.random() < .35) {
			entity = new FatalEntity(x, y, tryWidth, tryHeight);
		} else {
			entity = new NeutralEntity(x, y, tryWidth, tryHeight);
		}
		
		entity.setUserData();
		
		return entity;
	}
}