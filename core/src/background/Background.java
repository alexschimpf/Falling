package background;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import common.Globals;
import common.IUpdate;
import common.Textures;
import common.Utils;
import entity.floating.collectable.background.BackgroundModifierEntity;

public final class Background implements IUpdate {

	protected static final int MAX_STAR_COUNT = 400;
	protected static final int MIN_STAR_COUNT = 200;
	protected static final float MIN_STAR_SIZE = Globals.VIEWPORT_WIDTH / 200;
	protected static final float MAX_STAR_SIZE = Globals.VIEWPORT_WIDTH / 100;
	
	private float starFloor = 0;
	private BackgroundModifierEntity modifier;
	private Textures textures = Textures.getInstance();
	private LinkedList<Star> stars = new LinkedList<Star>();
	
	public Background() {		
	}
	
	public void draw(SpriteBatch spriteBatch) {
		for(Star star : stars) {
			if(star.y < Utils.getCameraBottom() && star.isVisible) {
				spriteBatch.draw(star.texture, star.x, star.y, star.width, star.height);
			}
		}
	}

	@Override
	public boolean update() {
		tryCreateStars();
		
		if(modifier != null) {
			if(modifier.updateBackground(this)) {
				modifier = null;
			}
		}
		
		Iterator<Star> starIt = stars.iterator();
		while(starIt.hasNext()) {	
			Star star = starIt.next();
			if(star.update()) {
				starIt.remove();
			}
		}
		
		return false;
	}
	
	private void tryCreateStars() {
		if(stars.size() >= MIN_STAR_COUNT) {
			return;
		}
		
		float screenWidth = Globals.VIEWPORT_WIDTH;
		float screenHeight = Globals.VIEWPORT_HEIGHT;
		
		float newStarFloor = starFloor;
		while(stars.size() <= MAX_STAR_COUNT) {
			float width = MathUtils.random(MIN_STAR_SIZE, MAX_STAR_SIZE);
			float height = MathUtils.random(MIN_STAR_SIZE, MAX_STAR_SIZE);
			float x = MathUtils.random(0, screenWidth - width);
			float y = starFloor + MathUtils.random(0, screenHeight * 4);
			stars.add(new Star(x, y, width, height));
			
			newStarFloor = Math.max(starFloor, y);
		}
		
		starFloor = newStarFloor;
	}

	@Override
	public void done() {		
	}
	
	public void setModifier(BackgroundModifierEntity modifier) {
		this.modifier = modifier;
	}
}
