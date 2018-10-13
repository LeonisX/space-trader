package spacetrader.stub;

import java.util.Collection;
import java.util.Collections;

public class ArrayList<T> extends java.util.ArrayList<T>
{
	private static final long serialVersionUID = -537394628993404338l;

	public ArrayList(int i)
	{
		super(i);
	}

	public ArrayList()
	{
		super();
	}

	public ArrayList(Collection<? extends T> list)
	{
		super(list);
	}

	@SuppressWarnings("unchecked")
	public void Sort()
	{
		Object ob = this;
		Collections.sort((java.util.List<Comparable>)ob);
	}

	public void Reverse()
	{
		Collections.reverse(this);
	}

	public void RemoveRange(int index, int count)
	{
		removeRange(index, index+count);
	}
}
