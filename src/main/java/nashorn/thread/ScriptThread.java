package nashorn.thread;

import java.io.StringWriter;
import java.util.concurrent.Callable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import nashorn.model.ApiResponse;
import nashorn.model.ApiResponse.Status;

public class ScriptThread implements Callable<ApiResponse> {

	private final ScriptEngine scriptEngine;

	private StringWriter stringWriter;

	private String script;

	public ScriptThread(String script) {
		this.script = script;
		stringWriter = new StringWriter();
		scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

	}

	@Override
	public ApiResponse call() {
		scriptEngine.getContext().setWriter(stringWriter);
		try {
			scriptEngine.eval(script);
		} catch (ScriptException e) {
			return new ApiResponse.Builder()
					.message(e.getMessage())
					.status(Status.error)
					.build();
		}
		return new ApiResponse.Builder().message(stringWriter.toString())
				.status(Status.ok)
				.build();
	}

}
