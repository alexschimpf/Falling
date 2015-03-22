package entity.floating.collectable.modifier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;

public class TrippyModifierEntity extends ModifierEntity {
	
	public TrippyModifierEntity(float x, float y) {
		super(x, y);
	}

	@Override
	public boolean modify() {
		if(MathUtils.random() < 0.4f) {
			Gdx.gl.glClearColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}	
				
		return super.modify();
	}
}
