package slider;


public interface SliderListener {
	public void sliderMoved(float oldValue, float newValue, Slider target);
	public void sliderPressed(Slider target);
	public void sliderReleased(Slider target);
}
