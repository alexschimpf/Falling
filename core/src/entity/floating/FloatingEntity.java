package entity.floating;

import common.Utils;
import entity.Entity;

public abstract class FloatingEntity extends Entity {

	public FloatingEntity(float x, float y, float width, float height) {
		super();
		
		this.width = width;
		this.height = height;
		
		buildBody(x, y);
	}
	
	public FloatingEntity() {
		super();
	}
	
	@Override
	public boolean update() {
		return super.update() || getBottom() < Utils.getCameraTop();
	}
	
	@Override
	protected void buildBody(float x, float y) {
	}
}
