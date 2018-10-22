package spacetrader.controls;

public class LinkLabelLinkClickedEventArgs extends EventArgs {

    private Link link;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
