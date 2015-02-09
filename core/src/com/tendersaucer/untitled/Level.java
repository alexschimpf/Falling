package com.tendersaucer.untitled;

import java.io.File;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class Level implements IUpdate {

	protected float startAngle;
	protected Vector2 startPos;
	protected Vector2 endPos;
	protected LinkedList<Entity> entities = new LinkedList<Entity>();
	protected LinkedList<DrawLine> drawLines = new LinkedList<DrawLine>();
	
	public Level(File file) {
		
	}
	
	public Level(LinkedList<Entity> entities, Vector2 startPos, Vector2 endPos, float startAngle) {
		this.entities.addAll(entities);
		this.startPos = startPos;
		this.endPos = endPos;
		this.startAngle = startAngle;
	}

	@Override
	public boolean update() {
		for(Entity entity : entities) {
			if(entity.update()) {
				entity.done();
			}
		}
		
		return false;
	}

	@Override
	public void done() {
	}	
}
