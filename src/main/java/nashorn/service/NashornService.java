package nashorn.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import nashorn.exception.ScriptNotFoundException;
import nashorn.model.StatusRequest;

public interface NashornService {
	
	public Long addScript(String script);
	
	public String getResultScriptById(Long id) throws Exception;

	public List<StatusRequest> getListScripts();
	
	public void removeScriptById(String id) throws NumberFormatException, ExecutionException, ScriptNotFoundException;
}
