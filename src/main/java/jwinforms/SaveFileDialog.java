package jwinforms;

import util.Path;


public class SaveFileDialog extends FileDialog
{
	public SaveFileDialog()
	{
		setTitle("Save As");
		setButtonText("Save");
	}

	@Override
	public String getFileName()
	{
		return Path.DefaultExtension(super.getFileName(),".sav");
	}
}
