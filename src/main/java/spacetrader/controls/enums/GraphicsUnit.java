package spacetrader.controls.enums;

public enum GraphicsUnit {
    /**
     * Specifies the world coordinate system unit as the unit of measure.
     */
    WORLD,
    /**
     * Specifies the unit of measure of the display device. Typically pixels for video displays, and 1/100 inch for printers.
     */
    DISPLAY,
    /**
     * Specifies a device pixel as the unit of measure.
     */
    PIXEL,
    /**
     * Specifies a printer's point (1/72 inch) as the unit of measure.
     */
    POINT,
    /**
     * Specifies the inch as the unit of measure.
     */
    INCH,
    /**
     * Specifies the document unit (1/300 inch) as the unit of measure.
     */
    DOCUMENT,
    /**
     * Specifies the millimeter as the unit of measure.
     */
    MILLIMETER;

    private final static int DPI = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();

    static float toPixels(GraphicsUnit sourceUnit) {
        switch (sourceUnit) {
            case DISPLAY:
            case PIXEL:
                return 1;

            case INCH:
                return DPI;

            case MILLIMETER:
                return DPI / 254f;

            case POINT:
                return DPI / 72f;

            case DOCUMENT:
                return DPI / 300f;

            case WORLD:
                throw new Error("Unsupported unit: I don't know what \"WORLD\" means.");

            default:
                throw new Error("Unsupported unit: " + sourceUnit);
        }
    }

    public float toPixels(float value) {
        return value * toPixels(this);
    }
}
