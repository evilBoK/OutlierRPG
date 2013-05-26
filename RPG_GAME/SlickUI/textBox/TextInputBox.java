package textBox;

import gamestates.BasicUIGameState;
import gui.GUIComponent;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


public class TextInputBox implements GUIComponent, MouseListener, KeyListener {
	private Rectangle body;
	private UnicodeFont font;
	private ArrayList<String> lines;
	private int cLine, cChar;
	
	private State state;
	private Color renderColor, defaultColor, selectedColor, submittedColor;
	
	private boolean acceptingInput, renderCursor;
	private short[] keyDown;
	private int inputTimer, inputTicker, cursorTimer;
	private ArrayList<TextBoxListener> listeners;
	
	enum State {
		Default, Selected, Submitted
	}
	
	public TextInputBox(int x, int y, int width, int height, UnicodeFont font, BasicUIGameState gameState) {
		this.body = new Rectangle (x, y, width, height);
		this.font = font;
		
		lines = new ArrayList<String>();
		lines.add("");
		listeners = new ArrayList<TextBoxListener>();
		
		acceptingInput = false;
		
		cLine = 0;
		cChar = 0;
		renderColor = null;
		defaultColor = null;
		selectedColor = null;
		submittedColor = null;
		setState(State.Default);
		
		if(gameState instanceof TextBoxListener)
			addListener((TextBoxListener)gameState);
		//Is this cast legal?
		
		gameState.register(this);
	}
	
	
	
	//GUIComponent
	@Override
	public void setActive(Boolean active) {
		this.acceptingInput = active;
	}
	@Override
	public void setInput(Input input) {
		input.addKeyListener(this);
		input.addMouseListener(this);
	}
	public void update(int delta) throws SlickException {
		delta = delta<1? 1 : delta>20? 20 : delta;
		cursorTimer += delta;
		if(keyDown[0] != (-1) || keyDown[1] != (-1))
			inputTimer += delta;
		
		if(cursorTimer >= 500) {
			renderCursor = !renderCursor;
			cursorTimer -= 500;
		}
		if(inputTimer > 800) {
			inputTicker += delta;
			if(inputTicker >= 100) {
				keyPressed((int)keyDown[0], (char)keyDown[1]);
				inputTicker -= 100;
				renderCursor = true;
			}
		}	
	}
	@Override
	public void render(Graphics g) {
		float x = body.getX(), y = body.getY(), step = font.getLineHeight();
		if(renderColor!=null) {
			for(int i=0; i<lines.size(); i++)
				font.drawString(x, y+i*step, lines.get(i), renderColor);
		}
		else {
			for(int i=0; i<lines.size(); i++)
				font.drawString(x, y+i*step, lines.get(i), Color.white);
		}
		if(state.equals(State.Selected) && renderCursor)
			font.drawString(x+font.getWidth(lines.get(cLine).substring(0, cChar))-(font.getLeading()*2), y+cLine*step, "|");
	}
	
	//TextBoxListener Methods
	public void addListener(TextBoxListener listener) {
		listeners.add(listener);
	}
	public void removeListener(TextBoxListener listener) {
		listeners.remove(listener);
	}
	public void submit() {
		StringBuilder text = new StringBuilder();
		for(int i=0; i<lines.size(); i++)
			text.append(lines.get(i));
			
		for(TextBoxListener listener : listeners)
			listener.textSubmitted(text.toString());
		setState(State.Submitted);
	}

	
	//KeyListener Methods
	@Override
	public boolean isAcceptingInput() {
		return acceptingInput;
	}
	@Override
	public void keyPressed(int keyID, char c) {
		if(state.equals(State.Selected)) {
			
			keyDown[0] = (short) keyID;
			keyDown[1] = (short) c;
			
			if((c>=48 && c<= 57) || (c>=65 && c<= 122)) {
				if(addChar(c))
					cursorForwards();
			}
			else {
				switch(keyID) {
				case 14:
					if(removeChar())
						cursorBackwards();
					break;
				case 28:
					submit();
					break;
				case 57:
					if(addSpace())
						cursorForwards();
					break;
				case 200:
					cursorUp();
					break;
				case 208:
					cursorDown();
					break;
				case 203:
					cursorBackwards();
					break;
				case 205:
					cursorForwards();
					break;
				}
			}
		}	
	}
	@Override
	public void keyReleased(int keyID, char c) {
		if(state.equals(State.Selected) && (keyDown[0] == keyID || keyDown[1] == c) ) {
			keyDown[0] = (-1);
			keyDown[1] = (-1);
			inputTimer = 0;
		}
	}

	
	//MouseListener Methods
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(int button, int x, int y) {
		if(button == 0 && body.contains(x, y) && !state.equals(State.Selected)) {
			for(TextBoxListener listener : listeners)
				listener.gainedFocus(this);
			setState(State.Selected);
		}
		else if (!(button == 0 && body.contains(x, y)) && state.equals(State.Selected)) {
			for(TextBoxListener listener : listeners)
				listener.lostFocus(this);
			setState(State.Default);
		}
	}

	
	
	
	//General Methods
	private void setState(State state) {
		this.state = state;
		switch(state) {
		case Default:
			renderCursor = false;
			inputTimer = 0;
			inputTicker = 0;
			cursorTimer = 0;
			keyDown = new short[]{(-1), (-1)};
			renderColor = defaultColor;
			break;
		case Selected:
			renderCursor = true;
			renderColor = selectedColor;
			break;
		case Submitted:
			renderCursor = false;
			inputTimer = 0;
			inputTicker = 0;
			cursorTimer = 0;
			keyDown = new short[]{(-1), (-1)};
			renderColor = submittedColor;
			break;
		}
	}
	
	public boolean setText(String text) {
		ArrayList<String> result = new ArrayList<String>();
		String[] words = text.split(" ");
		int lines = 0, maxLines = (int) (body.getHeight()/font.getLineHeight());
		
		String currentLine = "";
		for(int i=0; i<words.length; i++) {
			String word = words[i];
			if(font.getWidth(word) > body.getWidth()-font.getSpaceWidth())
				return false;
			else {
				String longerLine = currentLine.concat(word);
				if(font.getWidth(longerLine) <= body.getWidth()-font.getSpaceWidth())
					currentLine = longerLine.concat(" ");
				else if (lines++ < maxLines){
					result.add(currentLine);
					currentLine = word.concat(" ");
				}
				else
					return false;
			}
		}
		
		if(lines < maxLines)
			result.add(currentLine);
		else
			return false;
		
		this.lines = result;
		return true;
	}
	
	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	public Color getSubmittedColor() {
		return submittedColor;
	}

	public void setSubmittedColor(Color submittedColor) {
		this.submittedColor = submittedColor;
	}

	private void cursorForwards() {
		if(cChar < lines.get(cLine).length())
			cChar++;
		else if(cLine+1 < lines.size()) {
			cChar -= (lines.get(cLine).length()-1);
			cLine++;
		}
	}
	
	private void cursorBackwards() {
		if( ((cChar > 1) || (cChar==1 && cLine==0)) && cLine<lines.size() )
			cChar--;
		else if(cLine > 0) {
			cLine--;
			cChar = lines.get(cLine).length();	
		}
	}
	
	private void cursorUp() {
		if(cLine>0)
			cLine--;
		cChar = cChar<lines.get(cLine).length()? cChar:lines.get(cLine).length();
	}
	
	private void cursorDown() {
		if(cLine<lines.size()-1)
			cLine++;
		cChar = cChar<lines.get(cLine).length()? cChar:lines.get(cLine).length();
	}
	
	public void clearText() {
		lines = new ArrayList<String>();
		lines.add("");
		cLine = 0;
		cChar = 0;
	}
	
	public boolean addChar(char c) {	
		ArrayList<String> newLines = new ArrayList<String>(lines);
		
		String currentString = newLines.get(cLine);
		newLines.set(cLine, currentString.substring(0, cChar).concat(Character.toString(c).concat(currentString.substring(cChar))));
		
		int breakIndex;
		String line, beforeBreak="", afterBreak="";
		for(int i=cLine; i<newLines.size(); i++) {
			line = newLines.get(i);
			if( (font.getWidth(line)+font.getSpaceWidth() <= body.getWidth()) || (line.endsWith(" ") && font.getWidth(line) <= body.getWidth()) )
				break;
			breakIndex = line.lastIndexOf(" ", line.length()-2);
			if(breakIndex >= 0) {
				beforeBreak = line.substring(0, breakIndex+1);
				afterBreak = line.substring(breakIndex+1);
				
				newLines.set(i, beforeBreak);
				if(i+1 < newLines.size())
					newLines.set(i+1, afterBreak.concat(newLines.get(i+1)));
				else if(font.getLineHeight()*(newLines.size()+1) < body.getHeight())
					newLines.add(afterBreak);
				else
					return false;
			}
			else
				return false;
		}

		lines = newLines;
		return true;
	}
	
	public boolean removeChar() {
		ArrayList<String> newLines = new ArrayList<String>(lines);
		
		String currentString = newLines.get(cLine);
		if(cLine==0 && cChar==0) return false;
		newLines.set(cLine, currentString.substring(0, cChar-1).concat(currentString.substring(cChar)));
		
		if(cLine == newLines.size()-1 && newLines.get(cLine).length()==0 && cLine>0)
			newLines.remove(cLine);
		else {
			int breakIndex;
			String line, nextLine, beforeBreak="", afterBreak="";
			for(int i=(cLine>0? cLine-1:cLine); i<newLines.size()-1; i++) {
				line = newLines.get(i);
				nextLine = newLines.get(i+1);
				
				breakIndex = nextLine.indexOf(' ', 1);
				if(breakIndex < 0)
					breakIndex = nextLine.length();
				else
					breakIndex++;
				
				beforeBreak = nextLine.substring(0, breakIndex);
				afterBreak = nextLine.substring(breakIndex);
				
				if( (font.getWidth(line.concat(beforeBreak))+font.getSpaceWidth() <= body.getWidth()) || (beforeBreak.endsWith(" ") && font.getWidth(line.concat(beforeBreak)) <= body.getWidth()) ){
					newLines.set(i, line.concat(beforeBreak));
					if(afterBreak.length()>0)
						newLines.set(i+1, afterBreak);
					else
						newLines.remove(i+1);
				}
				else
					break;
			}
		}
		
		lines = newLines;
		return true;
	}
	
	public boolean addSpace(){
		ArrayList<String> newLines = new ArrayList<String>(lines);
		
		String currentString = newLines.get(cLine);
		newLines.set( cLine, currentString.substring(0, cChar).concat(" ").concat(currentString.substring(cChar)) );
		
		int breakIndex;
		String line, beforeBreak, afterBreak;
		for(int i=cLine; i<newLines.size(); i++) {
			line = newLines.get(i);
			if( ((font.getWidth(line)+font.getSpaceWidth()) <= body.getWidth()) || ((cChar+1 == line.length()) && (font.getWidth(line) <= body.getWidth())))
				break;
			breakIndex = line.lastIndexOf(" ", line.length()-2);
			if(breakIndex >= 0) {
				if(line.charAt(breakIndex-1) != ' ')
					breakIndex++;
				beforeBreak = line.substring(0, breakIndex);
				afterBreak = line.substring(breakIndex);
				
				newLines.set(i, beforeBreak);
				if(i+1 < newLines.size())
					newLines.set(i+1, afterBreak.concat(newLines.get(i+1)));
				else if(font.getLineHeight()*(newLines.size()+1) < body.getHeight())
					newLines.add(afterBreak);
				else
					return false;
			}
			else
				return false;
		}
		
		lines = newLines;
		return true;
	}

	//Unimplemented Abstract Methods
	@Override
	public void inputEnded() {}
	@Override
	public void inputStarted() {}
	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {}
	@Override
	public void mouseWheelMoved(int arg0) {}

}
