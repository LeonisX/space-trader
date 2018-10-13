package jwinforms;

public enum ContentAlignment
{
	TopLeft(Horizontal.top, Vertical.left), TopCenter(Horizontal.top, Vertical.center), TopRight(Horizontal.top,
			Vertical.right), MiddleLeft(Horizontal.middle, Vertical.left), MiddleCenter(Horizontal.middle,
			Vertical.center), MiddleRight(Horizontal.middle, Vertical.right), ButtomLeft(Horizontal.buttom,
			Vertical.left), ButtomCenter(Horizontal.buttom, Vertical.center), ButtomRight(Horizontal.buttom,
			Vertical.right);

	public final Vertical vertical;
	public final Horizontal horizontal;

	public enum Horizontal
	{
		top, middle, buttom
	}

	public enum Vertical
	{
		left, center, right
	}

	ContentAlignment(Horizontal horizontal, Vertical vertical)
	{
		this.horizontal = horizontal;
		this.vertical = vertical;
	}
}
