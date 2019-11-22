package at.jojokobi.minigamesplugin.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MultiTickTask {
	
	public List<Runnable> tasks = new ArrayList<Runnable>();
	private Iterator<Runnable> iter;
	
	public void add (Runnable... runnables) {
		tasks.addAll(Arrays.asList(runnables));
	}
	
	public boolean hasNext () {
		return iter != null ? iter.hasNext() : !tasks.isEmpty();
	}
	
	public void next () {
		System.out.println("Executing task");
		if (iter == null) {
			iter = tasks.iterator();
		}
		iter.next().run();
	}
	
	public void executeAll () {
		while (hasNext()) {
			next();
		}
	}

}
