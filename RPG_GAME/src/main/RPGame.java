package main;

import gamestates.CharacterCreationState;
import gamestates.MainMenuState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
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
			 AppGameContainer app = new AppGameContainer(new RPGame());
	         app.setDisplayMode(816, 816, false);
	         app.setTargetFrameRate(119);
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
