package background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import common.Globals;
import common.IUpdate;
import common.Textures;
import common.Utils;

public class Star implements IUpdate {
	
	public boolean isVisible = true;
	public float x, y;
	public float width, height;
	public float dy;
	public TextureRegion texture;
	
	public Star(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		dy = y - Utils.getCameraTop();
		
		texture = Textures.getInstance().getStarTexture();
	}

	@Override
	public boolean update() {
		if(MathUtils.random() < 0.001) {
			isVisible = false;
		} else {
			isVisible = true;
		}
		
		
		y = Utils.getCameraTop() + dy - (Gdx.graphics.getDeltaTime() * Globals.getInstance().getLevel().getSpeed() * 8);
		dy = y - Utils.getCameraTop();
		
		return y + height < Utils.getCameraTop();
	}

	@Override
	public void done() {		
	}	
}
