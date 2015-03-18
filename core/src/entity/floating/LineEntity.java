package entity.floating;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.TimeUtils;

import common.Globals;

public final class LineEntity extends FloatingEntity {

	private static final float LIFE_TIME = 2000;
	
	private long startTime;
	private float color = 1;
	private float x2, y2;
	
	public LineEntity(float x1, float y1, float x2, float y2) {
		super();
		
		this.x2 = x2;
		this.y2 = y2;
		
		float dx = x2 - x1;
		float dy = y2 - y1;
		this.width = (float)Math.sqrt((dx * dx) + (dy * dy));		
		this.height = 0.5f;
		
		setStraightVelocity();
		
		buildBody(x1, y1);
		
		startTime = TimeUtils.millis();
	}

	@Override
	public boolean update() {
		super.update();

		float elapsed = TimeUtils.millis() - startTime;
		if(elapsed > LIFE_TIME) {
			return true;
		}
		
		color = elapsed / LIFE_TIME;
		
		return false;
	}
	
	@Override
	protected void buildBody(float x1, float y1) {
		float dx = x2 - x1;
		float dy = y2 - y1;	
		float angle = MathUtils.atan2(dy, dx);
		
		float cX = (x1 + x2) / 2;
		float cY = (y1 + y2) / 2;
		
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.KinematicBody;
		bDef.position.set(cX, cY);
		body = Globals.getInstance().getLevel().getWorld().createBody(bDef);			
		
		Vector2 center = new Vector2(0, 0);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2, center, angle);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 0;
		fDef.friction = 0;
		fDef.restitution = .1f;
		body.createFixture(fDef);
		
		shape.dispose();
	}
	
	public float getX2() {
		return x2;
	}
	
	public float getY2() {
		return y2;
	}
}
