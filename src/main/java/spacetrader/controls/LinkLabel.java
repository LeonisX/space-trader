package spacetrader.controls;


public class LinkLabel extends Label {

    private LinkHolder links = new LinkHolder();
    private EventHandler<Object, LinkLabelLinkClickedEventArgs> linkClicked;
    private LinkArea linkArea;

    public LinkHolder getLinks() {
        return links;
    }

    public void setLinks(LinkHolder links) {
        this.links = links;
    }

    public EventHandler<Object, LinkLabelLinkClickedEventArgs> getLinkClicked() {
        return linkClicked;
    }

    public void setLinkClicked(EventHandler<Object, LinkLabelLinkClickedEventArgs> linkClicked) {
        this.linkClicked = linkClicked;
    }

    public void setLinkArea(LinkArea linkArea) {
        this.linkArea = linkArea;
    }
}
