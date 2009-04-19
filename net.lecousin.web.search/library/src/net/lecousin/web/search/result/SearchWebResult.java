package net.lecousin.web.search.result;

public class SearchWebResult {

	public SearchWebResult(String url, String title, String description) {
		this.url = url;
		this.title = title;
		this.description = description;
	}
	
	private String url;
	private String title;
	private String description;
	
	public String getURL() { return url; }
	public String getTitle() { return title; }
	public String getDescription() { return description; }
	
}
