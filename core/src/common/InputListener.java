package common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import core.Game;

public class InputListener implements InputProcessor {
	
	private Game game;
	
	public InputListener(Game game)
	{
		this.game = game;
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
		if(Globals.getInstance().getState() != State.Running || button != Buttons.LEFT) {
			return false;
		}
		
        game.updateLine(screenX, screenY, false);
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(Globals.getInstance().getState() != State.Running || button != Buttons.LEFT) {
			return false;
		}
		
		game.tryBuildLine(screenX, screenY);		
		game.eraseLine();
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(Globals.getInstance().getState() != State.Running) {
			return false;
		}
		
		game.updateLine(screenX, screenY, true);

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
}

