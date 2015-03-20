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
import entity.floating.NeutralEntity;
import entity.floating.collectable.CollectableEntity;

public final class CollisionListener implements ContactListener {

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
		if(dataA == null || dataB == null) {
			return;
		}
		
		Entity entityA = dataA.getEntity();
		Entity entityB = dataB.getEntity();
	
		checkFatalCollision(contact, entityA, entityB);
		checkCollectableCollision(contact, entityA, entityB);
		checkNeutralCollision(contact, entityA, entityB, true);
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		Body bodyA = fixA.getBody();
		Body bodyB = fixB.getBody();
		BodyData dataA = (BodyData)bodyA.getUserData();
		BodyData dataB = (BodyData)bodyB.getUserData();
		if(dataA == null || dataB == null) {
			return;
		}
		
		Entity entityA = dataA.getEntity();
		Entity entityB = dataB.getEntity();
		
		checkNeutralCollision(contact, entityA, entityB, false);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
	
	private void checkFatalCollision(Contact contact, Entity a, Entity b) {
		boolean theEntity = a instanceof TheEntity || b instanceof TheEntity;
		boolean fatal = a instanceof FatalEntity || b instanceof FatalEntity;
		if(theEntity && fatal) {
			TheEntity theEntityObj;
			if(a instanceof TheEntity) {
				theEntityObj = (TheEntity)a;
			} else {
				theEntityObj = (TheEntity)b;
			}
			
			theEntityObj.done();
		}
	}
	
	private void checkCollectableCollision(Contact contact, Entity a, Entity b) {
		boolean theEntity = a instanceof TheEntity || b instanceof TheEntity;
		boolean collectable = a instanceof CollectableEntity || b instanceof CollectableEntity;
		if(theEntity && collectable) {
			contact.setEnabled(false);
			
			CollectableEntity collectableObj;
			if(a instanceof CollectableEntity) {
				collectableObj = (CollectableEntity)a;
			} else {
				collectableObj = (CollectableEntity)b;
			}
			
			collectableObj.collect();
			((BodyData)collectableObj.getBody().getUserData()).setNeedsRemoved();
		}
	}
	
	private void checkNeutralCollision(Contact contact, Entity a, Entity b, boolean beginContact) {
		boolean theEntity = a instanceof TheEntity || b instanceof TheEntity;
		boolean neutral = a instanceof NeutralEntity || b instanceof NeutralEntity;
		neutral = neutral && !(a instanceof FatalEntity);
		if(theEntity && neutral) {
			NeutralEntity neutralObj;
			if(a instanceof NeutralEntity) {
				neutralObj = (NeutralEntity)a;
			} else {
				neutralObj = (NeutralEntity)b;
			}

			neutralObj.setLitUp(beginContact);
		}	
	}
}
