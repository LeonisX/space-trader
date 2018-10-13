package jwinforms;

import java.awt.Color;

public abstract class Image
{
	abstract public java.awt.Image asSwingImage();

	abstract public int getHeight();

	abstract public int getWidth();
	
	abstract public void setTransparentColor(Color transparentColor);
}
