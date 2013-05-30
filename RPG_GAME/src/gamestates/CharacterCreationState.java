package gamestates;

import gui.FontBuilder;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.StateBasedGame;

import textBox.TextBoxListener;
import textBox.TextInputBox;

public class CharacterCreationState extends BasicUIGameState implements TextBoxListener {
	private StateBasedGame sb;
	
	private Music bgm;
	private Sound accept, reject;
	private Image back1, back2;
	
	private TextInputBox nameField;
	private RoundedRectangle nameBox, customBox;
	private Color translucentGrey;
	
	private String name;
	private Image fEyes,fFace, fHair, fNose, fMouth, fEars, fEyebrows, fAcc, fGlasses, fTattoo, fKimono;
	private Image mHead, mAcc, mCloak, mEars, mEyes, mGlasses, mKemono, mTail, mWing, mClothes, mHair, mBeard;
	
	public CharacterCreationState(int stateID) {
		super(stateID);
	}
	
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException{
		super.init(gc, sb);
		this.sb = sb;
		
		bgm = new Music("/res/Modified Resources/BalletElevinLoop.ogg");
		back1 = new Image("/res/Graphics/BattleBacks1/Wood1.png");
		back2 = new Image("/res/Graphics/BattleBacks2/Room2.png");
		
		accept = new Sound("/res/Audio/SE/Decision2.ogg");
		reject = new Sound("/res/Audio/SE/Buzzer1.ogg");
		
		nameField = new TextInputBox(394, 40, 300, 62, FontBuilder.getInstance("/res/Fonts/VLGothic/VL-Gothic-Regular.ttf").setSize(48).build(), this);
		nameField.setText("Enter Name");
		nameBox = new RoundedRectangle(344, 20, 400, 100, 30);
		translucentGrey = new Color(0.25f, 0.25f, 0.25f, 0.5f);
		
		customBox = new RoundedRectangle(65, 175, 450, 600, 50);
		
		name = "";
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
		back1.draw(0,0,2);
		back2.draw(0,0,2);
		
		Color c = g.getColor();
		g.setColor(translucentGrey);
		g.fill(nameBox);
		g.fill(customBox);
		g.setColor(Color.white);
		g.draw(nameBox);
		g.draw(customBox);
		g.setColor(c);
		
		nameField.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		nameField.update(delta);
	}

	@Override
	public void textSubmitted(TextInputBox box, String text) {
		text = text.trim();
		if(text.length()>=1) {
			accept.play();
			name = text;
		}
		else {
			reject.play();
			box.setText("Enter Name");
		}
	}

	@Override
	public void gainedFocus(TextInputBox box) {
		if(box.isDefault())
			box.clearText();
	}

	@Override
	public void lostFocus(TextInputBox box) {
		box.submit();
	}

}
