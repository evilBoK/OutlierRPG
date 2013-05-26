package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.HieroSettings;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.util.ResourceLoader;

/**
 * A utility class designed to make it easier and more intuitive
 * to create UnicodeFonts.
 * The goal is to encourage the use of the excellent
 * UnicodeFonts over the deprecated TrueTypeFont.
 * @see org.newdawn.slick.UnicodeFont
 *   
 * @author Chronocide
 *
 */
public class FontBuilder{  
  private static final Effect DEFAULT_EFFECT =
//    new FilterEffect(new AffineTransformOp(new AffineTransform(new float[]{1,0,0,1}), null));
    new ColorEffect(Color.WHITE);
  
  private static final int DEFAULT_SIZE  = 24;
  private static final int DEFAULT_STYLE = Font.PLAIN;
  private Font font;
  private boolean isItalic = (DEFAULT_STYLE & Font.ITALIC) == Font.ITALIC;
  private boolean isBold   = (DEFAULT_STYLE & Font.BOLD) == Font.BOLD;
  private int     size     = DEFAULT_SIZE;
  
  private int paddingTop      = 0;
  private int paddingLeft     = 0;
  private int paddingBottom   = 0;
  private int paddingRight    = 0;
  private int paddingAdvanceX = 0;
  private int paddingAdvanceY = 0;
  private int glyphPageWidth  = 512;
  private int glyphPageHeight = 512;
  
  private boolean autoLoad = true;
  private List glyphs  = new ArrayList();
  private StringBuilder glyphString = new StringBuilder();
  private List effects = new ArrayList(); 
  
  
  
  /**
   * There is no good reason for a client to extend this class.</br>
   * Package private constructor prevents this but allows extension
   * within the API to take place.</br>
   */
  FontBuilder(){
    //DO NOTHING
  }
  
  
  
  /**
   * Static factory: gets a <tt>FontBuilder</tt> for creating
   * <tt>UnicodeFont</tt>s.</br>
   * Using a <tt>java.awt.Font</tt> object sets the size, bold,
   * and italic properties of the <tt>UnicodeFont</tt>.
   * @param font the <tt>java.awt.Font</tt> to base the
   * <tt>UnicodeFont</tt> off of. 
   * @return self reference for chaining.
   */
  public static final FontBuilder getInstance(Font font){
    if(font == null) throw new NullPointerException();
    FontBuilder fontBuilder = new FontBuilder();
    fontBuilder.font     = font;
    fontBuilder.size     = font.getSize();
    fontBuilder.isBold   = font.isBold();
    fontBuilder.isItalic = font.isItalic();
    return fontBuilder;
  }
  
  
  
  /**
   * Static factory: gets a <tt>FontBuilder</tt> for creating
   * <tt>UnicodeFont</tt>s.</br>
   * Using a <tt>String</tt> to create an instance uses the
   * default size of 24 and no bold or italic style.
   * @param path a path to a *.ttf file.
   * @return self reference for chaining.
   */
  public static final FontBuilder getInstance(String path)
      throws SlickException{
    Font font;
    try {
      font = Font.createFont(Font.TRUETYPE_FONT,
                             ResourceLoader.getResourceAsStream(path));
    }catch(FontFormatException ex){
       throw new SlickException("Invalid font: " + path, ex);
    }catch(IOException ex){
      throw new SlickException("Error reading font: " + path, ex);
    }
    
    FontBuilder fb = getInstance(font);
    fb.size = DEFAULT_SIZE;
    fb.isItalic = (DEFAULT_STYLE & Font.ITALIC) == Font.ITALIC;
    fb.isBold   = (DEFAULT_STYLE & Font.BOLD) == Font.BOLD;
    return fb;
  }
  
  
  
  /**
   * Adds an effect to the <tt>UnicodeFont</tt>.</br>
   * <tt>UnicodeFont</tt> requires at least one <tt>Effect</tt> in
   * order to function.
   * If no effect is added a default <tt>Effect</tt> is used. The
   * default will be non intrusive (that is the font should appear
   * to not have any affect applied) but the actual <tt>Effect</tt>
   * used is not guaranteed to always be the same.</br>
   * @param e the effect to add.
   * @return self reference for chaining.
   */
  public FontBuilder addEffect(Effect e){
    if(e==null)throw new NullPointerException();
    effects.add(e);
    return this;
  }
  
  
  
  /**
   * Adds a contiguous set of unicode glyphs using code points
   * (inclusive).</br>
   * @see UnicodeFont#addGlyphs(int, int)
   * @param startPoint the code point of the first glyph to include
   * @param endPoint the code point of the last glyph to include
   * @return self reference for chaining.
   */
  public FontBuilder addGlyphs(int startPoint, int endPoint){
    this.glyphs.add(new Integer(startPoint));
    this.glyphs.add(new Integer(endPoint));
    return this;
  }
  
  
  
  /**
   * Adds all the glyphs required to render the String str.</br>
   * @see UnicodeFont#addGlyphs(String)
   * @param str the String containing the glyphs to be added.
   * @return self reference for chaining.
   */
  public FontBuilder addGlyphs(String str){
    glyphString.append(str);
    return this;
  }
  
  
  
  /**
   * This method disables autoload.</br>
   * When auto load is enabled all glyphs set to be added will be
   * loaded when the <tt>UnicodeFont</tt> is built.</br>
   * Also if no glyphs are specified when autoLoad is enabled, the
   * standard ASCII glyphs will be loaded automatically.</br>
   * By default autloLoad is enabled, and there is therefore no
   * companion method "disableAutoLoad()"
   * @see UnicodeFont#loadGlyphs()
   * @return self reference for chaining.
   */
  public FontBuilder disableAutoLoad(){
    this.autoLoad = false;
    return this;
  }
  
  
  
  /**
   * Turns the bold style on or off.</br>
   * Non-bold is default.
   * @param bold true if you want bold font else false.
   * @return self reference for chaining.
   */
  public FontBuilder setBold(boolean isBold){
    this.isBold = isBold;
    return this;
  }



  /**
   * Sets the height of the backing textures. Default is 512.</br>
   * @see UnicodeFont#setGlyphPageHeight(int)
   * @param glyphPageHeight The width of the glyph pages in this font.
   * @return self reference for chaining.
   */
  public FontBuilder setGlyphPageHeight(int glyphPageHeight){
    this.glyphPageHeight = glyphPageHeight;
    return this;
  }
  
  
  
  /**
   * Sets the width of the backing textures. Default is 512.</br>
   * @see UnicodeFont#setGlyphPageWidth(int)
   * @param glyphPageWidth The width of the glyph pages in this font.
   * @return self reference for chaining.
   */
  public FontBuilder setGlyphPageWidth(int glyphPageWidth){
    this.glyphPageWidth = glyphPageWidth;
    return this;
  }


  
  /**
   * Turns the italic style on or off.</br>
   * Non-italic is default.
   * @param isItalic true if you want italic font else false.
   * @return self reference for chaining.
   */
  public FontBuilder setItalic(boolean isItalic){
    this.isItalic = isItalic;
    return this;
  }


  
  /**
   * Sets the additional amount to offset glyphs on the x axis.</br>
   * This is typically set to a negative number when left or right
   * padding is used so that glyphs are not spaced too far apart.
   * @see UnicodeFont#setPaddingAdvanceX(int)
   * @param paddingAdvanceX The padding applied for each horizontal
   * advance (i.e. when a glyph is rendered).
   * @return self reference for chaining.
   */
  public FontBuilder setPaddingAdvanceX(int paddingAdvanceX){
    this.paddingAdvanceX = paddingAdvanceX;
    return this;
  }


  
  /**
   * Sets the additional amount to offset a line of text on the
   * y axis.</br> This is typically set to a negative number when
   * top or bottom padding is used so that lines of text are not
   * spaced too far apart.
   * @see UnicodeFont#setPaddingAdvanceY(int)
   * @param paddingAdvanceY The padding applied for each vertical
   * advance (i.e. when a glyph is rendered).
   * @return self reference for chaining.
   */
  public FontBuilder setPaddingAdvanceY(int paddingAdvanceY){
    this.paddingAdvanceY = paddingAdvanceY;
    return this;
  }



  /**
   * Sets the padding below a glyph on the GlyphPage to allow for
   * effects to be drawn.</br>
   * @see UnicodeFont#setPaddingBottom(int)
   * @param paddingBottom The padding at the bottom of the glyphs.
   * when drawn.
   * @return self reference for chaining.
   */
  public FontBuilder setPaddingBottom(int paddingBottom){
    this.paddingBottom = paddingBottom;
    return this;
  }



  /**
  * Sets the padding to the left of a glyph on the GlyphPage to
  * allow for effects to be drawn.</br>
  * @see UnicodeFont#setPaddingLeft(int)
  * @param paddingLeft The padding at the left of the glyphs when
  * drawn.
  * @return self reference for chaining.
  */
  public FontBuilder setPaddingLeft(int paddingLeft){
    this.paddingLeft = paddingLeft;
    return this;
  }



  /**
   * Sets the padding to the right of a glyph on the GlyphPage to
   * allow for effects to be drawn.</br>
   * @see UnicodeFont#setPaddingRight(int)
   * @param paddingRight The padding at the right of the glyphs
   * when drawn.
   * @return self reference for chaining.
   */
  public FontBuilder setPaddingRight(int paddingRight){
    this.paddingRight = paddingRight;
    return this;
  }
  
  
  
  /**
   * Sets the padding above a glyph on the GlyphPage to allow for
   * effects to be drawn.</br>
   * @see UnicodeFont#setPaddingTop(int)
   * @param paddingTop The padding at the top of the glyphs when
   * drawn.
   * @return self reference for chaining.
   */
  public FontBuilder setPaddingTop(int paddingTop){
    this.paddingTop = paddingTop;
    return this;
  }
  
  
  
  /**
   * Sets the size of the <tt>UnicdeFont</tt> the default is 24.</br>
   * @param size the font size in pnts.
   * @return self reference for chaining.
   */
  public FontBuilder setSize(int size){
    this.size = size;
    return this;
  }
  
  
  
  /**
   * Apply settings to the <tt>UnicodeFont</tt> based on a
   * <tt>HieroSettings</tt> object.</br>
   * @param settings the <tt>HieroSettings</tt> to be applied to
   * the <tt>UnicodeFont</tt>. 
   * @return self reference for chaining.
   */
  public FontBuilder setStyle(HieroSettings settings){
    this.size     = settings.getFontSize();
    this.isBold   = settings.isBold();
    this.isItalic = settings.isItalic();
    
    this.paddingTop      = settings.getPaddingTop();
    this.paddingLeft     = settings.getPaddingLeft();
    this.paddingBottom   = settings.getPaddingBottom();
    this.paddingRight    = settings.getPaddingRight();
    this.paddingAdvanceX = settings.getPaddingAdvanceX();
    this.paddingAdvanceY = settings.getPaddingAdvanceY();
    this.glyphPageWidth  = settings.getGlyphPageWidth();
    this.glyphPageHeight = settings.getGlyphPageHeight();
    
    for(int i = 0; i<settings.getEffects().size(); i++){
      this.addEffect((Effect)settings.getEffects().get(i));
    }

    return this;
  }
  
  
  
  /**
   * 
   * @param path a path to a Heiro Settings file.</br>
   * @return self reference for chaining.
   * @throws SlickException 
   */
  public FontBuilder setStyle(String path)throws SlickException{
    return setStyle(new HieroSettings(path));
  }
  
  
  
  /**
   * Builds a <tt>UnicodeFont</tt>.</br>
   * @return a new <tt>UnicodeFont</tt> instance.</br>
   * @throws SlickException 
   */
  public UnicodeFont build()throws SlickException{    
    UnicodeFont uf = new UnicodeFont(font, size, isBold, isItalic);
    
    uf.setGlyphPageHeight(glyphPageHeight);
    uf.setGlyphPageWidth(glyphPageWidth);
    uf.setPaddingAdvanceX(paddingAdvanceX);
    uf.setPaddingAdvanceY(paddingAdvanceY);
    uf.setPaddingBottom(paddingBottom);
    uf.setPaddingLeft(paddingLeft);
    uf.setPaddingRight(paddingRight);
    uf.setPaddingTop(paddingTop);
    
    
    //EFFECTS
    if(effects.isEmpty()){
      effects.add(DEFAULT_EFFECT);
    }
    uf.getEffects().addAll(effects);
    
    //GLYPHS
    String str = this.glyphString.toString();
    if(glyphs.isEmpty() && str.length()==0){
      if(autoLoad){
        uf.addAsciiGlyphs();
      }
    }else{
      for(int i =0; i < glyphs.size(); i+=2){
        uf.addGlyphs(((Integer)glyphs.get(i)).intValue(),
                     ((Integer)glyphs.get(i+1)).intValue());
      }
      uf.addGlyphs(str);
    }
    if(autoLoad){
      uf.loadGlyphs();
    }
    
    return uf;
  }
}