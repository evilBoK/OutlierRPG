package gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class CharacterCreationState extends BasicUIGameState {
	private StateBasedGame sb;
	
	private Music bgm;
	
	private Image background;
	
	public CharacterCreationState(int stateID) {
		super(stateID);
	}
	
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException{
		super.init(gc, sb);
		this.sb = sb;
		
		bgm = new Music("/res/Modified Resources/BalletElevinLoop.ogg");
		
		background = new Image("/res/Graphics/BattleBacks1/Wood1.png");
	}
	
	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		bgm.loop(1, 0);
		bgm.fade(500, 1, false);
	}
	
	public void leave(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.leave(gc, sb);
		bgm.fade(500, 0, true);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {

	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		
	}

}
