package jwinforms;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileDialog //extends WinformForm
{
	protected final JFileChooser chooser = new JFileChooser();

	private String buttonText;
	private String Filter;
	private String DefaultExt;

	private String title;

	public void setTitle(String title)
	{
		this.title = title;
	}

	protected void setButtonText(String text)
	{
		buttonText = text;
	}

	public DialogResult ShowDialog(WinformPane owner)
	{
		int returnVal = chooser.showDialog(owner.asSwingObject(), buttonText);

		switch (returnVal)
		{
		case JFileChooser.CANCEL_OPTION:
		case JFileChooser.ERROR_OPTION:
			return DialogResult.Cancel;
		case JFileChooser.APPROVE_OPTION:
			return DialogResult.OK;
		default:
			throw new Error("JFileChooser returned unknown value " + returnVal);
		}
	}

	public void setInitialDirectory(String dir)
	{
		chooser.setCurrentDirectory(new File(dir));
	}

	public void setFilter(String filter)
	{
		//setFilter("Windows Bitmaps (*.bmp)|*bmp")
		String[] parts = filter.split("\\|");

		String desc = parts[0];
		String[] extensions = parts[1].split(";");
		// I assume the format is "*.bmp;*.txt;*.gif".
		for (int i = 0; i < extensions.length; i++)
		{
			String extension = extensions[i];
			extensions[i] = extension.substring(extension.lastIndexOf('.') + 1);
		}

		FileFilter filefilter = new FileNameExtensionFilter(desc, extensions);
		chooser.setFileFilter(filefilter);
	}

	public void setDefaultExt(String defaultExt)
	{
		DefaultExt = defaultExt;
	}

	public void setFileName(String fileName)
	{
		chooser.setSelectedFile(new File(fileName));
	}

	public String getFileName()
	{
		return chooser.getSelectedFile().getAbsolutePath();
	}
}
