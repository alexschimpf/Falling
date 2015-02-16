package com.tendersaucer.untitled;

public abstract class DynamicEntity extends Entity {
	
	protected DynamicEntity(String filename, float x, float y, float width, float height) {
		super(filename, x, y, width, height);
	}
	
	@Override
	public boolean update() {
		super.update();
		
		return false;
	}

	@Override
	public void done() {
	}
}
