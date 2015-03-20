package common;

import entity.Entity;

public class BodyData {

	private boolean needsRemoved = false;
	private Entity entity;
	
	public BodyData(Entity entity) {
		this.entity = entity;;
	}
	
	public boolean needsRemoved() {
		return needsRemoved;
	}
	
	public void setNeedsRemoved() {
		needsRemoved = true;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
}
