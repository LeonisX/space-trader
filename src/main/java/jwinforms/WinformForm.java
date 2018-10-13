package jwinforms;

import java.awt.Dimension;
import java.awt.Dialog.ModalityType;

import javax.swing.JDialog;

// TODO make Closing etc work.
public class WinformForm extends WinformPane
{
	private final JDialog jdialog;
	private final WinformJPanel panel;
	protected final WinformJPanel Controls;

	DialogResult result;

	public WinformForm()
	{
		// super(new WinformJPanel());
		super(new JDialog());
		jdialog = (JDialog)swingVersion;
		jdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		panel = new WinformJPanel(this);
		jdialog.setContentPane(panel);
		Controls = panel;
		jdialog.setResizable(false);
	}

	// ///////////// implementation ends here.
	public Graphics CreateGraphics()
	{
		return new Graphics(jdialog.getGraphics());
	}

	public DialogResult ShowDialog()
	{
		return ShowDialog(null);
	}

	// This should be "modal", i.e. - parent is blocked.
	public DialogResult ShowDialog(WinformPane owner)
	{
		parent = owner;

		panel.addMouseListener(getMouseListener());
		jdialog.setModalityType(ModalityType.APPLICATION_MODAL);

		fixLocation();
		show();

		return result;
	}

	private void fixLocation()
	{
		if (StartPosition == null)
			return;
		switch (StartPosition)
		{
		case CenterParent:
			jdialog.setLocationRelativeTo(parent == null ? null : parent.asSwingObject());
			break;
		case Manual:
			break;
		default:
			throw new Error("Unknown startPosition kind: " + StartPosition);
		}
	}

	public void Close()
	{
		jdialog.setVisible(false);
		jdialog.dispose();
	}

	public void PerformLayout()
	{}

	// Must encapsulate most of these.
	private Size AutoScaleBaseSize;
	private boolean ShowInTaskbar;
	private boolean MinimizeBox;
	private boolean MaximizeBox;
	private FormStartPosition StartPosition;
	private Button AcceptButton;
	private Button CancelButton;
	private String Title;
	private WinformPane parent;

	public void setAutoScaleBaseSize(Size autoScaleBaseSize)
	{
		AutoScaleBaseSize = autoScaleBaseSize;
	}

	public Size getAutoScaleBaseSize()
	{
		return AutoScaleBaseSize;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public String getTitle()
	{
		return Title;
	}

	public void setAcceptButton(Button acceptButton)
	{
		jdialog.getRootPane().setDefaultButton(acceptButton.asJButtton());
	}

	public Button getAcceptButton()
	{
		return AcceptButton;
	}

	// TODO handle.
	public void setCancelButton(Button cancelButton)
	{
		CancelButton = cancelButton;
	}

	public Button getCancelButton()
	{
		return CancelButton;
	}

	public void setStartPosition(FormStartPosition startPosition)
	{
		StartPosition = startPosition;
	}

	public void setControlBox(boolean controlBox)
	{
		jdialog.setDefaultCloseOperation(controlBox ? JDialog.DISPOSE_ON_CLOSE : JDialog.DO_NOTHING_ON_CLOSE);
	}

	// TODO ShowInTaskbar
	public void setShowInTaskbar(boolean showInTaskbar)
	{
		ShowInTaskbar = showInTaskbar;
	}

	public boolean getShowInTaskbar()
	{
		return ShowInTaskbar;
	}

	public void setMinimizeBox(boolean minimizeBox)
	{
		MinimizeBox = minimizeBox;
	}

	public boolean getMinimizeBox()
	{
		return MinimizeBox;
	}

	public void setMaximizeBox(boolean maximizeBox)
	{
		MaximizeBox = maximizeBox;
	}

	public boolean getMaximizeBox()
	{
		return MaximizeBox;
	}

	public void setFormBorderStyle(FormBorderStyle style)
	{
		switch (style)
		{
		case FixedDialog:
			jdialog.setUndecorated(false);
			break;
		case FixedSingle:
			jdialog.setUndecorated(false);
			break;
		case None:
			jdialog.setUndecorated(true);
			break;

		default:
			throw new Error("Unknown border style: " + style);
		}
	}

	public void setClientSize(Dimension clientSize)
	{
		// bigger, cause decorations count in swing.
		if (clientSize == null)
		{
			System.out.println("null here");
			return;
		}
		setSize(new Dimension(clientSize.width + 10, clientSize.height + 30));
	}

	public String getText()
	{
		return jdialog.getTitle();
	}

	public void setText(String text)
	{
		jdialog.setTitle(text);
	}

	@Override
	public void dispose()
	{
		jdialog.dispose();
	}

	@Override
	public void setResult(DialogResult dialogResult)
	{
		result = dialogResult;
	}

	protected void setBackgroundImage(Image backgroundImage)
	{
		panel.BackgroundImage = backgroundImage;
	}

	protected Image getBackgroundImage()
	{
		return panel.BackgroundImage;
	}
}
