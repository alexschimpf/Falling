package core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import common.Globals;
import common.InputListener;
import common.Line;
import common.State;

public class Game extends ApplicationAdapter {

	private Globals globals;
	private Level level;
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;

	@Override
	public void create() {
		globals = Globals.getInstance();
		globals.setGame(this);
		globals.setState(State.Running);
		
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		debugRenderer = new Box2DDebugRenderer();
		
		Gdx.input.setInputProcessor(new InputListener());
		
		globals.initLevel();
		level = globals.getLevel();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update();

		debugMatrix = new Matrix4(Globals.getInstance().getCamera().combined);
		shapeRenderer.setProjectionMatrix(Globals.getInstance().getCamera().combined);
		shapeRenderer.begin(ShapeType.Line); 
		{
			InputListener inputListener = (InputListener)Gdx.input.getInputProcessor();
			inputListener.getLine().draw(spriteBatch, shapeRenderer);
			draw(spriteBatch, shapeRenderer);
			
			debugRenderer.render(level.getWorld(), debugMatrix);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void resize(int width, int height) {
        globals.getCamera().viewportHeight = (Globals.VIEWPORT_WIDTH / width) * height;
        globals.getCamera().update();
	}
	
	private void update() {
		globals.updateCamera();
		InputListener inputListener = (InputListener)Gdx.input.getInputProcessor();
		inputListener.updateLine();
		inputListener.checkLineValidity();
		
		switch(globals.getState()) {
			case Loading:
				break;
			case Running:
				level.update();
				break;
			case GameOver:
				level.reset();
				globals.setState(State.Running);
				break;
			case Paused:
				break;
		}
	}
	
	private void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		switch(globals.getState()) {
			case Loading:
				break;
			case Running:
				level.draw(spriteBatch, shapeRenderer);
				break;
			case GameOver:
				break;
			case Paused:
				break;
		}
	}
}
