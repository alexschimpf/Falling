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

	private boolean needsLevelReset = false;
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
		globals.setState(State.GameOver);
		
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
			level.drawFilledPolygons(polygonSpriteBatch);
		}
		polygonSpriteBatch.end();
		
		spriteBatch.setProjectionMatrix(Globals.getInstance().getCamera().combined);
		spriteBatch.begin();
		{
			drawSprites();
		}
		spriteBatch.end();

		debugMatrix = new Matrix4(Globals.getInstance().getCamera().combined);
		shapeRenderer.setProjectionMatrix(Globals.getInstance().getCamera().combined);
		shapeRenderer.begin(ShapeType.Line); 
		{
			drawShapes();
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
	
	public void setNeedsLevelReset() {
		needsLevelReset = true;
	}
	
	private void update() {
		globals.updateCamera();
		InputListener inputListener = (InputListener)Gdx.input.getInputProcessor();
		inputListener.updateLine();
		inputListener.checkLineValidity();
		
		switch(globals.getState()) {
			case Running:
				level.update();
				break;
			case GameOver:
				if(needsLevelReset) {
					needsLevelReset = false;
					level.reset();
					numBits = 0;
				}
			default:
				break;
		}
	}
	
	private void drawShapes() {
		switch(Globals.getInstance().getState()) {
			case Running:
				InputListener inputListener = (InputListener)Gdx.input.getInputProcessor();
				inputListener.getLine().draw(shapeRenderer);
				level.drawFatals(shapeRenderer);
				
				debugRenderer.render(level.getWorld(), debugMatrix);
		        break;
			default:
				break;	
		}
	}
	
	private void drawSprites() {
		switch(Globals.getInstance().getState()) {
			case Running:
				drawNumBitsText();
				break;
			case GameOver:
				drawBeginText();
				break;
			default:
				break;
		}
	}
	
	private void drawNumBitsText() {
		String numBitsStr = numBits + " bits";
		float textWidth = font.getBounds(numBitsStr).width;
		float textX = Globals.VIEWPORT_WIDTH - textWidth - 2;
		float textY = Utils.getCameraTop() + ((7.0f / 10.0f) * font.getBounds(numBitsStr).height);
		font.draw(spriteBatch, numBitsStr, textX, textY);
	}
	
	private void drawBeginText() {
		String numBitsStr = "Touch to begin.";
		float textWidth = font.getBounds(numBitsStr).width;
		float textHeight = font.getBounds(numBitsStr).height;
		float textX = (Globals.VIEWPORT_WIDTH - textWidth) / 2;
		float textY = (Globals.VIEWPORT_HEIGHT - textHeight) / 2;
		font.draw(spriteBatch, numBitsStr, textX, textY);
	}
}
