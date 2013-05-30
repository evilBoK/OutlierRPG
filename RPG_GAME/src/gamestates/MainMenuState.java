package gamestates;

import gui.FontBuilder;
import main.RPGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import button.Button;
import button.ButtonListener;
import button.TextButton;

public class MainMenuState extends BasicUIGameState implements ButtonListener {
	private StateBasedGame sb;
	private GameContainer gc;
	
	private Music bgm;
	
	private Image background, foreground;
	private float offset;
	private UnicodeFont titleFont;
	
	private TextButton newGame, loadGame, settings, exit;
	
	public MainMenuState(int stateID) {
		super(stateID);
	}
	
	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		this.offset = 0;

		bgm.loop(1, 0);
		bgm.fade(500, 1, false);
	}
	
	public void leave(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.leave(gc, sb);
		bgm.fade(500, 0, true);
	}

	public void init(GameContainer gc, StateBasedGame sb) throws SlickException{
		super.init(gc, sb);
		this.sb = sb;
		this.gc = gc;
		
		bgm = new Music("/res/Modified Resources/TitleLoop(Ship).ogg");
		background = new Image("/res/Modified Resources/OceanBlue.png");
		foreground = new Image("/res/Modified Resources/OceanWhite.png");
		titleFont = FontBuilder.getInstance("/res/Fonts/alien5/ALIEN5.TTF").addEffect(new ColorEffect(new java.awt.Color(0.75f, 0.75f, 1))).setSize(244).build();
		
		//Set Up the MenuButtons with 4 easy methods each:
		UnicodeFont buttonFont = FontBuilder.getInstance("/res/Fonts/zekton/zekton.ttf").addEffect(new ColorEffect(new java.awt.Color(0.9f, 0.9f, 0.9f))).setSize(54).build();
		newGame	 = new TextButton(buttonFont, "New Game" , 414, 560, this);
		loadGame = new TextButton(buttonFont, "Load Game", 407, 620, this);
		settings = new TextButton(buttonFont, "Settings" , 448, 680, this);
		exit	 = new TextButton(buttonFont, "Exit"	 , 503, 740, this);
		
		UnicodeFont f1 = FontBuilder.getInstance("/res/Fonts/zekton/zekton.ttf").addEffect(new ColorEffect(new java.awt.Color(0.75f, 0.75f, 1))).setSize(54).build();
		UnicodeFont f2 = FontBuilder.getInstance("/res/Fonts/zekton/zekton.ttf").addEffect(new ColorEffect(java.awt.Color.white)).setSize(54).build();
		newGame.setMouseOverFont(f1);
		newGame.setMouseDownFont(f2);
		loadGame.setMouseOverFont(f1);
		loadGame.setMouseDownFont(f2);
		settings.setMouseOverFont(f1);
		settings.setMouseDownFont(f2);
		exit.setMouseOverFont(f1);
		exit.setMouseDownFont(f2);
		
		Sound s1 = new Sound("/res/Audio/SE/Cursor2.ogg");
		Sound s2 = new Sound("/res/Audio/SE/Sword4.ogg");
		newGame.setMouseOverSound(s1);
		newGame.setMouseDownSound(s2);
		loadGame.setMouseOverSound(s1);
		loadGame.setMouseDownSound(s2);
		settings.setMouseOverSound(s1);
		settings.setMouseDownSound(s2);
		exit.setMouseOverSound(s1);
		exit.setMouseDownSound(s2);
		
		//Print the centered coordinates of the buttons
//		System.out.println((1088 - titleFont.getWidth("Outlier"))/2);
//		System.out.println(buttonFont.getLineHeight());
//		System.out.println((1088-newGame.getWidth())/2);
//		System.out.println((1088-loadGame.getWidth())/2);
//		System.out.println((1088-settings.getWidth())/2);
//		System.out.println((1088-exit.getWidth())/2);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sb, int delta){
		delta = delta>20? 20:delta;
		
		offset += ((float)delta)/30f;
		offset %= 1088;
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics g){
		background.draw((int)offset, 0, 2);
		background.draw((int)offset-1088, 0, 2);
		titleFont.drawString(194, 10, "Outlier");
		foreground.draw((int)offset, 0, 2);
		foreground.draw((int)offset-1088, 0, 2);
		
		newGame.render(g);
		loadGame.render(g);
		settings.render(g);
		exit.render(g);
	}

	@Override
	public void buttonClicked(Button target) {
		newGame.setActive(false);
		loadGame.setActive(false);
		settings.setActive(false);
		
		if(target.equals(newGame))
			sb.enterState(RPGame.CHARACTER_CREATION_STATE, new FadeOutTransition(), new FadeInTransition());
		else if(target.equals(loadGame))
			sb.enterState(RPGame.LOAD_GAME_STATE, new FadeOutTransition(), new FadeInTransition());
		else if(target.equals(settings))
			sb.enterState(RPGame.SETTINGS_STATE, new FadeOutTransition(), new FadeInTransition());
		else
			gc.exit();
	}
}
