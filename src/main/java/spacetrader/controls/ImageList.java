package spacetrader.controls;

import java.awt.*;
import java.awt.image.ColorModel;
import java.util.ArrayList;

public class ImageList {
    public Object ColorDepth;
    private Image[] images;
    private Size size;

    public ImageList(IContainer components) {
        // TODO Auto-generated constructor stub
    }

    public void Draw(spacetrader.controls.Graphics graphics, int x, int y, int imageIndex) {
        graphics.DrawImage(images[imageIndex], x, y, new Rectangle(0, 0, size.width, size.height), GraphicsUnit.Pixel);
    }

    public Image[] getImages() {
        return images;
    }

    public void setImageStream(ImageListStreamer imageStream) {
        ArrayList<Image> al = new ArrayList<Image>();
        for (Image image : imageStream.images) {
            al.add(image);
        }
        images = al.toArray(new Image[0]);
    }

    public void setTransparentColor(Color transparentColor) {
        for (Image image : images)
            image.setTransparentColor(transparentColor);
    }

    public void setImageSize(Size imageSize) {
        size = imageSize;
    }
}

class ColorTranslate {
    private final static int[] transparent = new int[]{0, 0, 0, 0};
    private ColorModel model;
    private int reference;

    ColorTranslate(ColorModel model, int reference) {
        this.model = model;
        this.reference = reference;
    }

    int[] translate(int pixel) {
        if (pixel == reference)
            return transparent;
        else
            return new int[]{model.getRed(pixel), model.getGreen(pixel), model.getBlue(pixel), 255};
    }
}
