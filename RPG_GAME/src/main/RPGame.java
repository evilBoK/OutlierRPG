package main;

import java.awt.Composite;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gamestates.CharacterCreationState;
import gamestates.MainMenuState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RPGame extends StateBasedGame {

	public static final int MAIN_MENU_STATE = 0;
	public static final int CHARACTER_CREATION_STATE = 1;
	public static final int LOAD_GAME_STATE = 2;
	public static final int SETTINGS_STATE = 3;
	public static final int WORLD_STATE = 4;
	public static final int COMBAT_STATE = 5;
	
	public RPGame() {
		super("The Role-Playing Game");
	}

	public static void main(String[] args) {
		try {
			 AppGameContainer app = new AppGameContainer(new ScalableGame(new RPGame(), 1088, 832, true));
			 app.setDisplayMode(544, 416, false);
	         app.setTargetFrameRate(60);
	         app.setShowFPS(false);
	         app.start();
			}
			catch(SlickException e) {}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainMenuState(MAIN_MENU_STATE));
		this.addState(new CharacterCreationState(CHARACTER_CREATION_STATE));
	}

}
