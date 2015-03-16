package entity.floating;

import java.awt.geom.Line2D;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.TimeUtils;

import common.Globals;

public final class LineEntity extends FloatingEntity {

	private static final float LIFE_TIME = 3000;
	
	private long startTime;
	private float color = 1;
	private float x1, y1, x2, y2;
	
	public LineEntity(float x1, float y1, float x2, float y2) {
		super();
		
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		setStraightVelocity();
		
		buildBody();
		
		startTime = TimeUtils.millis();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(color, color, color, 1);
		shapeRenderer.line(x1, y1, x2, y2);
	}

	@Override
	public boolean update() {
		float elapsed = TimeUtils.millis() - startTime;
		if(elapsed > LIFE_TIME) {
			return true;
		}
		
		color = 1 - (elapsed / LIFE_TIME);
		
		return super.update();
	}
	
	public boolean intersectsLine(Line2D line) {
		return line.intersectsLine(x1, y1, x2, y2);
	}
	
	@Override
	protected void buildBody() {
		float dx = x2 - x1;
		float dy = y2 - y1;
		float w = (float)Math.sqrt((dx * dx) + (dy * dy));
		float h = 1.0f;		
		
		float minX = Math.min(x1, x2);
		float minY = Math.min(y1, y2);
		float cX = minX + Math.abs(dx) / 2;
		float cY = minY + Math.abs(dy) / 2;
		
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.KinematicBody;
		bDef.position.set(cX, cY);
		body = Globals.getInstance().getLevel().getWorld().createBody(bDef);
		
		Vector2 center = new Vector2(0, 0);		
		float angle = (float)Math.atan2(dy, dx);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w / 2, h / 2, center, angle);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 0;
		fDef.friction = 0;
		fDef.restitution = .3f;
		body.createFixture(fDef);
		
		shape.dispose();
	}
}
