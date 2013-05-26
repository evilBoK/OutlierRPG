package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public interface GUIComponent {
	public void setActive(Boolean active);
	public void setInput(Input input);
	public void render(Graphics g);
}
