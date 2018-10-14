package spacetrader.controls;

import spacetrader.util.Lisp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map.Entry;

class ImageStreamResourceManager extends ResourceManager {

    ImageStreamResourceManager(URL resource, String path) {
        super(resource, path);
    }

    ImageListStreamer getStream() {
        java.util.List<Entry<Object, Object>> ents = new ArrayList<Entry<Object, Object>>(properties.entrySet());
        ents.sort((arg0, arg1) -> {
            String left = (String) arg0.getKey();
            String right = (String) arg1.getKey();
            return left.compareTo(right);
        });

        Iterable<Image> images = Lisp.map(ents, entry -> {
//				System.out.println("Converting image: "+ entry.getValue());
            return (Image) getImage((String) entry.getValue());
        });

        return new ImageListStreamer(images);
    }
}
