package spacetrader.controls;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import spacetrader.controls.enums.FontStyle;
import spacetrader.controls.enums.GraphicsUnit;

public class Font extends java.awt.Font {

    public static final String WINDOWS_DEFAULT_FONT_FAMILY;
    public static final String WINDOWS_DEFAULT_FONT_FAMILY_BOLD;

    static {
        String preferredFonts = "Microsoft Sans Serif";

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        HashSet<String> names = new HashSet<>();
        Collections.addAll(names, ge.getAvailableFontFamilyNames());

        if (names.contains(preferredFonts)) {
            WINDOWS_DEFAULT_FONT_FAMILY = preferredFonts;
            WINDOWS_DEFAULT_FONT_FAMILY_BOLD = preferredFonts;
        } else {
            try {
                java.awt.Font def = makeAndRegisterFont("fonts/tahoma.ttf");
                WINDOWS_DEFAULT_FONT_FAMILY = def.getFamily();
                java.awt.Font bold = makeAndRegisterFont("fonts/tahomabd.ttf");
                WINDOWS_DEFAULT_FONT_FAMILY_BOLD = bold.getFamily();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final String fontFamily;
    private final String name;

    public Font(String name, float size) {
        this(name, size, FontStyle.Regular, GraphicsUnit.POINT);
    }

    public Font(String name, float size, FontStyle style, GraphicsUnit unit) {
        super(name, style.awtFontStyle, (int) unit.toPixels(size));
        this.name = getName();
        fontFamily = getFamily();
    }

    Font(java.awt.Font source) {
        super(source);
        name = getName();
        fontFamily = getFamily();
    }

    private static java.awt.Font makeAndRegisterFont(String name) throws FontFormatException, IOException {
        InputStream stream = Font.class.getClassLoader().getResourceAsStream(name);
        if (stream == null) {
            throw new IOException("Resource not found: " + name);
        }

        java.awt.Font font = java.awt.Font.createFont(Font.TRUETYPE_FONT, stream);
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        return font;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    @Override
    public String getName() {
        return name;
    }
}
