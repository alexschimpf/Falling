package com.tendersaucer.untitled;

public abstract class StaticEntity extends Entity {

	protected StaticEntity(String filename, float x, float y, float width, float height) {
		super(filename, x, y, width, height);
	}
	
	@Override
	public final boolean update() {
		return false;
	}

	@Override
	public final void done() {
	}
}
