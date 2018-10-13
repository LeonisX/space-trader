package jwinforms;



public class LinkLabel extends Label
{

	public LinkHolder Links = new LinkHolder();
	public EventHandler<Object, LinkLabelLinkClickedEventArgs> LinkClicked;
	public LinkArea LinkArea;

}
