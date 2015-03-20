package core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import common.Globals;
import common.InputListener;
import common.Line;
import common.State;
import common.Utils;

public class Game extends ApplicationAdapter {

	private int numBits = 0;
	private BitmapFont font;
	private Globals globals;
	private Level level;
	private PolygonSpriteBatch polygonSpriteBatch;
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;

	@Override
	public void create() {
		globals = Globals.getInstance();
		globals.setGame(this);
		globals.setState(State.Running);
		
		polygonSpriteBatch = new PolygonSpriteBatch();
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		debugRenderer = new Box2DDebugRenderer();
		
		font = new BitmapFont(Gdx.files.internal("Diner-Fatt-32.fnt"), true);
	    font.setColor(Color.WHITE);
	    font.setScale(0.3f, 0.15f);
	    font.setUseIntegerPositions(false);
		
		Gdx.input.setInputProcessor(new InputListener());
		
		globals.initLevel();
		level = globals.getLevel();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update();
		
		polygonSpriteBatch.setProjectionMatrix(Globals.getInstance().getCamera().combined);
		polygonSpriteBatch.begin();
		{
			drawFilledPolygons();
		}
		polygonSpriteBatch.end();
		
		spriteBatch.setProjectionMatrix(Globals.getInstance().getCamera().combined);
		spriteBatch.begin();
		{
			drawNumBitsText();
		}
		spriteBatch.end();

		debugMatrix = new Matrix4(Globals.getInstance().getCamera().combined);
		shapeRenderer.setProjectionMatrix(Globals.getInstance().getCamera().combined);
		shapeRenderer.begin(ShapeType.Line); 
		{
			InputListener inputListener = (InputListener)Gdx.input.getInputProcessor();
			inputListener.getLine().draw(shapeRenderer);
			level.drawFatals(shapeRenderer);
			
			debugRenderer.render(level.getWorld(), debugMatrix);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void resize(int width, int height) {
        globals.getCamera().viewportHeight = (Globals.VIEWPORT_WIDTH / width) * height;
        globals.getCamera().update();
	}
	
	public void addBits(int num) {
		numBits += num;
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
				numBits = 0;
				globals.setState(State.Running);
				break;
			case Paused:
				break;
		}
	}
	
	private void drawFilledPolygons() {
		level.drawFilledPolygons(polygonSpriteBatch);
	}
	
	private void drawNumBitsText() {
		String numBitsStr = numBits + " bits";
		float numBitsTextWidth = font.getBounds(numBitsStr).width;
		float numBitsTextX = Globals.VIEWPORT_WIDTH - numBitsTextWidth - 2;
		float numBitsTextY = Utils.getCameraTop() + ((7.0f / 10.0f) * font.getBounds(numBitsStr).height);
		font.draw(spriteBatch, numBitsStr, numBitsTextX, numBitsTextY);
	}
}
