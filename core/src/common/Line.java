package common;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import entity.Entity;

public class Line {
	public float x1, y1;
	public float x2, y2;
	public Vector2 p1;
	public Vector2 p2;
	
	public Line(float x1, float y1, float x2, float y2) {
		p1 = new Vector2();
		p2 = new Vector2();
		
		setLine(x1, y1, x2, y2);
	}
	
	public Line() {
		p1 = new Vector2();
		p2 = new Vector2();
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

	public boolean intersectsEntity(Entity entity) {
		Shape shape = entity.getBody().getFixtureList().get(0).getShape();
		if(shape instanceof CircleShape) {
			CircleShape circleShape = (CircleShape)shape;
			float radius = circleShape.getRadius();
			Vector2 center = new Vector2(entity.getX(), entity.getY());
			return Intersector.intersectSegmentCircle(p1, p2, center, radius);
		} else if(shape instanceof PolygonShape) {
			return false;
//			PolygonShape polygonShape = (PolygonShape)shape;
//			
//			String verts = "";
//			float[] vertices = new float[polygonShape.getVertexCount() * 2];
//			for(int i = 0; i < polygonShape.getVertexCount(); i++) {
//				Vector2 vertex = new Vector2();
//				polygonShape.getVertex(i, vertex);
//				vertices[i * 2] = Utils.convertToPixels(vertex.x);
//				vertices[(i * 2) + 1] = Utils.convertToPixels(vertex.y);
//				
//				verts += "(" + vertices[i * 2] + ", " + vertices[(i * 2) + 1] + "), ";
//			}
//			Polygon polygon = new Polygon(vertices);
//			
//			System.out.println("line: " + p1.x + ", " + p1.y + " to " + p2.x + ", " + p2.y);
//			System.out.println("verts: " + verts);
//			return Intersector.intersectSegmentPolygon(p1, p2, polygon);
		}
		
		return false;
	}
}
