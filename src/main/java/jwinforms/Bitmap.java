package jwinforms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import javax.imageio.ImageIO;

public class Bitmap extends Image implements Icon, Serializable
{
	private transient BufferedImage image;

	private static final long serialVersionUID = 2134761799614301086L;
	private final URL imageUrl;
	private Color transparent = null;

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		image = ImageIO.read(imageUrl);
		setTransparentColor(transparent);
	}

	public Bitmap(Image source)
	{
		image = ((Bitmap)source).image;
		imageUrl = ((Bitmap)source).imageUrl;
	}

	public Bitmap(String fileName)
	{
		try
		{
			File input = new File(fileName);
			imageUrl = input.toURI().toURL();
			image = ImageIO.read(input);
		} catch (IOException e)
		{
			throw new Error(e);
		}
	}

	public Bitmap(URL imageUrl)
	{
		try
		{
			this.imageUrl = imageUrl;
			image = ImageIO.read(imageUrl);
		} catch (IOException e)
		{
			throw new Error(e);
		}
	}

	private transient boolean transSet = false;

	@Override
	public void setTransparentColor(Color transparentColor)
	{
		if (transSet)
			throw new Error("setTransparentColor called twice");
		transSet = true;

		if (transparentColor == null)
			return;

		// Don't yet support all colors.
		if (!transparentColor.equals(Color.white))
		{
			BufferedImage bi = (BufferedImage)asSwingImage();
			int[] pixel = bi.getRaster().getPixel(0, 0, new int[4]);
			System.out.print("Trying to set unknown background: color at 0x0:");
			for (int i : pixel)
			{
				System.out.print(" " + i);
			}
			System.out.println();
			//		throw new Error("This bg color not yet supported");
			return;
		}

		int transparent = 0;
		this.transparent = transparentColor;

		BufferedImage bi = (BufferedImage)this.asSwingImage();
		BufferedImage newImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

		int[] arr = new int[1];
		WritableRaster raster1 = bi.getRaster();
		WritableRaster raster2 = newImage.getRaster();
		ColorTranslate translate = new ColorTranslate(bi.getColorModel(), transparent);
		for (int x = 0; x < getWidth(); x++)
		{
			for (int y = 0; y < getHeight(); y++)
			{
				raster1.getPixel(x, y, arr);
				raster2.setPixel(x, y, translate.translate(arr[0]));
			}
		}

		image = newImage;
	}

	public int ToArgb(int col, int row)
	{
		// note that alpha is ignored.
		return image.getRGB(col, row);
	}

	@Override
	public int getHeight()
	{
		return image.getHeight();
	}

	@Override
	public int getWidth()
	{
		return image.getWidth();
	}

	@Override
	public java.awt.Image asSwingImage()
	{
		return image;
	}
}
