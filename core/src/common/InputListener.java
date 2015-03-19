package common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.InputProcessor;

import entity.floating.LineEntity;

public class InputListener implements InputProcessor {
	
	private boolean isLineAnchored = false;
	private Color lineColor = Color.WHITE;
	private Line line = new Line();
	
	public InputListener() {
	}

	@Override
	public boolean keyDown(int keyCode) {
		switch(keyCode) {
			case Keys.ESCAPE:
				Gdx.app.exit();
				return true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keyCode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = (int)Utils.toMetersX(screenX);
		screenY = (int)Utils.toMetersY(screenY);

		if(Globals.getInstance().getState() != State.Running || button != Buttons.LEFT) {
			return false;
		}
		
        updateLine(screenX, screenY);
        line.setDy1();
        line.setDy2();
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = (int)Utils.toMetersX(screenX);
		screenY = (int)Utils.toMetersY(screenY);
		
		if(Globals.getInstance().getState() != State.Running || button != Buttons.LEFT) {
			return false;
		}
		
		tryBuildLine(screenX, screenY);		
		eraseLine();
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		screenX = (int)Utils.toMetersX(screenX);
		screenY = (int)Utils.toMetersY(screenY);
		
		if(Globals.getInstance().getState() != State.Running) {
			return false;
		}
		
		updateLine(screenX, screenY);
		line.setDy2();

		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public Line getLine() {
		return line;
	}
	
	public Color getLineColor() {
		return lineColor;
	}
	
	public void checkLineValidity() {
		if(!isLineAnchored) {
			return;
		}
		
		if(Globals.getInstance().getLevel().isLineValid(line)) {
			lineColor = Color.WHITE;
		} else {
			lineColor = Color.RED;
		}
	}
	
	public void updateLine() {
		updateLine(0, 0, false);
	}
	
	private void updateLine(float x, float y) {
		updateLine(x, y, true);
	}
 	
	private void updateLine(float x, float y, boolean fromTouchEvent) {
		if(isLineAnchored) {
			if(!fromTouchEvent) {
				x = (int)Utils.toMetersX(Gdx.input.getX());
				y = (int)Utils.toMetersY(Gdx.input.getY());	
			}
			
			line.setLine(line.x1, line.y1, x, y);
		} else {
			line.setLine(x, y, x, y);
		}
		
		if(fromTouchEvent) {
		  isLineAnchored = true;
		}
	}
	
	private void tryBuildLine(float x, float y) {
		// Don't build line if it is invalid.
		if(lineColor.r > lineColor.g) {
			return;
		}
			
		updateLine(x, y);	
		LineEntity lineEntity = new LineEntity(line.x1, Utils.getCameraTop() + line.getDy1(), line.x2, Utils.getCameraTop() + line.getDy2());
		lineEntity.setUserData();
		Globals.getInstance().getLevel().addLine(lineEntity);
	}
	
	private void eraseLine() {
		isLineAnchored = false;
		line.erase();
	}
}

