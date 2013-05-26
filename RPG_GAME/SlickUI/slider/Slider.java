package slider;


import gamestates.BasicUIGameState;
import gui.GUIComponent;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Rectangle;


enum Orientation {
	Horizontal, Vertical;
}

public class Slider implements GUIComponent, MouseListener {
	private int x, y, width, height;
	private Rectangle staticBar, dynamicBar;
	private Color staticBarColor, dynamicBarColor;
	float staticBarThickness, dynamicBarThickness;
	
	private float sliderValue;
	private boolean sliding;
	
	private Orientation orientation;
	private boolean acceptingInput;
	private ArrayList<SliderListener> listeners;
	
	public Slider(int x, int y, int width, int height, BasicUIGameState gameState) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		//Default Slider Proportions
		staticBarThickness = 20f/100f;
		dynamicBarThickness  = 10f/100f;
		
		//Determines orientation
		detectOrientation();
		
		//Creates, scales, and positions bars
		buildSlider();
		
		listeners = new ArrayList<SliderListener>();
		sliderValue = 0.5f;
		
		staticBarColor = Color.darkGray;
		dynamicBarColor = Color.lightGray;
		
		if(gameState instanceof SliderListener)
			addListener((SliderListener)gameState);
		//Is this cast legal?
		
		gameState.register(this);
	}
	
	private void detectOrientation() {
		if(width > height)
			this.orientation = Orientation.Horizontal;
		else
			this.orientation = Orientation.Vertical;
	}
	
	private void buildSlider() {
		switch(orientation) {
		case Horizontal:
			staticBar = new Rectangle(0,0, width*(1f-dynamicBarThickness), height*staticBarThickness);
			staticBar.setCenterX(x + (width/2));
			staticBar.setCenterY(y + (height/2));
			
			dynamicBar = new Rectangle(0,0,width*(dynamicBarThickness), height);
			dynamicBar.setCenterX(x + (width/2f));
			dynamicBar.setCenterY(y + (height/2f));
			break;
		case Vertical:
			staticBar = new Rectangle(0,0, width*staticBarThickness, height*(1f-dynamicBarThickness));
			staticBar.setCenterX(x + (width/2));
			staticBar.setCenterY(y + (height/2));
			
			dynamicBar = new Rectangle(0,0,width, height*dynamicBarThickness);
			dynamicBar.setCenterX(x + (width/2f));
			dynamicBar.setCenterY(y + (height/2f));
			break;
		}
	}
	
	//GUIComponent
	@Override
	public void setActive(Boolean active) {
		this.acceptingInput = active;
	}
	@Override
	public void setInput(Input input) {
		input.addMouseListener(this);
	}
	public void render(Graphics g) {
		Color colorHolder = g.getColor();
		g.setColor(staticBarColor);
		g.fill(staticBar);
		g.setColor(dynamicBarColor);
		g.fill(dynamicBar);
		g.setColor(colorHolder);
	}
	
	//SliderListener Methods
	public void addListener(SliderListener listener) {
		listeners.add(listener);
	}
	public void removeListener(SliderListener listener) {
		listeners.remove(listener);
	}
	
	//MouseListener Methods
	@Override
	public boolean isAcceptingInput() {
		return acceptingInput;
	}
	@Override
	public void mousePressed(int button, int x, int y) {
		if(button==0 && dynamicBar.contains(x, y)) {
			sliding = true;
			for(SliderListener listener : listeners)
				listener.sliderPressed(this);
		}
	}
	@Override
	public void mouseDragged(int oldX, int oldY, int newX, int newY) {
		if(sliding) {
			float oldValue = sliderValue;
			
			switch(orientation) {
			case Horizontal:
				//Restrict newX to size of slider
				newX = newX>staticBar.getMinX()? newX:(int)staticBar.getMinX();
				newX = newX<staticBar.getMaxX()+1? newX:(int)staticBar.getMaxX()+1;
				//Update dynamicBar
				dynamicBar.setCenterX(newX);
				//Save new sliderValue
				sliderValue = (float)(newX-(int)staticBar.getMinX())/(int)staticBar.getWidth();
				break;
			case Vertical:
				//Restrict newX to size of slider
				newY = newY>staticBar.getMinY()? newY:(int)staticBar.getMinY();
				newY = newY<staticBar.getMaxY()+1? newY:(int)staticBar.getMaxY()+1;
				//Update dynamicBar
				dynamicBar.setCenterY(newY);
				//Save new sliderValue
				sliderValue = (float)(newY-(int)staticBar.getMinY())/(int)staticBar.getHeight();
				break;
			}
			
			for(SliderListener listener : listeners)
				listener.sliderMoved(oldValue, sliderValue, this);
		}
	}
	
	@Override
	public void mouseReleased(int button, int x, int y) {
		if(sliding && button==0) {
			sliding = false;
			for(SliderListener listener : listeners) {
				listener.sliderReleased(this);
			}
		}
	}
	
	//General Methods
	public float getSliderValue() {
		return sliderValue;
	}
	public void setSliderValue(float sliderValue) {
		sliderValue = sliderValue>0f? sliderValue:0f;
		sliderValue = sliderValue<1f? sliderValue:1f;
		
		switch(orientation) {
		case Horizontal:
			dynamicBar.setCenterX((sliderValue*staticBar.getWidth()) + staticBar.getMinX());
			break;
		case Vertical:
			dynamicBar.setCenterY((sliderValue*staticBar.getHeight()) + staticBar.getMinY());
			break;
		}
		this.sliderValue = sliderValue;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		detectOrientation();
		buildSlider();
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		detectOrientation();
		buildSlider();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		buildSlider();
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		buildSlider();
	}
	public Color getxBarColor() {
		return staticBarColor;
	}
	public void setxBarColor(Color xBarColor) {
		this.staticBarColor = xBarColor;
	}
	public Color getyBarColor() {
		return dynamicBarColor;
	}
	public void setyBarColor(Color yBarColor) {
		this.dynamicBarColor = yBarColor;
	}
	public float getStaticBarThickness() {
		return staticBarThickness;
	}
	public void setStaticBarThickness(float staticBarThickness) {
		//Check staticBarThickness against upper and lower bounds
		staticBarThickness = staticBarThickness>0f? staticBarThickness:0f;
		staticBarThickness = staticBarThickness<1f? staticBarThickness:1f;
		
		this.staticBarThickness = staticBarThickness;
		buildSlider();
	}
	public float getDynamicBarThickness() {
		return dynamicBarThickness;
	}
	public void setDynamicBarThickness(float dynamicBarThickness) {
		//Check dynamicBarThickness against upper and lower bounds
		dynamicBarThickness = dynamicBarThickness>0f? dynamicBarThickness:0f;
		dynamicBarThickness = dynamicBarThickness<1f? dynamicBarThickness:1f;
		
		this.dynamicBarThickness = dynamicBarThickness;
		buildSlider();
	}

	//Unimplemented Abstract Methods
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}
	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}
	@Override
	public void mouseWheelMoved(int arg0) {}
	@Override
	public void inputEnded() {}
	@Override
	public void inputStarted() {}
}
