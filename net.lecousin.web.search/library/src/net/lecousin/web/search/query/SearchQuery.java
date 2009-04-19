package net.lecousin.web.search.query;


public class SearchQuery {

	public String[] all;
	public String[] one;
	public String[] none;
	
	public Language lang = Language.ENGLISH;
	
	public enum Language {
		ENGLISH,
		FRENCH
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(lang.toString()).append(": ");
		boolean first = true;
		for (String str : all) {
			if (first) first = false; else s.append(' ');
			s.append(str);
		}
		if (one.length > 0) {
			if (s.length() > 0)
				s.append(" +");
			s.append('(');
			first = true;
			for (String str : one) {
				if (first) first = false; else s.append(" OR ");
				s.append(str);
			}
			s.append(')');
		}
		if (none.length > 0) {
			if (s.length() > 0) s.append(' ');
			s.append("-(");
			first = true;
			for (String str : none) {
				if (first) first = false; else s.append(" ");
				s.append(str);
			}
			s.append(')');
		}
		return s.toString();
	}
}
