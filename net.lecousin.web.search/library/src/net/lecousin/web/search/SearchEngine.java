package net.lecousin.web.search;

import java.util.List;

import net.lecousin.framework.progress.WorkProgress;
import net.lecousin.web.search.query.SearchWebQuery;
import net.lecousin.web.search.result.SearchWebResult;

public interface SearchEngine {

	public List<SearchWebResult> searchWeb(SearchWebQuery query, int startIndex, int endIndex, WorkProgress progress, int work) throws SearchEngineException;
	
}
