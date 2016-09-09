package nashorn.service;

import java.util.concurrent.ExecutionException;

import nashorn.exception.ScriptNotFoundException;
import nashorn.model.ApiResponse;
import nashorn.model.StatusScriptResponse;

public interface NashornService {
	
	public ApiResponse addScript(String script);
	
	public ApiResponse getResultScriptById(Long id) throws Exception;

	public StatusScriptResponse getListScripts();
	
	public ApiResponse removeScriptById(String id) throws NumberFormatException, ExecutionException, ScriptNotFoundException;
}
