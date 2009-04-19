package net.lecousin.web.search.google.internal;

import java.util.LinkedList;
import java.util.List;

import net.lecousin.framework.Triple;
import net.lecousin.framework.xml.XmlParsingUtil;
import net.lecousin.framework.xml.XmlParsingUtil.Node;
import net.lecousin.web.search.result.SearchWebResult;

public class WebResult {

	private WebResult() {}
	
	public static List<SearchWebResult> analyze(String page) {
		List<SearchWebResult> result = new LinkedList<SearchWebResult>();
		int i = 0;
		int j;
		while ((j = page.indexOf("<h3 class=r>", i)) > 0) {
			int k = page.indexOf("</h3>", j);
			if (k < 0) break;
			String s = page.substring(j+12,k);
			i=k+5;
			Triple<Node,Boolean,Integer> t = XmlParsingUtil.parseOpenNode(s, 0);
			if (t.getValue1() == null) continue;
			String url = t.getValue1().attributes.get("href");
			String title = "";
			if (!t.getValue2()) {
				k = s.indexOf("</a>", t.getValue3());
				if (k > 0)
					title = s.substring(t.getValue3(), k);
			}
			j = page.indexOf("<div class=\"s\">", i);
			String description = "";
			if (j > 0) {
				k = page.indexOf("</div>", j);
				if (k > 0) {
					s = page.substring(j+15, k);
					i = k+6;
					j = s.indexOf("<br><cite>");
					if (j > 0) {
						description = s.substring(0, j);
					}
				}
			}
			SearchWebResult r = new SearchWebResult(url, title, description);
			result.add(r);
		}
		return result;
	}
	
}
