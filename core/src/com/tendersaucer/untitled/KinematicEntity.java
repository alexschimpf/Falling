package com.tendersaucer.untitled;

public abstract class KinematicEntity extends Entity {

	protected Path path;
	
	protected KinematicEntity(String filename, float x, float y, float width, float height) {
		super(filename, x, y, width, height);
	}
	
	@Override
	public boolean update() {
		super.update();
				
		move();
		
		return false;
	}

	@Override
	public void done() {
	}

	public void move() {
		path.update();
		body.setLinearVelocity(path.getSpeedX(), path.getSpeedY());
	}
}
