package button;

import gamestates.BasicUIGameState;
import gui.GUIComponent;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
//Should ButtonSubtype extend Button to satisfy ButtonListener?
enum State {
	Mouse_Over, Mouse_Down, Default;
}

public abstract class Button implements GUIComponent, MouseListener {
	//General
	protected int x, y, width, height;
	protected Shape clickBox;
	protected ArrayList<ButtonListener> listeners;		
	protected State state;
	protected Color filter;
	
	protected boolean acceptingInput;
	protected Sound mouseOverSound, mouseDownSound;
	
	protected Button(int x, int y, int width, int height, BasicUIGameState gameState) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.clickBox = new Rectangle(x,y,width,height);
		this.listeners = new ArrayList<ButtonListener>();
		this.acceptingInput = false;
		this.mouseOverSound = null;
		this.mouseDownSound = null;
		
		this.setState(State.Default);
		
		resetFilter();
		
		if(gameState instanceof ButtonListener)
			addListener((ButtonListener)gameState);
		//Is this cast legal?
		
		gameState.register(this);
	}
	
	public abstract void setState(State state);
	
	//GUIComponent
	@Override
	public void setActive(Boolean active) {
		this.acceptingInput = active;
	}
	@Override
	public void setInput(Input input) {
		input.addMouseListener(this);
	}
	@Override
	public abstract void render(Graphics g);
	
	//ButtonListener Methods
	public boolean addListener(ButtonListener listener) {
		return listeners.add(listener);
	}
	public boolean removeListener(ButtonListener listener) {
		return listeners.remove(listener);
	}
	
	//MouseListener Methods
	@Override
	public boolean isAcceptingInput() {
		return this.acceptingInput;
	}
	
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}
	
	@Override
	public void mouseDragged(int oldX, int oldY, int newX, int newY) {
		mouseMoved(oldX, oldY, newX, newY);
	}
	@Override
	public void mouseMoved(int oldX, int oldY, int newX, int newY) {
		if(clickBox.contains(newX, newY) && !clickBox.contains(oldX, oldY)) {
			if(mouseOverSound != null)
				mouseOverSound.play();
			
			this.setState(State.Mouse_Over);
		}
		else if(!clickBox.contains(newX, newY) && clickBox.contains(oldX, oldY))
			this.setState (State.Default);
	}
	@Override
	public void mousePressed(int button, int xPos, int yPos) {
		if(state.equals(State.Mouse_Over)) {
			this.setState(State.Mouse_Down);
			if(mouseDownSound != null)
				mouseDownSound.play();
		}
	}
	@Override
	public void mouseReleased(int button, int xPos, int yPos) {
		if(state.equals(State.Mouse_Down)){
			for (ButtonListener listener : listeners)
				listener.buttonClicked(this);
			this.setState(State.Mouse_Over);
		}
	}
	
	//General Methods
	public boolean isMouseOver() {
		return this.state.equals(State.Mouse_Over)||this.state.equals(State.Mouse_Down);
	}
	
	public boolean isMouseDown() {
		return this.state.equals(State.Mouse_Down);
	}
	
	//Unimplemented Abstract methods
	@Override
	public void mouseWheelMoved(int arg0) {}
	public Color getFilterColor() {
		return filter;
	}

	public void setFilterColor(Color filter) {
		this.filter = filter;
	}
	
	public void resetFilter() {
		this.filter = Color.white;
	}

	@Override
	public void inputEnded() {}
	@Override
	public void inputStarted() {}
}