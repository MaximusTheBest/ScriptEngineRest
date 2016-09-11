package nashorn.thread;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.Callable;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import nashorn.model.ApiResponse;
import nashorn.model.ApiResponse.Status;
import nashorn.service.impl.NashornServiceImpl;

public class ScriptThread implements Callable<ApiResponse> {

	private final ScriptEngine scriptEngine;

	private StringWriter stringWriter;

	private String script;

	private Long id;

	public ScriptThread(String script, Long id) {
		this.script = script;
		this.id = id;
		stringWriter = new StringWriter();
		scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
		ScriptContext defaultCtx = scriptEngine.getContext();
		defaultCtx.setWriter(stringWriter);
	}

	@Override
	public ApiResponse call() throws IOException {
		NashornServiceImpl.loggerCache.put(id, stringWriter);
		try {
			scriptEngine.eval(script);
			return new ApiResponse.Builder()
					.status(Status.ok)
					.build();
		} catch (ScriptException e) {
			return new ApiResponse.Builder()
					.message(e.getMessage())
					.status(Status.error)
					.build();
		} finally{
			Thread.currentThread().interrupt();
			stringWriter.close();
		}
		
	}

}
