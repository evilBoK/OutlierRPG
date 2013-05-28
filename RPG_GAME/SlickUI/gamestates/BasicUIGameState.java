package gamestates;

import gui.GUIComponent;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import button.ButtonListener;

public abstract class BasicUIGameState extends BasicGameState {
	private final int STATE_ID;
	private GameContainer gc;
	private StateBasedGame sb;
	
	private ArrayList<GUIComponent> gui;
	
	public BasicUIGameState(int stateID) {
		this.STATE_ID = stateID;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		for(GUIComponent gc: gui)
			gc.setActive(true);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		for(GUIComponent gc: gui)
			gc.setActive(false);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		this.gc = gc;
		this.sb = sb;
		
		this.gui = new ArrayList<GUIComponent>();
	}
	
	@Override
	public abstract void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException;
	@Override
	public abstract void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException;

	//Get the BasicUIGameState's Input object for the GUIComponent 
	//and add the GUIComponent to the components list
	public boolean register(GUIComponent gc){
		gc.setInput(this.gc.getInput());
		return gui.add(gc);
	}
	
	public boolean remove(GUIComponent gc) {
		return gui.remove(gc);
	}
	
	@Override
	public int getID() {
		return STATE_ID;
	}

}
