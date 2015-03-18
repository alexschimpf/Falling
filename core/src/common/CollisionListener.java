package common;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import entity.Entity;
import entity.TheEntity;
import entity.floating.FatalEntity;

public class CollisionListener implements ContactListener {

	public CollisionListener() {
		
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		Body bodyA = fixA.getBody();
		Body bodyB = fixB.getBody();
		BodyData dataA = (BodyData)bodyA.getUserData();
		BodyData dataB = (BodyData)bodyB.getUserData();
		Entity entityA = dataA.getEntity();
		Entity entityB = dataB.getEntity();
		
		boolean theEntity = entityA instanceof TheEntity || entityB instanceof TheEntity;
		boolean fatal = entityA instanceof FatalEntity || entityB instanceof FatalEntity;
		if(theEntity && fatal) {
			TheEntity theEntityObj;
			if(entityA instanceof TheEntity) {
				theEntityObj = (TheEntity)entityA;
			} else {
				theEntityObj = (TheEntity)entityB;
			}
			
			theEntityObj.done();
		}
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
