package textBox;

public interface TextBoxListener {
	public void textSubmitted(String text);
	public void gainedFocus(TextInputBox box);
	public void lostFocus(TextInputBox box);
}
