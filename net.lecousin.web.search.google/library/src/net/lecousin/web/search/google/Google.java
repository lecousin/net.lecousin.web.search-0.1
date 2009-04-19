package net.lecousin.web.search.google;

import java.io.IOException;
import java.util.List;

import javax.net.SocketFactory;

import net.lecousin.framework.net.http.client.HttpClient;
import net.lecousin.framework.net.http.client.HttpRequest;
import net.lecousin.framework.net.http.client.HttpResponse;
import net.lecousin.framework.net.mime.Mime;
import net.lecousin.framework.net.mime.content.MimeContent;
import net.lecousin.framework.progress.WorkProgress;
import net.lecousin.web.search.SearchEngine;
import net.lecousin.web.search.SearchEngineException;
import net.lecousin.web.search.google.internal.WebQueryBuilder;
import net.lecousin.web.search.google.internal.WebResult;
import net.lecousin.web.search.query.SearchWebQuery;
import net.lecousin.web.search.result.SearchWebResult;

import org.apache.http.HttpStatus;

public class Google implements SearchEngine {

	public List<SearchWebResult> searchWeb(SearchWebQuery query, int startIndex, int endIndex, WorkProgress progress, int work) throws SearchEngineException {
		HttpClient client = new HttpClient(SocketFactory.getDefault());
		HttpRequest req = new HttpRequest("www.google.com", 80, "/search");
		req.addParameter("q", WebQueryBuilder.build(query));
		req.addParameter("start", startIndex);
		req.addParameter("num", endIndex-startIndex);
		req.addParameter("hl", WebQueryBuilder.getLang(query.lang));
		try {
			HttpResponse resp = client.send(req, true, progress, work);
			if (resp.getStatusCode() != HttpStatus.SC_OK)
				throw new SearchEngineException("Unable to retrieve search result: " + resp.getStatusCode() + " " + resp.getStatusDescription());
			Mime mime = resp.getContent();
			if (mime == null)
				throw new SearchEngineException("Search HTTP response is empty");
			MimeContent content = mime.getContent();
			if (content == null)
				throw new SearchEngineException("Search HTTP response is empty");
			String page = content.getAsString();
			return WebResult.analyze(page);
		} catch (IOException e) {
			throw new SearchEngineException("Unable to retrieve search result", e);
		}
	}
	
	
	
}
