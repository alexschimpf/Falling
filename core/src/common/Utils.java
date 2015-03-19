package common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public final class Utils {

	private static Globals globals = Globals.getInstance();
	
	public static float toMetersX(float pixels) {
		float cameraX = globals.getCamera().position.x - (Globals.VIEWPORT_WIDTH / 2);
		return cameraX + (pixels * Globals.VIEWPORT_WIDTH) / Gdx.graphics.getWidth();
	}
	
	public static float toMetersY(float pixels) {
		float cameraY = globals.getCamera().position.y - (Globals.VIEWPORT_HEIGHT/ 2);
		return cameraY + (pixels * Globals.VIEWPORT_HEIGHT) / Gdx.graphics.getHeight();
	}
	
	public static float getCameraTop() {
		return globals.getCamera().position.y - (Globals.VIEWPORT_HEIGHT / 2);
	}
	
	public static float getCameraBottom() {
		return globals.getCamera().position.y + (Globals.VIEWPORT_HEIGHT / 2);
	}
	
	public static float[] getLocalVertices(PolygonShape shape) {
		return Utils.getVertices(null, shape);
	}
	
	public static float[] getWorldVertices(Body body, PolygonShape shape) {
		return Utils.getVertices(body, shape);
	}
	
	private static float[] getVertices(Body body, PolygonShape shape) {
		Vector2 vertex = new Vector2();
		float[] vertices = new float[shape.getVertexCount() * 2];
		for(int i = 0; i < shape.getVertexCount(); i++) {
			shape.getVertex(i, vertex);
			
			if(body != null) {
				vertex = body.getWorldPoint(vertex);
			}
			
			vertices[i * 2] = vertex.x;
			vertices[(i * 2) + 1] = vertex.y;
		}
		
		return vertices;
	}
}
