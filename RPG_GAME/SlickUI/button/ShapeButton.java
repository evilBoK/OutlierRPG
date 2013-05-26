package button;

import gamestates.BasicUIGameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class ShapeButton extends Button {
	//ShapeButton
	private Shape displayShape, defaultShape, mouseOverShape, mouseDownShape;
	
	public ShapeButton(Shape shape, BasicUIGameState gameState) {
		super((int)shape.getX(),(int)shape.getY(),(int)shape.getWidth(),(int)shape.getHeight(), gameState);
		
		this.defaultShape = shape;
		this.mouseOverShape = shape;
		this.mouseDownShape = shape;
		this.clickBox = shape;

		this.displayShape = shape;
	}
	
	public void render(Graphics g) {
		Color c = g.getColor();
		g.setColor(c.multiply(filter));
		g.fill(displayShape);
		g.setColor(c);
	}
	
	public void setMouseOverShape(Shape shape) {
		this.mouseOverShape = shape;
	}
	
	public void setMouseDownShape(Shape shape) {
		this.mouseDownShape = shape;
	}
	
	public void setState(State state) {
		this.state = state;
		
		switch(state) {
		case Mouse_Over:
			this.displayShape = mouseOverShape;
			break;
		case Mouse_Down:
			this.displayShape = mouseDownShape;
			break;
		case Default:
			this.displayShape = defaultShape;
			break;
		}
	}
}
