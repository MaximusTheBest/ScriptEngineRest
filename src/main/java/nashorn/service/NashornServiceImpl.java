package nashorn.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import nashorn.exception.ScriptNotFoundException;
import nashorn.model.StatusRequest;
import nashorn.thread.ScriptThread;

@Service
public class NashornServiceImpl implements NashornService {

	private Cache<Long, Future<String>> cache = CacheBuilder.newBuilder().maximumSize(100000).build();

	private Map<Long, String> loggerCache = new LinkedHashMap<Long, String>();

	private ExecutorService ex = Executors.newSingleThreadExecutor();

	@Override
	public synchronized Long addScript(String script) {
		Long id = cacheIncriment();
		Future<String> future = ex.submit(new ScriptThread(script));
		cache.put(id, future);
		return id;

	}
	
	@Override
	public String getResultScriptById(Long id) throws Exception {
		Future<String> future = null;
		try {
			future = cache.get(id);
		} catch (NullPointerException e) {
			throw new ScriptNotFoundException("Script not found");
		}
		if (future.isDone()) {
			return future.get();

		} else {
			throw new Exception("This thread is run");
		}
	}
	
	@Override
	public List<StatusRequest> getListScripts() {
		List<StatusRequest> listStatusRequest = new ArrayList<StatusRequest>();
		for (Entry<Long, Future<String>> entry : cache.asMap().entrySet()) {
			Long key = entry.getKey();
			Future<String> value = entry.getValue();
			StatusRequest request = new StatusRequest();
			request.setId(key);
			request.setIsFinished(value.isDone());
			request.setUrl("http://localhost:8080/script/" + key);
			listStatusRequest.add(request);

		}
		return listStatusRequest;
	}

	@Override
	public void removeScriptById(String id) throws NumberFormatException, ExecutionException, ScriptNotFoundException {
		Future<String> future = null;
		try {
			future = cache.get(Long.valueOf(id));
		} catch (NullPointerException e) {
			throw new ScriptNotFoundException("Script not found");
		} finally {
			future.cancel(true);
		}
		cache.invalidate(Long.valueOf(id));

	}

	private Long cacheIncriment() {
		return cache.size() + 1;
	}

}
