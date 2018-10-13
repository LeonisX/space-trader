package jwinforms;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

import util.Convertor;
import util.Lisp;

public class ImageStreamResourceManager extends ResourceManager
{
	public ImageStreamResourceManager(URL resource, String path)
	{
		super(resource, path);
	}

	public ImageListStreamer getStream()
	{
		java.util.List<Entry<Object, Object>> ents = new ArrayList<Entry<Object, Object>>(properties.entrySet());
		Collections.sort(ents, new Comparator<Entry<Object, Object>>()
		{
			public int compare(Entry<Object, Object> arg0, Entry<Object, Object> arg1)
			{
				String left = (String)arg0.getKey();
				String right = (String)arg1.getKey();
				return left.compareTo(right);
			}
		});
		
		Iterable<Image> images = Lisp.map(ents, new Convertor<Image, Entry<Object, Object>>()
		{
			public Image convert(Entry<Object, Object> entry)
			{
//				System.out.println("Converting image: "+ entry.getValue());
				return (Image)getImage((String)entry.getValue());
			}
		});

		return new ImageListStreamer(images);
	}
}
