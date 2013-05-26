package gamestates;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RPG_Game extends StateBasedGame {

	public static final int MAIN_MENU_STATE = 0;
	public static final int NEW_GAME_STATE = 1;
	public static final int CONTINUE_STATE = 2;
	public static final int OPTIONS_STATE = 3;
	public static final int CHARACTER_MOVE_STATE = 4;
	public static final int COMBAT_STATE = 5;
	
	public RPG_Game() {
		super("The Role-Playing Game");
	}

	public static void main(String[] args) {
		try {
			 AppGameContainer app = new AppGameContainer(new RPG_Game());
	         app.setDisplayMode(800, 600, false);
	         app.setTargetFrameRate(119);
	         app.setShowFPS(false);
	         app.start();
			}
			catch(SlickException e) {}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainMenuState(MAIN_MENU_STATE));
		//this.addState(new ContinueStateStub(CONTINUE_STATE));
		//this.addState(new OptionsStateStub(OPTIONS_STATE));
		//this.addState(new CharacterMoveState(CHARACTER_MOVE_STATE));
	}

}
