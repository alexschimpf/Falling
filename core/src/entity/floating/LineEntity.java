package entity.floating;

import java.awt.geom.Line2D;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.TimeUtils;

import common.Globals;
import common.Utils;

public final class LineEntity extends FloatingEntity {

	private static final float LIFE_TIME = 2000;
	
	private long startTime;
	private float color = 1;
	private float x2, y2;
	
	public LineEntity(float x1, float y1, float x2, float y2) {
		super();
		
		this.x = x1;
		this.y = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		float dx = x2 - x1;
		float dy = y2 - y1;
		this.width = (float)Math.sqrt((dx * dx) + (dy * dy));		
		this.height = 1;
		
		setStraightVelocity();
		
		buildBody();
		
		startTime = TimeUtils.millis();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(color, color, color, 1);
		shapeRenderer.line(x, y, x2, y2);
	}

	@Override
	public boolean update() {
		super.update();
		
//		float angle = body.getAngle();
//		float cX = Utils.convertToPixels(body.getPosition().x);
//		float cY = Utils.convertToPixels(body.getPosition().y);
//		float halfLength = width / 2;		
//		x = cX - (MathUtils.cos(angle) * halfLength);
//		y = cY - (MathUtils.sin(angle) * halfLength);
//		x2 = cX + (MathUtils.cos(angle) * halfLength);
//		y2 = cY + (MathUtils.sin(angle) * halfLength);
		
		float elapsed = TimeUtils.millis() - startTime;
		if(elapsed > LIFE_TIME) {
			return true;
		}
		
		color = elapsed / LIFE_TIME;
		
		return false;
	}
	
	@Override
	protected void buildBody() {
		float x1 = Utils.convertToMeters(this.x);
		float y1 = Utils.convertToMeters(this.y);
		float x2 = Utils.convertToMeters(this.x2);
		float y2 = Utils.convertToMeters(this.y2);
		float width = Utils.convertToMeters(this.width);
		float height = Utils.convertToMeters(this.height);
		
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
}
