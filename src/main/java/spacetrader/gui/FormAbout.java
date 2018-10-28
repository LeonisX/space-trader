package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.gui.debug.Launcher;
import spacetrader.util.ReflectionUtils;

public class FormAbout extends WinformForm {

    private PictureBox logoPicture = new PictureBox();
    private Label titleLabel = new Label();
    private Label aboutLabel = new Label();
    private Button closeButton = new Button();

    public FormAbout() {
        initializeComponent();
    }

    public static void main(String[] args) {
        Launcher.runForm(new FormAbout());
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        setName("formAbout");
        setText("About Space Trader");
        setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        setStartPosition(FormStartPosition.CENTER_PARENT);
        setClientSize(540, 181);
        setMaximizeBox(false);
        setMinimizeBox(false);
        setShowInTaskbar(false);
        setCancelButton(closeButton);
        
        suspendLayout();

        titleLabel.setAutoSize(true);
        titleLabel.setFont(FontCollection.bold10);
        titleLabel.setLocation(172, 8);
        //titleLabel.setSize(187, 13);
        titleLabel.setText("Space Trader for Java 8");

        aboutLabel.setLocation(172, 32);
        aboutLabel.setSize(410, 170);
        aboutLabel.setText("Copyright © 2018 by Stavila Leonid aka Leonis<BR>Site: http://tv-games.ru; e-mail: tv-games@mail.ru<BR><BR>" +
                "* Space Trader for Java version copyright © 2008-2010 by Aviv Eyal<BR>" +
                "* Space Trader for Windows version copyright © 2003-2008 by Jay French<BR>" +
                "* Original Palm OS version copyright © 2000-2002 by Peter Spronk<BR>" +
                "<BR>Pictures copyright © 2000 by Alexander Lawrence<BR><BR>" +
                "This game is freeware under a GNU General Public License.<BR>" +
                "https://github.com/LeonisX/space-trader");

        logoPicture.setImage(((Image) (ResourceManager.getImage("images/splash.jpg"))));
        logoPicture.setLocation(8, 8);
        logoPicture.setSize(160, 160);
        logoPicture.setTabStop(false);

        closeButton.setDialogResult(DialogResult.CANCEL);
        //TODO delete all sizes
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);

        controls.addAll(logoPicture, aboutLabel, titleLabel, closeButton);

        performLayout();

        ReflectionUtils.loadControlsData(this);
    }
}
