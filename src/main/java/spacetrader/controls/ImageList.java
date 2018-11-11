package spacetrader.controls;

import java.awt.*;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import spacetrader.controls.enums.GraphicsUnit;

public class ImageList {

    //TODO used???
    private Object colorDepth;
    private Image[] images;
    private int width;
    private int height;

    public void draw(Graphics graphics, int x, int y, int imageIndex) {
        graphics.drawImage(images[imageIndex], x, y, new Rectangle(0, 0, width, height), GraphicsUnit.PIXEL);
    }

    public Image[] getImages() {
        return images;
    }

    public void setColorDepth(Object colorDepth) {
        this.colorDepth = colorDepth;
    }

    public void setImageStream(ImageListStreamer imageStream) {
        ArrayList<Image> al = new ArrayList<>();
        for (Image image : imageStream.images) {
            al.add(image);
        }
        images = al.toArray(new Image[0]);
    }

    public void setTransparentColor(Color transparentColor) {
        for (Image image : images) {
            image.setTransparentColor(transparentColor);
        }
    }

    public void setImageSize(int width, int height) {
        this.width = width;
        this.height = height;
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
        if (pixel == reference) {
            return transparent;
        } else {
            return new int[]{model.getRed(pixel), model.getGreen(pixel), model.getBlue(pixel), 255};
        }
    }
}
