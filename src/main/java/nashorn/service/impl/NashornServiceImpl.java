package nashorn.service.impl;

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

import nashorn.model.ApiResponse;
import nashorn.model.ApiResponse.Status;
import nashorn.model.StatusScript;
import nashorn.model.StatusScriptResponse;
import nashorn.service.NashornService;
import nashorn.thread.ScriptThread;

@Service
public class NashornServiceImpl implements NashornService {

	public static final String URL = "http://localhost:8080";

	private Cache<Long, Future<ApiResponse>> cache = CacheBuilder.newBuilder().maximumSize(100000).build();

	private Map<Long, String> loggerCache = new LinkedHashMap<Long, String>();

	private ExecutorService ex = Executors.newSingleThreadExecutor();

	@Override
	public synchronized ApiResponse addScript(String script) {
		Long id = cacheIncriment();
		Future<ApiResponse> future = ex.submit(new ScriptThread(script));
		cache.put(id, future);
		return new ApiResponse.Builder().message(String.valueOf(id)).status(Status.ok).build();

	}

	@Override
	public ApiResponse getResultScriptById(Long id) throws Exception {
		Future<ApiResponse> future = null;
		future = cache.getIfPresent(id);
		if (future == null) {
			return new ApiResponse.Builder().message("Script not found").status(Status.error).build();
		}
		if (future.isDone()) {
			return future.get();

		} else {
			return new ApiResponse.Builder().message("This thread is run").status(Status.error).build();
		}
	}

	@Override
	public StatusScriptResponse getListScripts() {
		List<StatusScript> listStatusRequest = new ArrayList<StatusScript>();
		for (Entry<Long, Future<ApiResponse>> entry : cache.asMap().entrySet()) {
			Long key = entry.getKey();
			Future<ApiResponse> value = entry.getValue();
			StatusScript response = new StatusScript.Builder().id(key).isFinished(value.isDone())
					.isCalcel(value.isCancelled()).url(URL + "/script/" + key).build();
			listStatusRequest.add(response);
		}
		StatusScriptResponse response = new StatusScriptResponse();
		if (listStatusRequest.isEmpty()) {
			response.setMessage("List status scripts is empty");
		} else {
			response.setMessage("Status list is returned");
		}
		response.setStatus(Status.ok);
		response.setResult(listStatusRequest);
		return response;
	}

	@Override
	public ApiResponse removeScriptById(String id) throws ExecutionException {
		Future<ApiResponse> future = null;
		future = cache.getIfPresent(Long.valueOf(id));
		if (future == null) {
			return new ApiResponse.Builder().message("Script not found").status(Status.error).build();
		}
		future.cancel(true);
		cache.invalidate(Long.valueOf(id));
		return new ApiResponse.Builder().message("Script deleted").status(Status.ok).build();
	}

	private Long cacheIncriment() {
		return cache.size() + 1;
	}

}
