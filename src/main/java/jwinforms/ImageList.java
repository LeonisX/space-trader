package jwinforms;

import java.awt.Color;
import java.awt.image.ColorModel;
import java.util.ArrayList;

public class ImageList
{
	private Image[] images;
	private Size size;
	public Object ColorDepth;

	public ImageList(IContainer components)
	{
	// TODO Auto-generated constructor stub
	}

	public void Draw(Graphics graphics, int x, int y, int imageIndex)
	{
		graphics.DrawImage(images[imageIndex], x, y, new Rectangle(0, 0, size.width, size.height), GraphicsUnit.Pixel);
	}

	public Image[] getImages()
	{
		return images;
	}

	public void setImageStream(ImageListStreamer imageStream)
	{
		ArrayList<Image> al = new ArrayList<Image>();
		for (Image image : imageStream.images)
		{
			al.add(image);
		}
		images = al.toArray(new Image[0]);
	}

	public void setTransparentColor(Color transparentColor)
	{
		for (Image image : images)
			image.setTransparentColor(transparentColor);
	}

	public void setImageSize(Size imageSize)
	{
		size = imageSize;
	}
}

class ColorTranslate
{
	public ColorModel model;
	public int reference;

	public ColorTranslate(ColorModel model, int reference)
	{
		this.model = model;
		this.reference = reference;
	}

	private final static int[] transparent = new int[] { 0, 0, 0, 0 };

	int[] translate(int pixel)
	{
		if (pixel == reference)
			return transparent;
		else
			return new int[] { model.getRed(pixel), model.getGreen(pixel), model.getBlue(pixel), 255 };
	}
}
