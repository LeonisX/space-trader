package jwinforms;

public enum GraphicsUnit
{
	/** Specifies the world coordinate system unit as the unit of measure. */
	World,
	/** Specifies the unit of measure of the display device. Typically pixels for video displays, and 1/100 inch for printers. */
	Display,
	/** Specifies a device pixel as the unit of measure. */
	Pixel,
	/** Specifies a printer's point (1/72 inch) as the unit of measure. */
	Point,
	/** Specifies the inch as the unit of measure. */
	Inch,
	/** Specifies the document unit (1/300 inch) as the unit of measure. */
	Document,
	/** Specifies the millimeter as the unit of measure. */
	Millimeter;

	static float toPixels(GraphicsUnit sourceUnit)
	{
		switch (sourceUnit)
		{
		case Display:
		case Pixel:
			return 1;

		case Inch:
			return dpi;
		case Millimeter:
			return dpi / 254f;

		case Point:
			return dpi / 72f;
		case Document:
			return dpi / 300f;

		case World:
			throw new Error("Unsupported unit: I don't know what \"World\" means.");
		default:
			throw new Error("Unsupported unit: " + sourceUnit);
		}
	}


	float toPixels(float value)
	{
		return value * toPixels(this);
	}

	private final static int dpi = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
}
