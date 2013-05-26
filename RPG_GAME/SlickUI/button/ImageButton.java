package button;

import gamestates.BasicUIGameState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ImageButton extends Button {
	//ImageButton
	private Image displayImage, defaultImage, mouseOverImage, mouseDownImage;
	
	public ImageButton(Image image, int x, int y, BasicUIGameState gameState) {
		super(x,y,image.getWidth(),image.getHeight(), gameState);
		
		this.defaultImage = image;
		this.mouseOverImage = image;
		this.mouseDownImage = image;
		
		this.displayImage = image;
	}
	
	public void render(Graphics g) {
		displayImage.draw(x, y, filter);
	}
	
	public void setMouseOverImage(Image image) {
		this.mouseOverImage = image;
	}
	
	public void setMouseDownImage(Image image) {
		this.mouseDownImage = image;
	}
	
	public void setState(State state) {
		this.state = state;
		
		switch(state) {
		case Mouse_Over:
			this.displayImage = mouseOverImage;
			break;
		case Mouse_Down:
			this.displayImage = mouseDownImage;
			break;
		case Default:
			this.displayImage = defaultImage;
			break;
		}
	}
}
