package entity.floating.collectable.background;

import background.Background;
import entity.floating.collectable.CollectableEntity;

public abstract class BackgroundModifierEntity extends CollectableEntity {

	public BackgroundModifierEntity() {
		super();
	}
	
	public abstract boolean updateBackground(Background background);
	
	@Override
	public void collect() {
		globals.getBackground().setModifier(this);
	}
}
