package com.tendersaucer.untitled;

import java.io.File;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Level implements IUpdate {

	protected float startAngle;
	private It it;
	private World world;
	protected Vector2 startPos;
	protected Vector2 endPos;
	
	public Level(File file) {
	}

	@Override
	public boolean update() {
		Array<Body> bodies = new Array<Body>();
		this.world.getBodies(bodies);
		for(Body body : bodies) {
			Entity entity = ((BodyData)body.getUserData()).getEntity();
			if(entity.update()) {
				entity.done();
			}
		}
		
		return false;
	}

	@Override
	public void done() {
	}	
	
	public It getIt() {
		return this.it;
	}

	public World getWorld() {
		return this.world;
	}
}
