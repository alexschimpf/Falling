package common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import entity.Entity;

public final class Line {
	public float x1, y1;
	public float x2, y2;
	
	private Vector2 p1;
	private Vector2 p2;	
	private float dy1, dy2;
	
	public Line(float x1, float y1, float x2, float y2) {
		p1 = new Vector2();
		p2 = new Vector2();
		
		setLine(x1, y1, x2, y2);
	}
	
	public Line() {
		p1 = new Vector2();
		p2 = new Vector2();
	}
	
	public void draw(ShapeRenderer shapeRenderer) {		
		Color color = ((InputListener)Gdx.input.getInputProcessor()).getLineColor();

		y1 = Utils.getCameraTop() + dy1;
		y2 = Utils.getCameraTop() + dy2;
		
		shapeRenderer.setColor(color);
		shapeRenderer.line(x1, y1, x2, y2);
	}
	
	public void setLine(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		p1.x = x1;
		p1.y = y1;
		p2.x = x2;
		p2.y = y2;
	}
	
	public void erase() {
		setLine(0, 0, 0, 0);
	}
	
	public void setDy1() {
		dy1 = y1 - Utils.getCameraTop();
	}
	
	public void setDy2() {
		dy2 = y2 - Utils.getCameraTop();
	}
	
	public float getDy1() {
		return dy1;
	}
	
	public float getDy2() {
		return dy2;
	}

	public boolean intersectsEntity(Entity entity) {
		Frustum frustum = Globals.getInstance().getCamera().frustum;
		if(!frustum.boundsInFrustum(entity.getLeft(), entity.getTop(), 0, entity.getWidth() / 2, entity.getHeight() / 2, 0)) {
			return false;
		}
		
		p1.y = Utils.getCameraTop() + dy1;
		p2.y = Utils.getCameraTop() + dy2;
		
		Body body = entity.getBody();
		for(Fixture fixture : body.getFixtureList()) {
			Shape shape = fixture.getShape();
			if(shape instanceof CircleShape) {
				CircleShape circleShape = (CircleShape)shape;
				float radius = circleShape.getRadius();
				Vector2 center = new Vector2(entity.getCenterX(), entity.getCenterY());
				return Intersector.intersectSegmentCircle(p1, p2, center, radius * radius);
			} else if(shape instanceof PolygonShape) {
				PolygonShape polygonShape = (PolygonShape)shape;
				
				float[] vertices = Utils.getWorldVertices(body, polygonShape);
				Polygon polygon = new Polygon(vertices);
				
				return Intersector.intersectSegmentPolygon(p1, p2, polygon);
			}
		}
		
		return false;
	}
}
