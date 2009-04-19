package net.lecousin.web.search;

import java.util.LinkedList;
import java.util.List;

import net.lecousin.framework.Triple;
import net.lecousin.framework.eclipse.extension.EclipsePluginExtensionUtil;
import net.lecousin.framework.log.Log;
import net.lecousin.web.search.internal.EclipsePlugin;

import org.eclipse.core.runtime.IConfigurationElement;

public class SearchEngineManager {

	private static List<Triple<SearchEngine,String,String>> engines = null;
	
	public static List<SearchEngine> getEngines() {
		init();
		List<SearchEngine> result = new LinkedList<SearchEngine>();
		for (Triple<SearchEngine,String,String> t : engines)
			result.add(t.getValue1());
		return result;
	}
	public static List<String> getEngineIDs() {
		init();
		List<String> result = new LinkedList<String>();
		for (Triple<SearchEngine,String,String> t : engines)
			result.add(t.getValue2());
		return result;
	}
	public static String getEngineID(SearchEngine engine) {
		init();
		for (Triple<SearchEngine,String,String> t : engines)
			if (t.getValue1() == engine)
				return t.getValue2();
		return null;
	}
	public static String getEngineName(SearchEngine engine) {
		init();
		for (Triple<SearchEngine,String,String> t : engines)
			if (t.getValue1() == engine)
				return t.getValue3();
		return null;
	}
	public static String getEngineName(String id) {
		init();
		for (Triple<SearchEngine,String,String> t : engines)
			if (t.getValue2().equals(id))
				return t.getValue3();
		return null;
	}
	public static SearchEngine getEngine(String id) {
		init();
		for (Triple<SearchEngine,String,String> t : engines)
			if (t.getValue2().equals(id))
				return t.getValue1();
		return null;
	}
	
	private static void init() {
		if (engines != null) return;
		engines = new LinkedList<Triple<SearchEngine,String,String>>();
		for (IConfigurationElement ext : EclipsePluginExtensionUtil.getExtensionsSubNode(EclipsePlugin.ID, "search_engine", "engine")) {
			String id = ext.getAttribute("id");
			String name = ext.getAttribute("name");
			try {
				SearchEngine engine = EclipsePluginExtensionUtil.createInstance(SearchEngine.class, ext, "class", new Object[][] { new Object[] {} });
				engines.add(new Triple<SearchEngine,String,String>(engine, id, name));
			} catch (Throwable t) {
				if (Log.error(SearchEngineManager.class))
					Log.error(SearchEngineManager.class, "Unable to instantiate SearchEngine id '"+id+"'", t);
			}
		}
	}
}
