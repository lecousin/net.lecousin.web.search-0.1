package net.lecousin.web.search.google.internal;

import net.lecousin.web.search.query.SearchWebQuery;
import net.lecousin.web.search.query.SearchQuery.Language;

public class WebQueryBuilder {

	private WebQueryBuilder() {}
	
	public static String build(SearchWebQuery q) {
		StringBuilder result = new StringBuilder();
		for (String s : q.all)
			quote(result, s);
		if (q.one.length > 0)
			quote(result, q.one[0]);
		for (int i = 1; i < q.one.length; ++i) {
			result.append("OR ");
			quote(result, q.one[i]);
		}
		for (String s : q.none) {
			result.append('-');
			quote(result, s);
		}
		return result.toString();
	}
	
	private static void quote(StringBuilder s1, String s2) {
		if (s2.indexOf(' ') >= 0)
			s1.append('"').append(s2).append('"');
		else
			s1.append(s2);
		s1.append(' ');
	}
	
	public static String getLang(Language l) {
		switch (l) {
		default:
		case ENGLISH: return "en";
		case FRENCH: return "fr";
		}
	}
}
