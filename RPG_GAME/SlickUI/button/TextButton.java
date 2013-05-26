package button;

import gamestates.BasicUIGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/*USING THE FILTER ON A TEXTBUTTON DOES NOT BLEND, BUT 'LAYER' THE COLORS, RESULTING IN DARKER TONES
 * This can be solved by making button's setFilterColor abstract and implementing it thus:
 * 
 * Color c = Color.white;
 *		for(Object o: displayFont.getEffects()) {
 *			if(o instanceof ColorEffect) {
 *				final ColorEffect e = (ColorEffect)o;
 *				java.awt.Color jc = e.getColor();
 *				c *= new Color(jc.getRed(), jc.getGreen(), jc.getBlue());
 *			}
 *		}
 *
 *		AND IN RENDER():
 *java.util.List l = displayFont.getEffects();
 *displayFont.getEffects().clear();
 *displayFont.drawString(x, y, text, filter.multiply(c));
 *displayFont.getEffects().add(l);
 */

public class TextButton extends Button {
	//TextButton
	private UnicodeFont displayFont, defaultFont, mouseOverFont, mouseDownFont;
	private String text;
	
	public TextButton(UnicodeFont font, String text, int x, int y, BasicUIGameState gameState) {
		super(x, y, font.getWidth(text), font.getLineHeight(), gameState);
		
		this.text = text;
		this.defaultFont = font;
		this.mouseOverFont = font;
		this.mouseDownFont = font;
		
		this.displayFont = font;
	}
	
	public void render(Graphics g) {
		displayFont.drawString(x, y, text, filter);
	}
	
	public void setMouseOverFont(UnicodeFont font) {
		this.mouseOverFont = font;
	}
	
	public void setMouseDownFont(UnicodeFont font) {
		this.mouseDownFont = font;
	}
	
	public void setState(State state) {
		this.state = state;
		
		switch(state) {
		case Mouse_Over:
			this.displayFont = mouseOverFont;
			break;
		case Mouse_Down:
			this.displayFont = mouseDownFont;
			break;
		case Default:
			this.displayFont = defaultFont;
			break;
		}
	}
}
