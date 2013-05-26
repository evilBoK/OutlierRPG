package gamestates;

import gui.FontBuilder;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import button.ImageButton;
import button.ShapeButton;
import button.TextButton;

public class MainMenuState extends BasicUIGameState implements MouseListener{

	private UnicodeFont[] potentialFonts;
	private UnicodeFont displayFont;
	private int fontIndex;
	
	private Image backdrop;
	
	public MainMenuState(int stateID) {
		super(stateID);
	}

	public void init(GameContainer gc, StateBasedGame sb) throws SlickException{
		super.init(gc, sb);

		backdrop = new Image("/res/Graphics/Parallaxes/Ocean1.png");
		
		potentialFonts = new UnicodeFont[9];
		fontIndex = 0;
		
		potentialFonts[0] = FontBuilder.getInstance("/res/Fonts/13misa/13_Misa.TTF")			.setSize(54).build();
		potentialFonts[1] = FontBuilder.getInstance("/res/Fonts/abstract/ABSTRACT.TTF")			.setSize(24).build();
		potentialFonts[2] = FontBuilder.getInstance("/res/Fonts/alien5/ALIEN5.TTF")				.setSize(92).build();
		potentialFonts[3] = FontBuilder.getInstance("/res/Fonts/alienfur/AlienFur.ttf")			.setSize(92).build();
		potentialFonts[4] = FontBuilder.getInstance("/res/Fonts/civitype_fg/civitype.ttf")		.setSize(92).build();
		potentialFonts[5] = FontBuilder.getInstance("/res/Fonts/coalition/Coalition_v2.ttf")	.setSize(54).build();
		potentialFonts[6] = FontBuilder.getInstance("/res/Fonts/stark/Stark.ttf")				.setSize(92).build();
		potentialFonts[7] = FontBuilder.getInstance("/res/Fonts/VLGothic/VL-Gothic-Regular.ttf").setSize(54).build();
		potentialFonts[8] = FontBuilder.getInstance("/res/Fonts/zekton/zekton rg.ttf")			.setSize(78).build();
		
		displayFont = potentialFonts[fontIndex];
	}
	
	public void update(GameContainer gc, StateBasedGame sb, int delta){

	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		displayFont = potentialFonts[fontIndex = (fontIndex+1)%9];
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics g){
		backdrop.draw(0, 0);
		displayFont.drawString(272-displayFont.getWidth("Outlier")/2, 25, "Outlier", Color.black);
		g.drawString("FontIndex: ".concat(Integer.toString(fontIndex)), 10, 520);
	}
}
